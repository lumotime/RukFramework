package com.j3dream.android.build

/**
 * <p>文件名称: Dependencies </p>
 * <p>所属包名: com.j3dream.android.build</p>
 * <p>描述:  </p>
 * <p>feature:
 *
 * </p>
 * <p>创建时间: 2020/10/23 15:50 </p>
 * @author <a href="mail to: cnrivkaer@outlook.com" rel="nofollow">lumo</a>
 * @version v1.0
 */
object Dependencies {

    val kotlin_stdlib = buildDependency("org.jetbrains.kotlin:kotlin-stdlib", DepVersions.kotlin)
    val Luban = buildDependency("top.zibin:Luban", DepVersions.Luban)
    val rxpermissions = buildDependency("com.github.tbruyelle:rxpermissions", DepVersions.rxpermissions)
    val BaseRecyclerViewAdapterHelper = buildDependency("com.github.CymChad:BaseRecyclerViewAdapterHelper", DepVersions.BaseRecyclerViewAdapterHelper)
    val logger_orhanobut = buildDependency("com.orhanobut:logger", DepVersions.logger_orhanobut)
    val okio = buildDependency("com.squareup.okio:okio", DepVersions.okio)
    val okhttp = buildDependency("com.squareup.okhttp3:okhttp", DepVersions.okhttp)
    val guava = buildDependency("com.google.guava:guava", DepVersions.guava)
    val gson = buildDependency("com.google.code.gson:gson", DepVersions.gson)
    val glide = buildDependency("com.github.bumptech.glide:glide", DepVersions.glide)
    val glide_compiler = buildDependency("com.github.bumptech.glide:compiler", DepVersions.glide)
    val eventbus = buildDependency("org.greenrobot:eventbus", DepVersions.eventbus)

    object Commons {
        val codec = buildDependency("commons-codec:commons-codec", DepVersions.Commons.codec)
        val pool2 = buildDependency("org.apache.commons:commons-pool2:", DepVersions.Commons.pool2)
    }

    object Framework {
        val core = buildDependency("com.j3dream:core", DepVersions.Framework.core)
        val common_android = buildDependency("com.j3dream.android:common", DepVersions.Framework.common_android)
    }

    object Test {
        val junit = buildDependency("junit:junit", DepVersions.Test.junit)
        val junit_android = buildDependency("androidx.test.ext:junit", DepVersions.Test.junit_android)
        val espresso_android_core = buildDependency("androidx.test.espresso:espresso-core", DepVersions.Test.espresso_android)
    }

    object AndroidX{
        val core_ktx = buildDependency("androidx.core:core-ktx", DepVersions.AndroidX.core_ktx)
        val appcompat = buildDependency("androidx.appcompat:appcompat", DepVersions.AndroidX.appcompat)
        val constraintlayout = buildDependency("androidx.constraintlayout:constraintlayout", DepVersions.AndroidX.constraintlayout)
        val multidex = buildDependency("androidx.multidex:multidex", DepVersions.AndroidX.multidex)
        val recyclerview = buildDependency("androidx.recyclerview:recyclerview", DepVersions.AndroidX.recyclerview)
    }

    object Tencent {
        val qmui = buildDependency("com.qmuiteam:qmui", DepVersions.Tencent.qmui)
        val bugly_crash = buildDependency("com.tencent.bugly:crashreport", DepVersions.Tencent.bugly_crash)
    }

    /**
     * 通过地址和版本号构建依赖全地址
     * @param path 依赖地址
     * @param version 依赖所使用的版本号
     * @return 依赖全地址
     */
    private fun buildDependency(path: String, version: String): String{
        val dependency = StringBuffer()
        dependency.append(path)
        if (!path.endsWith(":")){
            dependency.append(":")
        }
        return dependency.append(version).toString()
    }
}