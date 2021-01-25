package com.j3dream.android.arcface.facelibrary;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.arcsoft.face.FaceFeature;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.j3dream.android.arcface.ArcfaceConstant;
import com.j3dream.android.arcface.cache.FaceFeatureCache;
import com.j3dream.android.arcface.cache.FaceFeatureCacheConfig;
import com.j3dream.android.arcface.data.CorrectUserInfo;
import com.j3dream.android.arcface.data.UserFeatureInfo;
import com.j3dream.android.arcface.pool.CompareFaceTask;
import com.j3dream.core.util.SystemUtils;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <p>文件名称: FaceRecognition </p>
 * <p>所属包名: com.lumotime.arcface.facelibrary</p>
 * <p>描述: 人脸识别 </p>
 * <p>feature:
 *
 * </p>
 * <p>创建时间: 2020/6/29 15:23 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
public class FaceRecognition {

    /**
     * 默认的人脸比对阀值
     */
    public static final float DEFAULT_FACE_PASS_RATE = 0.7F;

    /**
     * 查找比对人脸库的线程池
     */
    private ExecutorService compareExecutorService;
    private FaceFeatureCache faceFeatureCache;

    private FaceRecognition() {
        // 人像的对比查找数据 计算密集型 操作, 线程池核心线程数 == Cpu核心数, 队列为2倍CPU大小
        compareExecutorService = new ThreadPoolExecutor(SystemUtils.getCpuCount(), SystemUtils.getCpuCount(),
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(SystemUtils.getCpuCount() * 5));
        // 初始化 FaceFeatureRamLibrary 检测的缓存器 DEFAULT_FACE_FEATURE_RAM_LIBRARY_CACHE_INIT_SIZE
        // 为初始化容量
        faceFeatureCache = new FaceFeatureCache(new FaceFeatureCacheConfig.Builder().build());
    }

    /**
     * 比对的相似度 由大到小 排列
     *
     * @param compareInfoLinkedList 原始数据
     * @return 排列后的数据
     */
    private static LinkedList<CorrectUserInfo> orderReverse(
            @NonNull LinkedList<CorrectUserInfo> compareInfoLinkedList) {
        // 对重组后的结果进行重新排序
        Collections.sort(compareInfoLinkedList, new Comparator<CorrectUserInfo>() {
            @Override
            public int compare(CorrectUserInfo left, CorrectUserInfo right) {
                Float leftSimilar = 0F;
                if (left != null && left.getSimilar() != null) {
                    leftSimilar = left.getSimilar();
                }
                Float rightSimilar = 0F;
                if (right != null && right.getSimilar() != null) {
                    rightSimilar = right.getSimilar();
                }
                return rightSimilar.compareTo(leftSimilar);
            }
        });
        return compareInfoLinkedList;
    }

    public static FaceRecognition getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * 查找最接近的用户特征信息从LibraryLibrary中
     *
     * @param faceFeature 用户特征信息
     * @return 人脸比对最接近的结果 可能为空值
     */
    public CorrectUserInfo findClosestUserFeatureInfoInFeatureLibrary(
            FaceFeature faceFeature) {
        return Iterables.getFirst(
                findClosestUserFeatureInfoListInFeatureLibrary(faceFeature),
                null
        );
    }

    /**
     * 查找最接近的用户特征信息从LibraryLibrary中
     *
     * @param faceFeature 用户特征信息
     * @return 人脸比对最接近的结果 可能为空值
     */
    public LinkedList<CorrectUserInfo> findClosestUserFeatureInfoListInFeatureLibrary(
            FaceFeature faceFeature) {
        return findClosestUserFeatureInfoListInFeatureLibrary(faceFeature, DEFAULT_FACE_PASS_RATE,
                FaceFeatureCache.DEFAULT_FACE_FEATURE_RAM_LIBRARY_CACHE_INIT_SIZE);
    }

    /**
     * 查找最接近的用户特征信息从LibraryLibrary中
     *
     * @param faceFeature 用户特征信息
     * @param passRate    人脸比对阈值
     * @param groupSize   单线程任务大小
     * @return 人脸比对最接近的结果 可能为空值
     */
    public LinkedList<CorrectUserInfo> findClosestUserFeatureInfoListInFeatureLibrary(
            FaceFeature faceFeature, final float passRate, int groupSize) {
        LinkedList<CorrectUserInfo> correctUserInfoList = Lists.newLinkedList();
        if (faceFeatureCache.isAvailable()) {
            LinkedList<CorrectUserInfo> closestCacheFeature
                    = findFeatureInFaceFeatureCache(faceFeature, passRate);
            // 缓存中比中的结果应大于人脸阈值 + 偏移量，如果合值大于1时 将使用 阀值 + 1/5人脸阀值
            final float cachePassRate =
                    faceFeatureCache.getCacheConfig().getPassRateOffset();

            Collection<CorrectUserInfo> passRateCacheCorrectUserInfo = Collections2
                    .filter(closestCacheFeature, new Predicate<CorrectUserInfo>() {
                        @Override
                        public boolean apply(@NullableDecl CorrectUserInfo input) {
                            if (input == null) {
                                return false;
                            }
                            return input.getSimilar() >= (cachePassRate > 1 ?
                                    (passRate + cachePassRate / 5) : cachePassRate + passRate);
                        }
                    });
            if (passRateCacheCorrectUserInfo.size() > 1) {
                Collection<UserFeatureInfo> transformUserFeatureInfoList = Collections2
                        .transform(passRateCacheCorrectUserInfo, new Function<CorrectUserInfo, UserFeatureInfo>() {
                            @NullableDecl
                            @Override
                            public UserFeatureInfo apply(@NullableDecl CorrectUserInfo input) {
                                if (input == null) {
                                    return new CorrectUserInfo();
                                }
                                return input.getUserFaceInfo();
                            }
                        });
                faceFeatureCache.removeCache(transformUserFeatureInfoList);
            }
            // 将不符合缓存器标准的数据添加到结果数据中
            correctUserInfoList.addAll(closestCacheFeature);
        }

        LinkedList<CorrectUserInfo> closestUserFeatureInfoList = findClosestUserFeatureInfoList(
                faceFeature,
                FaceFeatureRamLibrary.getInstance().getUserAccessList(),
                passRate, groupSize
        );
        // 将缓存中和内存库中的数据结合
        closestUserFeatureInfoList.addAll(correctUserInfoList);
        // 按照相似度倒序排列
        LinkedList<CorrectUserInfo> allCorrectUserInfoList = orderReverse(closestUserFeatureInfoList);
        CorrectUserInfo correctUserInfo;
        if (faceFeatureCache.getCacheConfig().isCacheUse()
                && (correctUserInfo = Iterables.getFirst(allCorrectUserInfoList, null)) != null
                && correctUserInfo.getSimilar() >= passRate) {
            UserFeatureInfo userFaceInfo = correctUserInfo.getUserFaceInfo();
            // 将当前的人脸特征更新到人脸特征缓存区
            userFaceInfo.setFeature(faceFeature.getFeatureData());
            faceFeatureCache.addCache(userFaceInfo);
        }
        return allCorrectUserInfoList;
    }

    /**
     * 从缓存中查找人脸特征列表
     *
     * @param faceFeature 人脸特征
     * @param passRate    通过合格率
     * @return 符合的人脸特征列表
     */
    private LinkedList<CorrectUserInfo> findFeatureInFaceFeatureCache(
            @NonNull FaceFeature faceFeature, float passRate) {
        LinkedList<UserFeatureInfo> values = faceFeatureCache.getAllCache();
        float cacheGroupSize = Math.round(faceFeatureCache.getCount() % 10);
        LinkedList<CorrectUserInfo> closestCacheFeature = findClosestUserFeatureInfoList(
                faceFeature,
                values,
                passRate,
                // 将缓存中的数据充分的均分到每个线程上
                Math.round(cacheGroupSize)
        );
        return closestCacheFeature;
    }

    /**
     * 人脸库 人脸快速识别
     *
     * @param faceFeature  人脸特征
     * @param userCompares 人脸特征库列表
     * @return 人脸比对最接近的结果 可能为空值
     */
    @Nullable
    public CorrectUserInfo findClosestUserFeatureInfo(
            FaceFeature faceFeature,
            List<UserFeatureInfo> userCompares) {
        return Iterables.getFirst(
                findClosestUserFeatureInfoList(faceFeature, userCompares, DEFAULT_FACE_PASS_RATE,
                        FaceFeatureCache.DEFAULT_FACE_FEATURE_RAM_LIBRARY_CACHE_INIT_SIZE),
                null
        );
    }

    /**
     * 人脸库 人脸快速识别
     *
     * @param faceFeature  人脸特征
     * @param userCompares 人脸特征库列表
     * @param passRate     结果阈值
     * @param groupSize    单线程任务大小
     * @return 人脸比对最接近的结果 可能为空值
     */
    @Nullable
    public CorrectUserInfo findClosestUserFeatureInfo(
            FaceFeature faceFeature,
            List<UserFeatureInfo> userCompares,
            float passRate,
            int groupSize) {
        return Iterables.getFirst(
                findClosestUserFeatureInfoList(faceFeature, userCompares, passRate, groupSize),
                null
        );
    }

    /**
     * 人脸库 人脸快速识别
     *
     * @param faceFeature  人脸特征
     * @param userCompares 人脸特征库列表
     * @return 人脸比对最接近的结果列表
     */
    public LinkedList<CorrectUserInfo> findClosestUserFeatureInfoList(
            FaceFeature faceFeature,
            List<UserFeatureInfo> userCompares) {
        return findClosestUserFeatureInfoList(faceFeature, userCompares,
                DEFAULT_FACE_PASS_RATE,
                FaceFeatureCache.DEFAULT_FACE_FEATURE_RAM_LIBRARY_CACHE_INIT_SIZE);
    }

    /**
     * 人脸库 人脸快速识别
     *
     * @param faceFeature  人脸特征
     * @param userCompares 人脸特征库列表
     * @param passRate     结果阈值
     * @param groupSize    单线程任务大小
     * @return 人脸比对最接近的结果列表
     */
    public LinkedList<CorrectUserInfo> findClosestUserFeatureInfoList(
            FaceFeature faceFeature,
            List<UserFeatureInfo> userCompares,
            float passRate,
            int groupSize) {
        //识别到的人脸列表
        LinkedList<CorrectUserInfo> resultUserInfoList = Lists.newLinkedList();
        //分成1000一组，多线程处理
        List<List<UserFeatureInfo>> faceUserInfoPartList = Lists.partition(userCompares, groupSize);
        CompletionService<List<CorrectUserInfo>> completionService
                = new ExecutorCompletionService(compareExecutorService);
        for (List<UserFeatureInfo> part : faceUserInfoPartList) {
            completionService.submit(new CompareFaceTask(part, faceFeature, passRate));
        }
        for (int i = 0; i < faceUserInfoPartList.size(); i++) {
            List<CorrectUserInfo> faceUserInfoList = null;
            try {
                faceUserInfoList = completionService.take().get();
            } catch (InterruptedException | ExecutionException ex) {
                Log.e(ArcfaceConstant.TAG, "merge face recognition result failure", ex);
            } finally {
                if (faceUserInfoList != null && !faceUserInfoList.isEmpty()) {
                    resultUserInfoList.addAll(faceUserInfoList);
                }
            }
        }
        return orderReverse(resultUserInfoList);
    }

    public FaceFeatureCacheConfig getFaceFeatureCacheConfig() {
        return faceFeatureCache.getCacheConfig();
    }

    public void setFaceFeatureCacheConfig(FaceFeatureCacheConfig cacheConfig) {
        this.faceFeatureCache.setCacheConfig(cacheConfig);
    }

    /**
     * 获取人脸特征缓存器
     *
     * @return 人脸特征缓存器
     */
    public FaceFeatureCache getFaceFeatureCache() {
        return faceFeatureCache;
    }

    private static class Holder {
        private static final FaceRecognition INSTANCE = new FaceRecognition();
    }
}
