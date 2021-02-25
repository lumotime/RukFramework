package com.j3dream.android.build.model

import com.j3dream.android.build.constant.BuildConstant

/**
 * <p>文件名称: ModuleInfo </p>
 * <p>所属包名: com.j3dream.build.android.bean</p>
 * <p>描述:  </p>
 * <p>feature:
 *
 * </p>
 * <p>创建时间: 2020/10/22 13:54 </p>
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
data class ModuleInfo(
        val applicationId: String,
        val versionCode: Int = BuildConstant.DEF_MIN_VERSION_CODE,
        val versionName: String = BuildConstant.DEF_MIN_VERSION_NAME,
        val compileSdkVersion: Int = BuildConstant.DEF_COMPILE_SDK_VERSION,
        val buildToolsVersion: String = BuildConstant.DEF_BUILD_TOOLS_VERSION,
        val minSdkVersion: Int = BuildConstant.DEF_MIN_SDK_VERSION,
        val targetSdkVersion: Int = BuildConstant.DEF_TARGET_SDK_VERSION,
        val artifactId: String
)