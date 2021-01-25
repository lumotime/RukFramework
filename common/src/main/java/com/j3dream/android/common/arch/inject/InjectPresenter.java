package com.j3dream.android.common.arch.inject;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>文件名称: InjectPresenter </p>
 * <p>所属包名: cn.novakj.j3.arch.mvp.inject</p>
 * <p>描述:  </p>
 * <p>创建时间: 2020/4/24 11:20 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface InjectPresenter {
}
