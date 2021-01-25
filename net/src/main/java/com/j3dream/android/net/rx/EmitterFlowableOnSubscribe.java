package com.j3dream.android.net.rx;

import androidx.annotation.Nullable;

import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;

/**
 * <p>文件名称: EmitterFlowableOnSubscribe </p>
 * <p>所属包名: com.sdftgs.visitor.common.rx</p>
 * <p>描述: 发送者Flowable创建者,为了保持FlowableEmitter 便于发送事件 </p>
 * <p>feature:
 *
 * </p>
 * <p>创建时间: 2020/7/2 13:51 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
public class EmitterFlowableOnSubscribe<T> implements FlowableOnSubscribe<T> {

    private FlowableEmitter<T> emitter;

    @Override
    public void subscribe(FlowableEmitter<T> emitter) throws Exception {
        this.emitter = emitter;
    }

    @Nullable
    public FlowableEmitter<T> getEmitter() {
        return emitter;
    }
}
