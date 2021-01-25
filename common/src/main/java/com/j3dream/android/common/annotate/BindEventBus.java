package com.j3dream.android.common.annotate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>文件名称: BindEventBus </p>
 * <p>所属包名: com.bloodsport.basic.annotate</p>
 * <p>描述: 绑定事件分发库注解 </p>
 * <p>创建时间: 2020/3/16 11:25 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface BindEventBus {
}
