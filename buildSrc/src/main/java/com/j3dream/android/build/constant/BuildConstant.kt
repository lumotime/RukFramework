package com.j3dream.android.build.constant

/**
 * <p>文件名称: Constant </p>
 * <p>所属包名: com.j3dream.build.android.constant</p>
 * <p>描述:  </p>
 * <p>feature:
 *
 * </p>
 * <p>创建时间: 2020/10/22 13:56 </p>
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
object BuildConstant{
    const val DEF_MIN_VERSION_CODE = 1
    const val DEF_MIN_VERSION_NAME = "0.0.1"
    const val DEF_COMPILE_SDK_VERSION = 29
    const val DEF_BUILD_TOOLS_VERSION = "29.0.3"
    const val DEF_MIN_SDK_VERSION = 21
    const val DEF_TARGET_SDK_VERSION = 29

    object Plugins {
        const val Application = "com.android.application"
        const val Library = "com.android.library"
        const val Kotlin_Android = "kotlin-android"
        const val Kotlin_Android_Extensions = "kotlin-android-extensions"
        const val Kotlin_KAPT = "kotlin-kapt"
    }
}