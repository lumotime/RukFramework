package com.j3dream.android.net.interceptor;

import android.util.Log;

import com.j3dream.android.net.NetConfigurator;
import com.j3dream.android.net.NetConstant;
import com.j3dream.android.net.config.NetConfig;
import com.j3dream.android.net.model.SecurityKey;
import com.j3dream.core.util.EncryptUtils;
import com.j3dream.core.util.ObjectUtils;
import com.j3dream.core.util.RandomUtils;
import com.j3dream.core.util.StringUtils;
import com.j3dream.core.util.ThrowableUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.Okio;

/**
 * <p>文件名称: ContactSecurityInterceptor </p>
 * <p>所属包名: com.bloodsport.net.interceptor</p>
 * <p>描述: 通讯安全拦截器, 主要用于对客户端与服务器端的通信进行加密 </p>
 * <p>创建时间: 2020-02-19 16:23 </p>
 * <p>公司信息: 济南丰通信息科技 技术部</p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
public class ContactSecurityInterceptor implements Interceptor {

    private static final MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
    private static final int DEFAULT_AES_KEY_LENGTH = 16;

    @Override
    public Response intercept(Chain chain) throws IOException {
        NetConfig netFrameConfig = NetConfigurator.getInstance().getNetConfig();
        // 加解密头部密钥字段名称
        String encryptKeyName = netFrameConfig.getEncryptKeyName();
        // 加密解密的密钥对象
        SecurityKey securityKey = netFrameConfig.getSecurityKey();
        Request request = chain.request();

        if (StringUtils.isEmpty(encryptKeyName) || ObjectUtils.isEmpty(securityKey)) {
            return chain.proceed(request);
        }

        // 构建新的请求
        Request.Builder reqBuilder = request.newBuilder();
        if (StringUtils.isNotEmpty(securityKey.getPublicKey())) {
            // 获取旧的请求中的内容
            RequestBody oldRequestBody = request.body();
            String oldBodyStr = "";
            if (ObjectUtils.isNotEmpty(oldRequestBody)) {
                Buffer requestBuffer = new Buffer();
                oldRequestBody.writeTo(requestBuffer);
                oldBodyStr = requestBuffer.readUtf8();
                requestBuffer.close();
            }
            Log.i(NetConstant.TAG, "request body: => " + StringUtils.null2Length0(oldBodyStr));
            // 加密请求头的索引位置 如果大于等于 0 则认为存在该头部, 即跳过头部加密
            String skipEncrypt = request.header(NetConstant.HEARD_SKIP_ENCRYPT_TAG);
            reqBuilder.headers(request.headers());
            String newRequestBody = oldBodyStr;
            if (StringUtils.isEmpty(skipEncrypt)) {
                // 获取到一个新的对称加密Key
                String curSymmetricEncryptionKey = RandomUtils.nextString(DEFAULT_AES_KEY_LENGTH);
                try {
                    newRequestBody = EncryptUtils.encryptForAES(oldBodyStr, curSymmetricEncryptionKey);
                    String rsaKey = EncryptUtils.encryptForRSA(curSymmetricEncryptionKey, securityKey.getPublicKey());
                    reqBuilder.header(encryptKeyName, rsaKey);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Log.e(NetConstant.TAG, "加密失败, ex:" + ThrowableUtils.getStackTrace(ex));
                }
            }
            // 填充原始内容
            RequestBody newBody = RequestBody.create(mediaType, newRequestBody);
            reqBuilder.method(request.method(), newBody)
                    .header("Content-Type", StringUtils.null2Length0(ObjectUtils.toString(newBody.contentType())))
                    .header("Content-Length", String.valueOf(newBody.contentLength()));
        }

        // 处理请求
        Response response = chain.proceed(reqBuilder.build());
        // 即使不成功也需要对response进行解密, 才能正常打印出返回的相关内容
        if (!response.isSuccessful()) {
            return response;
        }
        ResponseBody oldRespBody = response.body();
        String respBodyStr = "";
        if (ObjectUtils.isNotEmpty(oldRespBody)) {
            BufferedSource bufferedSource = Okio.buffer(oldRespBody.source());
            respBodyStr = bufferedSource.readUtf8();
        }
        // 响应加密的AES Key
        String respEncryptKey = response.header(encryptKeyName);
        String newRespBodyStr = respBodyStr;
        if (StringUtils.isNotEmpty(respEncryptKey)) {
            try {
                String aesKey = EncryptUtils.decryptForRSA(respEncryptKey, securityKey.getPrivateKey());
                newRespBodyStr = EncryptUtils.decryptForAES(newRespBodyStr, aesKey);
            } catch (Exception ex) {
                ex.printStackTrace();
                Log.e(NetConstant.TAG, "解密失败, ex: " + ThrowableUtils.getStackTrace(ex));
            }
        }
        Log.i(NetConstant.TAG, "response body: => " + StringUtils.null2Length0(newRespBodyStr));
        ResponseBody newResponseBody = ResponseBody.create(mediaType, newRespBodyStr);
        return response.newBuilder().body(newResponseBody).build();
    }
}
