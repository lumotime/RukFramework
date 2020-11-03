package com.j3dream.android.arcface.pool;

import android.content.Context;
import android.util.Log;

import com.arcsoft.face.ErrorInfo;
import com.j3dream.android.arcface.ArcfaceConstant;
import com.j3dream.android.arcface.config.EngineConfiguration;
import com.j3dream.android.arcface.engine.RukFaceEngine;
import com.j3dream.android.arcface.exception.FaceEngineException;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

public class FaceEngineFactory extends BasePooledObjectFactory<RukFaceEngine> {

    private Context context;
    private String appId;
    private String sdkKey;
    private String activeKey;
    private EngineConfiguration engineConfiguration;

    public FaceEngineFactory(Context context, String appId, String sdkKey, EngineConfiguration engineConfiguration) {
        this.context = context;
        this.appId = appId;
        this.sdkKey = sdkKey;
        this.engineConfiguration = engineConfiguration;
    }

    @Override
    public RukFaceEngine create() {
        RukFaceEngine faceEngine = new RukFaceEngine();
        // 在线激活当前的引擎, 使用本应用必须进行一次联网激活
        int activeCode = RukFaceEngine.activeOnline(context, appId, sdkKey);
        if (activeCode != ErrorInfo.MOK && activeCode != ErrorInfo.MERR_ASF_ALREADY_ACTIVATED) {
            Log.e(ArcfaceConstant.TAG, "引擎激活失败" + activeCode);
            throw new FaceEngineException("引擎激活失败" + activeCode);
        }
        int initCode = faceEngine.init(context, engineConfiguration);
        if (initCode != ErrorInfo.MOK) {
            Log.e(ArcfaceConstant.TAG, "引擎初始化失败" + initCode);
            throw new FaceEngineException("引擎初始化失败" + initCode);
        }
        return faceEngine;
    }

    @Override
    public PooledObject<RukFaceEngine> wrap(RukFaceEngine faceEngine) {
        return new DefaultPooledObject<>(faceEngine);
    }

    @Override
    public void destroyObject(PooledObject<RukFaceEngine> p) throws Exception {
        RukFaceEngine faceEngine = p.getObject();
        int result = faceEngine.unInit();
        super.destroyObject(p);
    }
}