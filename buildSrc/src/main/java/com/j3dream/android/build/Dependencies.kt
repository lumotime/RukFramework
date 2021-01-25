package com.j3dream.android.build

/**
 * <p>文件名称: Dependencies </p>
 * <p>所属包名: com.j3dream.android.build</p>
 * <p>描述:  </p>
 * <p>feature:
 *
 * </p>
 * <p>创建时间: 2020/10/23 15:50 </p>
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
object Dependencies {

    val localDeps: LocalDependencies = LocalDependencies()

    val kotlin_stdlib = buildDependency("org.jetbrains.kotlin:kotlin-stdlib", DepVersions.kotlin)
    val Luban = buildDependency("top.zibin:Luban", DepVersions.Luban)
    val rxpermissions =
        buildDependency("com.github.tbruyelle:rxpermissions", DepVersions.rxpermissions)
    val BaseRecyclerViewAdapterHelper = buildDependency(
        "com.github.CymChad:BaseRecyclerViewAdapterHelper",
        DepVersions.BaseRecyclerViewAdapterHelper
    )
    val logger_orhanobut = buildDependency("com.orhanobut:logger", DepVersions.logger_orhanobut)
    val okio = buildDependency("com.squareup.okio:okio", DepVersions.okio)
    val okhttp = buildDependency("com.squareup.okhttp3:okhttp", DepVersions.okhttp)
    val guava = buildDependency("com.google.guava:guava", DepVersions.guava)
    val gson = buildDependency("com.google.code.gson:gson", DepVersions.gson)
    val glide = buildDependency("com.github.bumptech.glide:glide", DepVersions.glide)
    val glide_compiler = buildDependency("com.github.bumptech.glide:compiler", DepVersions.glide)
    val eventbus = buildDependency("org.greenrobot:eventbus", DepVersions.eventbus)
    val rxjava = buildDependency("io.reactivex.rxjava2:rxjava", DepVersions.rxjava)
    val rxandroid = buildDependency("io.reactivex.rxjava2:rxandroid", DepVersions.rxandroid)
    val retrofit = buildDependency("com.squareup.retrofit2:retrofit", DepVersions.retrofit)
    val retrofit_convert_rxjava = buildDependency(
        "com.squareup.retrofit2:adapter-rxjava2",
        DepVersions.retrofit_convert_rxjava
    )
    val retrofit_convert_gson =
        buildDependency("com.squareup.retrofit2:converter-gson", DepVersions.retrofit_convert_gson)

    // chrome 查看sqlite数据库
    val stetho = buildDependency("com.facebook.stetho:stetho", DepVersions.stetho)

    // 二维码扫描数据库
    val zxing_android = buildDependency("com.google.zxing:android-core", DepVersions.zxing)

    object Commons {
        val codec = buildDependency("commons-codec:commons-codec", DepVersions.Commons.codec)
        val pool2 = buildDependency("org.apache.commons:commons-pool2:", DepVersions.Commons.pool2)
    }

    object Framework {
        val core = buildDependency("com.j3dream:core", DepVersions.Framework.core)
        val common_android =
            buildDependency("com.j3dream.android:common", DepVersions.Framework.common_android)
        val idreader_android =
            buildDependency("com.j3dream.android:idreader", DepVersions.Framework.idreader_android)
        val arcface_android =
            buildDependency("com.j3dream.android:arcface", DepVersions.Framework.arcface_android)
        val net_android =
            buildDependency("com.j3dream.android:net", DepVersions.Framework.net_android)
        val camera_android =
            buildDependency("com.j3dream.android:camera", DepVersions.Framework.camera_android)
    }

    object Test {
        val junit = buildDependency("junit:junit", DepVersions.Test.junit)
        val junit_android = buildDependency("androidx.test.ext:junit", DepVersions.Test.junit_android)
        val espresso_android_core = buildDependency("androidx.test.espresso:espresso-core", DepVersions.Test.espresso_android)
    }

    object AndroidX {
        val core_ktx = buildDependency("androidx.core:core-ktx", DepVersions.AndroidX.core_ktx)

        // androidx 协程相关
        val concurrent_futures = buildDependency(
            "androidx.concurrent:concurrent-futures",
            DepVersions.AndroidX.concurrent_futures
        )
        val concurrent_futures_ktx = buildDependency(
            "androidx.concurrent:concurrent-futures-ktx",
            DepVersions.AndroidX.concurrent_futures
        )
        val arch_core_runtime =
            buildDependency("androidx.arch.core:core-runtime", DepVersions.AndroidX.arch_core)
        val arch_core_common =
            buildDependency("androidx.arch.core:core-common", DepVersions.AndroidX.arch_core)
        val appcompat =
            buildDependency("androidx.appcompat:appcompat", DepVersions.AndroidX.appcompat)
        val constraintlayout = buildDependency(
            "androidx.constraintlayout:constraintlayout",
            DepVersions.AndroidX.constraintlayout
        )

        // Android 多dex
        val multidex = buildDependency("androidx.multidex:multidex", DepVersions.AndroidX.multidex)
        val recyclerview =
            buildDependency("androidx.recyclerview:recyclerview", DepVersions.AndroidX.recyclerview)
        val cardview = buildDependency("androidx.cardview:cardview", DepVersions.AndroidX.cardview)
        val material =
            buildDependency("com.google.android.material:material", DepVersions.AndroidX.material)

        // 用于提取 exif 信息
        val exifinterface = buildDependency(
            "androidx.exifinterface:exifinterface",
            DepVersions.AndroidX.exifinterface
        )

        // 统一 emoji 表情包
        val emoji = buildDependency("androidx.emoji:emoji", DepVersions.AndroidX.emoji)
        val emoji_appcompat =
            buildDependency("androidx.emoji:emoji-appcompat", DepVersions.AndroidX.emoji)
        val emoji_bundled =
            buildDependency("androidx.emoji:emoji-bundled", DepVersions.AndroidX.emoji)

        // 管理应用启动
        val startup =
            buildDependency("androidx.startup:startup-runtime", DepVersions.AndroidX.startup)

        // viewpager2
        val viewpager2 =
            buildDependency("androidx.viewpager2:viewpager2", DepVersions.AndroidX.viewpager2)
        val room_runtime =
            buildDependency("androidx.room:room-runtime", DepVersions.AndroidX.jetpack_room)
        val room_ktx = buildDependency("androidx.room:room-ktx", DepVersions.AndroidX.jetpack_room)
        val room_rxjava2 =
            buildDependency("androidx.room:room-rxjava2", DepVersions.AndroidX.jetpack_room)
        val room_test =
            buildDependency("androidx.room:room-testing", DepVersions.AndroidX.jetpack_room)
        val camera_core =
            buildDependency("androidx.camera:camera-core", DepVersions.AndroidX.jetpack_camera)
        val camera_camera2 =
            buildDependency("androidx.camera:camera-camera2", DepVersions.AndroidX.jetpack_camera)
        val camera_lifecycle =
            buildDependency("androidx.camera:camera-lifecycle", DepVersions.AndroidX.jetpack_camera)
        val fragment =
            buildDependency("androidx.fragment:fragment", DepVersions.AndroidX.jetpack_fragment)
        val fragment_ktx =
            buildDependency("androidx.fragment:fragment-ktx", DepVersions.AndroidX.jetpack_fragment)
        val fragment_debug = buildDependency(
            "androidx.fragment:fragment-testing",
            DepVersions.AndroidX.jetpack_fragment
        )
        val lifecycle_runtime = buildDependency(
            "androidx.lifecycle:lifecycle-runtime-ktx",
            DepVersions.AndroidX.jetpack_lifecycle
        )
        val lifecycle_viewmodel_savedstate = buildDependency(
            "androidx.lifecycle:lifecycle-viewmodel-savedstate",
            DepVersions.AndroidX.jetpack_lifecycle
        )
        val paging_runtime =
            buildDependency("androidx.paging:paging-runtime", DepVersions.AndroidX.jetpack_paging)
        val paging_rxjava2 =
            buildDependency("androidx.paging:paging-rxjava2", DepVersions.AndroidX.jetpack_paging)
        val work_runtime =
            buildDependency("androidx.work:work-runtime", DepVersions.AndroidX.jetpack_workmanager)
        val work_ktx = buildDependency(
            "androidx.work:work-runtime-ktx",
            DepVersions.AndroidX.jetpack_workmanager
        )
        val work_rxjava2 =
            buildDependency("androidx.work:work-rxjava2", DepVersions.AndroidX.jetpack_workmanager)
    }

    object Tencent {
        val qmui = buildDependency("com.qmuiteam:qmui", DepVersions.Tencent.qmui)
        val bugly_crash =
            buildDependency("com.tencent.bugly:crashreport", DepVersions.Tencent.bugly_crash)
        val mmkv = buildDependency("com.tencent:mmkv-static", DepVersions.Tencent.mmkv)
        val x5tbs = buildDependency("com.tencent.tbs.tbssdk:sdk", DepVersions.Tencent.x5tbs)
    }

    /**
     * 通过地址和版本号构建依赖全地址
     * @param path 依赖地址
     * @param version 依赖所使用的版本号
     * @return 依赖全地址
     */
    fun buildDependency(path: String, version: String): String {
        val dependency = StringBuffer()
        dependency.append(path)
        if (!path.endsWith(":")) {
            dependency.append(":")
        }
        return dependency.append(version).toString()
    }
}