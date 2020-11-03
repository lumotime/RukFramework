package com.j3dream.android.net.rx;

import com.j3dream.android.net.NetConfigurator;
import com.j3dream.android.net.exception.NetHandleException;
import com.j3dream.android.net.model.NetRespMod;
import com.j3dream.core.util.ObjectUtils;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * <p>文件名称: NetRespModConvertedFunction </p>
 * <p>所属包名: cn.novakj.j3.net.rx</p>
 * <p>描述: 网络响应实体转化数据方法 </p>
 * <p>创建时间: 2020/4/21 09:50 </p>
 *
 * @author <a href="mail to: cnrivkaer@outlook.com" rel="nofollow">lumo</a>
 * @version v1.0
 */
public class NetRespModConvertedFunction<R, T extends NetRespMod<R>> implements Function<T, Observable<R>> {

    @Override
    public Observable<R> apply(T t) throws Exception {
        if (t == null)
            return Observable.error(new NetHandleException("net response empty"));

        if (t.getCode() != NetConfigurator.getInstance().getNetConfig().getRespSuccessCode()) {
            return Observable.error(new NetHandleException(t.getMessage(), t.getCode()));
        } else {
            if (ObjectUtils.isNotEmpty(t.getData()))
                return Observable.just(t.getData());
            else
                return Observable.empty();
        }
    }
}
