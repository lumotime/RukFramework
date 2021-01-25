package com.j3dream.android.common.interf;

import com.j3dream.android.common.base.BaseApplication;

/**
 * <p>文件名称: ICompatApplication </p>
 * <p>所属包名: cn.novakj.j3.core.base</p>
 * <p>描述: 提供给多个组件初始化Application的能力 </p>
 * <p>创建时间: 2020/4/24 10:07 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
public interface ICompatApplication {

    /**
     * 需要在应用主线程中进行初始化的操作
     *
     * @param application 应用
     */
    void initModuleApplicationInMainThread(BaseApplication application);

    /**
     * 需要在应用后台初始化线程中进行初始化的操作
     *
     * @param application 应用
     */
    void initModuleApplicationInBackThread(BaseApplication application);
}
