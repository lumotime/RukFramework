package com.j3dream.android.build

import com.j3dream.android.build.model.ModuleInfo

/**
 * <p>文件名称: Modules </p>
 * <p>所属包名: com.j3dream.android.build</p>
 * <p>描述:  </p>
 * <p>feature:
 *
 * </p>
 * <p>创建时间: 2020/10/23 15:47 </p>
 * @author <a href="mail to: cnrivkaer@outlook.com" rel="nofollow">lumo</a>
 * @version v1.0
 */
object Modules{

    /**
     * example android application build config
     */
    val M_example = ModuleInfo("com.j3dream.android.example", 1001, "0.01.001")
    val M_common = ModuleInfo("com.j3dream.android.common")
    val M_idreader = ModuleInfo("com.j3dream.android.idreader")
    val M_arcface = ModuleInfo("com.j3dream.android.arcface")
    val M_net = ModuleInfo("com.j3dream.android.net")
    val M_camera = ModuleInfo("com.j3dream.android.camera")
}