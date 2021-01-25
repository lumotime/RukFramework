package com.j3dream.android.arcface;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;

import com.arcsoft.face.ErrorInfo;
import com.arcsoft.face.FaceEngine;
import com.arcsoft.face.FaceFeature;
import com.arcsoft.face.FaceInfo;
import com.arcsoft.face.FaceSimilar;
import com.arcsoft.face.enums.DetectFaceOrientPriority;
import com.arcsoft.face.enums.DetectMode;
import com.arcsoft.imageutil.ArcSoftImageFormat;
import com.arcsoft.imageutil.ArcSoftImageUtil;
import com.arcsoft.imageutil.ArcSoftImageUtilError;
import com.arcsoft.imageutil.ArcSoftRotateDegree;
import com.google.common.collect.Iterables;
import com.j3dream.android.arcface.config.EngineConfiguration;
import com.j3dream.android.arcface.config.FunctionConfiguration;
import com.j3dream.android.arcface.data.FaceFeatureInfo;
import com.j3dream.android.arcface.engine.RukFaceEngine;
import com.j3dream.android.arcface.exception.FaceEngineException;
import com.j3dream.android.arcface.exception.FaceHandleException;
import com.j3dream.android.arcface.pool.FaceEngineFactory;
import com.j3dream.android.common.util.Utils;
import com.j3dream.core.util.ObjectUtils;
import com.j3dream.core.util.StringUtils;
import com.j3dream.core.util.SystemUtils;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>文件名称: FaceEngineService </p>
 * <p>所属包名: com.lumotime.arcface</p>
 * <p>描述: 人脸引擎服务 </p>
 * <p>feature:
 *
 * </p>
 * <p>创建时间: 2020/6/29 09:27 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
public class FaceEngineService {

    private static final String[] NEED_LIBRARIES = new String[]{
            // 人脸相关
            "libarcsoft_face_engine.so",
            "libarcsoft_face.so",
            // 图像库相关
            "libarcsoft_image_util.so",
    };

    private String appId = "";
    private String sdkKey = "";

    /**
     * 人脸检测框架, 用于检测摄像头人脸数据, 使用VIDEO模式
     */
    private GenericObjectPool<RukFaceEngine> faceEngineDetectPool;

    /**
     * 通用人脸比对引擎池, 用于人脸库比对, 特征值提取等业务
     */
    private GenericObjectPool<RukFaceEngine> faceEngineGeneralPool;

    private FaceEngineService() {
    }

    public static FaceEngineService getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * 检查人脸引擎的so是否完整
     *
     * @return 人脸引擎so是否完整
     */
    public static boolean checkEngineSoFile() {
        File dir = new File(Utils.getApp().getApplicationInfo().nativeLibraryDir);
        File[] files = dir.listFiles();
        if (files == null || files.length == 0) {
            return false;
        }
        List<String> libraryNameList = new ArrayList<>();
        for (File file : files) {
            libraryNameList.add(file.getName());
        }
        boolean exists = true;
        for (String library : NEED_LIBRARIES) {
            exists &= libraryNameList.contains(library);
        }
        return exists;
    }

    /**
     * 截取合适的头像并旋转，保存为注册头像
     *
     * @param originImageData 原始的BGR24数据
     * @param width           BGR24图像宽度
     * @param height          BGR24图像高度
     * @param orient          人脸角度
     * @param cropRect        裁剪的位置
     * @return 头像的图像数据
     */
    public static Bitmap getHeadImage(byte[] originImageData, int width, int height, int orient, Rect cropRect) {
        return getHeadImage(originImageData, width, height, orient, cropRect, ArcSoftImageFormat.NV21);
    }

    /**
     * 截取合适的头像并旋转，保存为注册头像
     *
     * @param originImageData 原始的BGR24数据
     * @param width           BGR24图像宽度
     * @param height          BGR24图像高度
     * @param orient          人脸角度
     * @param cropRect        裁剪的位置
     * @param imageFormat     图像格式
     * @return 头像的图像数据
     */
    public static Bitmap getHeadImage(byte[] originImageData, int width, int height, int orient, Rect cropRect, ArcSoftImageFormat imageFormat) {

        cropRect = getBestRect(width, height, cropRect);

        cropRect.left &= ~3;
        cropRect.top &= ~3;
        cropRect.right &= ~3;
        cropRect.bottom &= ~3;

        byte[] headImageData = ArcSoftImageUtil.createImageData(cropRect.width(), cropRect.height(), imageFormat);
        int cropCode = ArcSoftImageUtil.cropImage(originImageData, headImageData, width, height, cropRect, imageFormat);
        if (cropCode != ArcSoftImageUtilError.CODE_SUCCESS) {
            throw new FaceEngineException("crop image failed, code is " + cropCode);
        }

        //判断人脸旋转角度，若不为0度则旋转注册图
        byte[] rotateHeadImageData = null;
        int rotateCode;
        int cropImageWidth;
        int cropImageHeight;
        // 90度或270度的情况，需要宽高互换
        if (orient == FaceEngine.ASF_OC_90 || orient == FaceEngine.ASF_OC_270) {
            cropImageWidth = cropRect.height();
            cropImageHeight = cropRect.width();
        } else {
            cropImageWidth = cropRect.width();
            cropImageHeight = cropRect.height();
        }
        ArcSoftRotateDegree rotateDegree = null;
        switch (orient) {
            case FaceEngine.ASF_OC_90:
                rotateDegree = ArcSoftRotateDegree.DEGREE_270;
                break;
            case FaceEngine.ASF_OC_180:
                rotateDegree = ArcSoftRotateDegree.DEGREE_180;
                break;
            case FaceEngine.ASF_OC_270:
                rotateDegree = ArcSoftRotateDegree.DEGREE_90;
                break;
            case FaceEngine.ASF_OC_0:
            default:
                rotateHeadImageData = headImageData;
                break;
        }
        // 非0度的情况，旋转图像
        if (rotateDegree != null) {
            rotateHeadImageData = new byte[headImageData.length];
            rotateCode = ArcSoftImageUtil.rotateImage(headImageData, rotateHeadImageData, cropRect.width(), cropRect.height(), rotateDegree, imageFormat);
            if (rotateCode != ArcSoftImageUtilError.CODE_SUCCESS) {
                throw new FaceEngineException("rotate image failed, code is " + rotateCode);
            }
        }
        // 将创建一个Bitmap，并将图像数据存放到Bitmap中
        Bitmap headBmp = Bitmap.createBitmap(cropImageWidth, cropImageHeight, Bitmap.Config.RGB_565);
        if (ArcSoftImageUtil.imageDataToBitmap(rotateHeadImageData, headBmp, imageFormat) != ArcSoftImageUtilError.CODE_SUCCESS) {
            throw new FaceEngineException("failed to transform image data to bitmap");
        }
        return headBmp;
    }

    private static boolean isListEmpty(List list) {
        return list == null || list.isEmpty();
    }

    @Nullable
    private static <T> T firstListObject(List<T> list) {
        return isListEmpty(list) ? null : list.get(0);
    }

    /**
     * 初始化人脸检测器的配置信息
     *
     * @return 人脸检测器配置信息
     */
    public static EngineConfiguration createDetectEngineConfiguration() {
        FunctionConfiguration detectFunctionCfg = new FunctionConfiguration();
        //开启人脸检测功能
        detectFunctionCfg.setSupportFaceDetect(true);
        //开启人脸识别功能
        detectFunctionCfg.setSupportFaceRecognition(true);
        //开启年龄检测功能
        detectFunctionCfg.setSupportAge(true);
        //开启性别检测功能
        detectFunctionCfg.setSupportGender(true);
        EngineConfiguration detectCfg = new EngineConfiguration();
        //未开启活体检测功能(免费版引擎一年期的活体使用权限, 需要关闭活体检测后可以正常使用)
        detectCfg.setFunctionConfiguration(detectFunctionCfg);
        //图片检测模式，如果是连续帧的视频流图片，那么改成VIDEO模式
        detectCfg.setDetectMode(DetectMode.ASF_DETECT_MODE_VIDEO);
        //调整检测人脸最大的数目
        detectCfg.setDetectFaceMaxNum(2);
        //人脸旋转角度
        detectCfg.setDetectFaceOrientPriority(DetectFaceOrientPriority.ASF_OP_ALL_OUT);
        detectCfg.setDetectFaceScaleVal(3);
        return detectCfg;
    }

    /**
     * 初始化通用人脸引擎的配置信息
     *
     * @return 通用人脸引擎配置信息
     */
    public static EngineConfiguration createGeneralEngineConfiguration() {
        EngineConfiguration generalCfg = new EngineConfiguration();
        FunctionConfiguration generalFunctionCfg = new FunctionConfiguration();
        //开启人脸检测功能
        generalFunctionCfg.setSupportFaceDetect(true);
        //开启人脸识别功能
        generalFunctionCfg.setSupportFaceRecognition(true);
        //开启年龄检测功能
        generalFunctionCfg.setSupportAge(true);
        //开启性别检测功能
        generalFunctionCfg.setSupportGender(true);
        //未开启活体检测功能(免费版引擎一年期的活体使用权限, 需要关闭活体检测后可以正常使用)
        generalCfg.setFunctionConfiguration(generalFunctionCfg);
        //图片检测模式，如果是连续帧的视频流图片，那么改成VIDEO模式
        generalCfg.setDetectMode(DetectMode.ASF_DETECT_MODE_IMAGE);
        //人脸旋转角度 通用引擎检测全部的角度
        generalCfg.setDetectFaceOrientPriority(DetectFaceOrientPriority.ASF_OP_ALL_OUT);
        return generalCfg;
    }

    /**
     * 将图像中需要截取的Rect向外扩张一倍，若扩张一倍会溢出，则扩张到边界，若Rect已溢出，则收缩到边界
     *
     * @param width   图像宽度
     * @param height  图像高度
     * @param srcRect 原Rect
     * @return 调整后的Rect
     */
    private static Rect getBestRect(int width, int height, Rect srcRect) {
        if (srcRect == null) {
            return null;
        }
        Rect rect = new Rect(srcRect);

        // 原rect边界已溢出宽高的情况
        int maxOverFlow = Math.max(-rect.left, Math.max(-rect.top, Math.max(rect.right - width, rect.bottom - height)));
        if (maxOverFlow >= 0) {
            rect.inset(maxOverFlow, maxOverFlow);
            return rect;
        }

        // 原rect边界未溢出宽高的情况
        int padding = rect.height() / 2;

        // 若以此padding扩张rect会溢出，取最大padding为四个边距的最小值
        if (!(rect.left - padding > 0 && rect.right + padding < width && rect.top - padding > 0 && rect.bottom + padding < height)) {
            padding = Math.min(Math.min(Math.min(rect.left, width - rect.right), height - rect.bottom), rect.top);
        }
        rect.inset(-padding, -padding);
        return rect;
    }

    public void activeEngine(String appId, String sdkKey) throws FaceEngineException {
        if (StringUtils.isEmpty(appId) || StringUtils.isEmpty(sdkKey)) {
            throw new FaceEngineException("引擎 APP_ID、SDK_KEY 不能为空");
        }
        setAppId(appId);
        setSdkKey(sdkKey);
        // 在线激活当前的引擎, 使用本应用必须进行一次联网激活
        int activeCode = RukFaceEngine.activeOnline(Utils.getApp().getApplicationContext(), appId, sdkKey);
        if (activeCode != ErrorInfo.MOK && activeCode != ErrorInfo.MERR_ASF_ALREADY_ACTIVATED) {
            Log.e(ArcfaceConstant.TAG, "引擎激活失败" + activeCode);
            throw new FaceEngineException("引擎激活失败" + activeCode);
        }
    }

    /**
     * 初始化人脸引擎, 请尝试在人脸检测业务前进行初始化
     * * 人脸检测、人脸特征值提取 引擎配比 1 : 3 (根据核心进行评估)
     */
    @RequiresPermission(allOf = {
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET
    })
    public FaceEngineService initFaceEngine(String appId, String sdkKey) throws FaceEngineException {
        return initFaceEngine(appId, sdkKey, Boolean.TRUE);
    }

    /**
     * 初始化人脸引擎, 请尝试在人脸检测业务前进行初始化
     * * 人脸检测、人脸特征值提取 引擎配比 1 : 3 (根据核心进行评估)
     */
    @RequiresPermission(allOf = {
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET
    })
    public FaceEngineService initFaceEngine(String appId, String sdkKey, boolean isDetectActive) throws FaceEngineException {
        return initFaceEngine(appId, sdkKey, isDetectActive, Boolean.FALSE);
    }

    /**
     * 初始化人脸引擎, 请尝试在人脸检测业务前进行初始化
     * * 人脸检测、人脸特征值提取 引擎配比 1 : 3 (根据核心进行评估)
     */
    @RequiresPermission(allOf = {
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET
    })
    public FaceEngineService initFaceEngine(String appId, String sdkKey, boolean isDetectActive, boolean isSingleEngine) throws FaceEngineException {
        return initFaceEngine(appId, sdkKey, createDetectEngineConfiguration(), createGeneralEngineConfiguration(), isDetectActive, isSingleEngine);
    }

    /**
     * 初始化人脸引擎, 请尝试在人脸检测业务前进行初始化
     * * 人脸检测、人脸特征值提取 引擎配比 1 : 3 (根据核心进行评估)
     */
    @RequiresPermission(allOf = {
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET
    })
    private FaceEngineService initFaceEngine(
            String appId, String sdkKey,
            @Nullable EngineConfiguration detectCfg,
            @Nullable EngineConfiguration generalCfg,
            boolean isDetectActive,
            boolean isSingleEngine
    ) throws FaceEngineException {
        if (StringUtils.isEmpty(appId) || StringUtils.isEmpty(sdkKey)) {
            throw new FaceEngineException(
                    "引擎 APP_ID、SDK_KEY 不能为空"
            );
        }

        if (ObjectUtils.isEmpty(detectCfg)) {
            detectCfg = createDetectEngineConfiguration();
        }

        if (ObjectUtils.isEmpty(generalCfg)) {
            generalCfg = createGeneralEngineConfiguration();
        }

        if (isInitialized()) {
            return this;
        }

        int cpuCount = SystemUtils.getCpuCount();
        long engineCount = Math.round(cpuCount * 0.8);
        // 人脸检测引擎核心数目
        int detectEngineCount = isSingleEngine ? 1 : Math.round(engineCount / 4.0F * 1.0F);
        // 人脸特征提取引擎核心数目
        int generalEngineCount = isSingleEngine ? 1 : Math.round(engineCount / 4.0F * 3.0F);

        Log.i(ArcfaceConstant.TAG, "engineCount: " + engineCount + "; detectEngineCount: " + detectEngineCount
                + "; compareEngineCount: " + generalEngineCount);
        // 摄像头人脸检测引擎
        if (isDetectActive) {
            GenericObjectPoolConfig detectPoolConfig = new GenericObjectPoolConfig();
            detectPoolConfig.setMaxIdle(detectEngineCount);
            detectPoolConfig.setMaxTotal(detectEngineCount);
            detectPoolConfig.setMinIdle(detectEngineCount);
            detectPoolConfig.setLifo(false);
            detectPoolConfig.setJmxEnabled(false);
            //底层库算法对象池
            //noinspection unchecked
            faceEngineDetectPool = new GenericObjectPool(new FaceEngineFactory(
                    Utils.getApp().getApplicationContext(),
                    appId, sdkKey, detectCfg), detectPoolConfig);
        }

        // 通用人脸引擎
        GenericObjectPoolConfig generalPoolConfig = new GenericObjectPoolConfig();
        generalPoolConfig.setMaxIdle(generalEngineCount);
        generalPoolConfig.setMaxTotal(generalEngineCount);
        generalPoolConfig.setMinIdle(generalEngineCount);
        generalPoolConfig.setLifo(false);
        generalPoolConfig.setJmxEnabled(false);

        //底层库算法对象池
        //noinspection unchecked
        faceEngineGeneralPool = new GenericObjectPool(new FaceEngineFactory(
                Utils.getApp().getApplicationContext(),
                appId, sdkKey, generalCfg), generalPoolConfig);
        return this;
    }

    public void unInit() {
        if (faceEngineDetectPool != null) {
            faceEngineDetectPool.clear();
            faceEngineDetectPool.close();
        }
        if (faceEngineGeneralPool != null) {
            faceEngineGeneralPool.clear();
            faceEngineGeneralPool.close();
        }
    }

    public boolean isInitialized() {
        return (faceEngineDetectPool != null && faceEngineGeneralPool != null
                && !faceEngineDetectPool.isClosed() && !faceEngineGeneralPool.isClosed());
    }

    /**
     * 获取人脸检测引擎池
     *
     * @return 人脸检测的引擎池
     */
    protected GenericObjectPool<RukFaceEngine> getFaceEngineDetectPool() {
        return faceEngineDetectPool;
    }

    /**
     * 获取通用人脸比对引擎池
     *
     * @return 通用人脸比对引擎池
     */
    public GenericObjectPool<RukFaceEngine> getFaceEngineGeneralPool() {
        return faceEngineGeneralPool;
    }

    /**
     * 检测预览的图像数据中人脸信息列表
     *
     * @param nv21Image 预览数据(nv21)
     * @param width     预览数据宽度
     * @param height    预览数据高度
     * @return 检测到预览的数据中的人脸信息列表
     */
    @Nullable
    public FaceInfo previewDetectFaceInfo(@NonNull byte[] nv21Image, int width, int height)
            throws Exception {
        return Iterables.getFirst(previewDetectFaceInfoList(nv21Image, width, height), null);
    }

    /**
     * 检测预览的图像数据中人脸信息列表
     *
     * @param nv21Image 预览数据(nv21)
     * @param width     预览数据宽度
     * @param height    预览数据高度
     * @return 检测到预览的数据中的人脸信息列表
     */
    @Nullable
    public List<FaceInfo> previewDetectFaceInfoList(@NonNull byte[] nv21Image, int width, int height)
            throws Exception {
        RukFaceEngine faceEngine = null;
        try {
            faceEngine = faceEngineDetectPool.borrowObject();
            if (faceEngine == null) {
                throw new FaceEngineException("fetch face engine failure!!!");
            }
            return faceEngine.detectFaces(nv21Image, width, height);
        } finally {
            if (faceEngine != null) {
                faceEngineDetectPool.returnObject(faceEngine);
            }
        }
    }

    /**
     * 比对两个位图中最大两张脸的特征信息
     *
     * @param nv21Image 待比对位图
     * @param width     nv21 待比对位图的宽度
     * @param height    nv21 待比对位图的高度
     * @param bitmap    待比对位图
     * @return 位图中各最大的一张脸的特征相似度
     */
    @Nullable
    public Float compareFace(@NonNull byte[] nv21Image, int width, int height, @NonNull Bitmap bitmap)
            throws Exception {
        RukFaceEngine faceEngine = null;
        try {
            faceEngine = faceEngineGeneralPool.borrowObject();
            if (faceEngine == null) {
                throw new FaceEngineException("fetch face engine failure!!!");
            }

            // 获取第一张位图的人脸信息及特征信息
            List<FaceInfo> faceInfoList = detectFaces(nv21Image, width, height);
            FaceInfo faceInfo;
            if (isListEmpty(faceInfoList) || (faceInfo = firstListObject(faceInfoList)) == null) {
                throw new FaceHandleException("nv21 detect faces list failure");
            }
            FaceFeature faceFeature = faceEngine.extractFaceFeature(nv21Image, width, height, faceInfo);
            if (faceFeature == null) {
                throw new FaceHandleException("nv21 extract face feature failure");
            }

            // 获取第二张位图的人脸信息及特征信息
            List<FaceInfo> faceInfoList1 = detectFaces(bitmap);
            FaceInfo faceInfo1;
            if (isListEmpty(faceInfoList1) || (faceInfo1 = firstListObject(faceInfoList)) == null) {
                throw new FaceHandleException("second bitmap detect faces list failure");
            }
            FaceFeature faceFeature1 = faceEngine.extractFaceFeature(bitmap, faceInfo1);
            if (faceFeature1 == null) {
                throw new FaceHandleException("second bitmap extract face feature failure");
            }
            return faceEngine.compareFace(faceFeature, faceFeature1);
        } finally {
            if (faceEngine != null) {
                faceEngineGeneralPool.returnObject(faceEngine);
            }
        }
    }

    /**
     * 比对两个位图中最大两张脸的特征信息
     *
     * @param bitmap  待比对位图
     * @param bitmap1 待比对位图
     * @return 位图中各最大的一张脸的特征相似度
     */
    @Nullable
    public Float compareFace(@NonNull Bitmap bitmap, @NonNull Bitmap bitmap1) throws Exception {
        RukFaceEngine faceEngine = null;
        try {
            //获取人脸引擎
            faceEngine = faceEngineGeneralPool.borrowObject();
            if (faceEngine == null) {
                throw new FaceEngineException("fetch face engine failure!!!");
            }

            // 获取第一张位图的人脸信息及特征信息
            List<FaceInfo> faceInfoList = faceEngine.detectFaces(bitmap);
            FaceInfo faceInfo;
            if (isListEmpty(faceInfoList) || (faceInfo = firstListObject(faceInfoList)) == null) {
                throw new FaceHandleException("first bitmap detect faces list failure");
            }
            FaceFeature faceFeature = faceEngine.extractFaceFeature(bitmap, faceInfo);
            if (faceFeature == null) {
                throw new FaceHandleException("first bitmap extract face feature failure");
            }

            // 获取第二张位图的人脸信息及特征信息
            List<FaceInfo> faceInfoList1 = detectFaces(bitmap1);
            FaceInfo faceInfo1;
            if (isListEmpty(faceInfoList1) || (faceInfo1 = firstListObject(faceInfoList)) == null) {
                throw new FaceHandleException("second bitmap detect faces list failure");
            }
            FaceFeature faceFeature1 = faceEngine.extractFaceFeature(bitmap1, faceInfo1);
            if (faceFeature1 == null) {
                throw new FaceHandleException("second bitmap extract face feature failure");
            }
            return faceEngine.compareFace(faceFeature, faceFeature1);
        } finally {
            if (faceEngine != null) {
                faceEngineGeneralPool.returnObject(faceEngine);
            }
        }
    }

    /**
     * 比对两个待比对的人脸特征相似度
     *
     * @param feature  待对比的人脸特征
     * @param feature1 待对比的人脸特征
     * @return 人脸特征相似度
     */
    @Nullable
    public Float compareFace(@NonNull FaceFeature feature, @NonNull FaceFeature feature1) throws Exception {
        return compareFace(feature.getFeatureData(), feature1.getFeatureData());
    }

    /**
     * 比对两个待比对的人脸特征相似度
     *
     * @param feature  待对比的人脸特征
     * @param feature1 待对比的人脸特征
     * @return 人脸特征相似度
     */
    @Nullable
    public Float compareFace(@NonNull byte[] feature, @NonNull byte[] feature1) throws Exception {
        RukFaceEngine faceEngine = null;
        try {
            //获取人脸引擎
            faceEngine = faceEngineGeneralPool.borrowObject();
            if (faceEngine == null) {
                throw new FaceEngineException("fetch face engine failure!!!");
            }

            FaceFeature faceFeature = new FaceFeature();
            faceFeature.setFeatureData(feature);

            FaceFeature faceFeature1 = new FaceFeature();
            faceFeature1.setFeatureData(feature1);

            FaceSimilar faceSimilar = new FaceSimilar();
            int errorCode = faceEngine.compareFaceFeature(faceFeature, faceFeature1, faceSimilar);
            if (errorCode == ErrorInfo.MOK) {
                return faceSimilar.getScore();
            } else {
                Log.e(ArcfaceConstant.TAG, "compareFace failure, errorCode: " + errorCode);
            }
        } finally {
            if (faceEngine != null) {
                faceEngineGeneralPool.returnObject(faceEngine);
            }
        }
        return null;
    }

    /**
     * 从Bitmap位图画面数据中检测出的人脸列表
     *
     * @param bitmap 画面位图
     * @return 检测到的人脸列表
     */
    @Nullable
    public FaceInfo detectFirstFace(@NonNull Bitmap bitmap) throws Exception {
        return Iterables.getFirst(detectFaces(bitmap), null);
    }

    /**
     * 从Bitmap位图画面数据中检测出的人脸列表
     *
     * @param bitmap 画面位图
     * @return 检测到的人脸列表
     */
    @Nullable
    public List<FaceInfo> detectFaces(@NonNull Bitmap bitmap) throws Exception {
        RukFaceEngine faceEngine = null;
        try {
            faceEngine = faceEngineGeneralPool.borrowObject();
            if (faceEngine == null) {
                throw new FaceEngineException("fetch face engine failure!!!");
            }
            return faceEngine.detectFaces(bitmap);
        } finally {
            if (faceEngine != null) {
                faceEngineGeneralPool.returnObject(faceEngine);
            }
        }
    }

    /**
     * 从NV21画面数据中检测出的人脸列表
     *
     * @param nv21Image nv21数据
     * @param width     nv21的视频宽度
     * @param height    nv21的视频高度
     * @return 检测到的人脸列表
     */
    @Nullable
    public FaceInfo detectFirstFace(@NonNull byte[] nv21Image, int width, int height) throws Exception {
        return Iterables.getFirst(detectFaces(nv21Image, width, height), null);
    }

    /**
     * 从NV21画面数据中检测出的人脸列表
     *
     * @param nv21Image nv21数据
     * @param width     nv21的视频宽度
     * @param height    nv21的视频高度
     * @return 检测到的人脸列表
     */
    @Nullable
    public List<FaceInfo> detectFaces(@NonNull byte[] nv21Image, int width, int height) throws Exception {
        RukFaceEngine faceEngine = null;
        try {
            faceEngine = faceEngineGeneralPool.borrowObject();
            if (faceEngine == null) {
                throw new FaceEngineException("fetch face engine failure!!!");
            }
            return faceEngine.detectFaces(nv21Image, width, height);
        } finally {
            if (faceEngine != null) {
                faceEngineGeneralPool.returnObject(faceEngine);
            }
        }
    }

    /**
     * 提取传入的图片数据中的最接近的一张人脸特征
     *
     * @param bitmap 传入的图片数据
     * @return 人脸特征信息
     * @throws Exception 处理中遇到的异常
     */
    @Nullable
    public FaceFeature extractFaceFeature(@NonNull Bitmap bitmap) throws Exception {
        FaceFeatureInfo faceFeatureInfo = extractFaceFeatureInfo(bitmap);
        FaceFeature faceFeature = new FaceFeature();
        faceFeature.setFeatureData(faceFeatureInfo.getFeatureData());
        return faceFeature;
    }

    /**
     * 提取传入的图片数据中的最接近的一张人脸特征
     *
     * @param bitmap 传入的图片数据
     * @return 人脸特征信息
     * @throws Exception 处理中遇到的异常
     */
    @Nullable
    public FaceFeatureInfo extractFaceFeatureInfo(@NonNull Bitmap bitmap) throws Exception {
        RukFaceEngine faceEngine = null;
        try {
            faceEngine = faceEngineGeneralPool.borrowObject();
            if (faceEngine == null) {
                throw new FaceEngineException("fetch face engine failure!!!");
            }
            FaceInfo faceInfo;
            List<FaceInfo> faceInfoList = faceEngine.detectFaces(bitmap);
            if (isListEmpty(faceInfoList) || (faceInfo = firstListObject(faceInfoList)) == null) {
                throw new FaceHandleException("first bitmap detect faces list failure");
            }
            FaceFeature faceFeature = faceEngine.extractFaceFeature(bitmap, faceInfo);
            if (faceFeature != null) {
                return new FaceFeatureInfo(faceInfo, faceFeature.getFeatureData());
            } else {
                throw new FaceHandleException("extract Face Feature failure");
            }
        } finally {
            if (faceEngine != null) {
                faceEngineGeneralPool.returnObject(faceEngine);
            }
        }
    }

    /**
     * 提取传入的图片数据中的最接近的一张人脸特征
     *
     * @param nv21   传入的图片数据
     * @param width  图片数据宽度
     * @param height 图片数据高度
     * @return 人脸特征信息
     * @throws Exception 处理中遇到的异常
     */
    @Nullable
    public FaceFeature extractFaceFeature(@NonNull byte[] nv21, int width, int height) throws Exception {
        FaceFeatureInfo faceFeatureInfo = extractFaceFeatureInfo(nv21, width, height);
        FaceFeature faceFeature = new FaceFeature();
        faceFeature.setFeatureData(faceFeatureInfo.getFeatureData());
        return faceFeature;
    }

    /**
     * 提取传入的图片数据中的最接近的一张人脸特征
     *
     * @param nv21   传入的图片数据
     * @param width  图片数据宽度
     * @param height 图片数据高度
     * @return 人脸特征信息
     * @throws Exception 处理中遇到的异常
     */
    @Nullable
    public FaceFeatureInfo extractFaceFeatureInfo(@NonNull byte[] nv21, int width, int height) throws Exception {
        RukFaceEngine faceEngine = null;
        try {
            faceEngine = faceEngineGeneralPool.borrowObject();
            if (faceEngine == null) {
                throw new FaceEngineException("fetch face engine failure!!!");
            }
            FaceInfo faceInfo;
            List<FaceInfo> faceInfoList = faceEngine.detectFaces(nv21, width, height);
            if (isListEmpty(faceInfoList) || (faceInfo = firstListObject(faceInfoList)) == null) {
                throw new FaceHandleException("first bitmap detect faces list failure");
            }
            FaceFeature faceFeature = faceEngine.extractFaceFeature(nv21, width, height, faceInfo);
            if (faceFeature != null) {
                return new FaceFeatureInfo(faceInfo, faceFeature.getFeatureData());
            } else {
                throw new FaceHandleException("extract Face Feature failure");
            }
        } finally {
            if (faceEngine != null) {
                faceEngineGeneralPool.returnObject(faceEngine);
            }
        }
    }

    /**
     * 提取传入的图片数据中的最接近的一张人脸特征
     *
     * @param bitmap   传入的图片数据
     * @param faceInfo 人脸在传入图片数据上的信息
     * @return 人脸特征信息
     * @throws Exception 处理中遇到的异常
     */
    @Nullable
    public FaceFeatureInfo extractFaceFeatureInfo(@NonNull Bitmap bitmap, @NonNull FaceInfo faceInfo) throws Exception {
        RukFaceEngine faceEngine = null;
        try {
            faceEngine = faceEngineGeneralPool.borrowObject();
            if (faceEngine == null) {
                throw new FaceEngineException("fetch face engine failure!!!");
            }
            FaceFeature faceFeature = faceEngine.extractFaceFeature(bitmap, faceInfo);
            if (faceFeature != null) {
                return new FaceFeatureInfo(faceInfo, faceFeature.getFeatureData());
            } else {
                throw new FaceHandleException("extract Face Feature failure");
            }
        } finally {
            if (faceEngine != null) {
                faceEngineGeneralPool.returnObject(faceEngine);
            }
        }
    }

    /**
     * 提取传入的图片数据中的最接近的一张人脸特征
     *
     * @param nv21     传入的图片数据
     * @param width    图片数据宽度
     * @param height   图片数据高度
     * @param faceInfo 人脸在传入图片数据上的信息
     * @return 人脸特征信息
     * @throws Exception 处理中遇到的异常
     */
    @Nullable
    public FaceFeatureInfo extractFaceFeatureInfo(@NonNull byte[] nv21, int width, int height,
                                                  @NonNull FaceInfo faceInfo) throws Exception {
        RukFaceEngine faceEngine = null;
        try {
            faceEngine = faceEngineGeneralPool.borrowObject();
            if (faceEngine == null) {
                throw new FaceEngineException("fetch face engine failure!!!");
            }
            FaceFeature faceFeature = faceEngine.extractFaceFeature(nv21, width, height, faceInfo);
            if (faceFeature != null) {
                return new FaceFeatureInfo(faceInfo, faceFeature.getFeatureData());
            } else {
                throw new FaceHandleException("extract Face Feature failure");
            }
        } finally {
            if (faceEngine != null) {
                faceEngineGeneralPool.returnObject(faceEngine);
            }
        }
    }

    /**
     * 人脸特征信息转化人脸特征
     *
     * @param featureInfo 人脸特征信息
     * @return 人脸特征
     */
    public FaceFeature convertFeature(@NonNull FaceFeatureInfo featureInfo) {
        FaceFeature faceFeature = new FaceFeature();
        faceFeature.setFeatureData(featureInfo.getFeatureData());
        return faceFeature;
    }

    public int getGeneralEngineActiveCount() {
        if (faceEngineGeneralPool == null) {
            return 0;
        } else {
            return faceEngineGeneralPool.getNumActive();
        }
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSdkKey() {
        return sdkKey;
    }

    public void setSdkKey(String sdkKey) {
        this.sdkKey = sdkKey;
    }

    private static class Holder {
        private static final FaceEngineService INSTANCE = new FaceEngineService();
    }
}
