package com.j3dream.android.build

/**
 * <p>文件名称: LocalDependencies </p>
 * <p>所属包名: com.j3dream.android.build</p>
 * <p>描述:  </p>
 * <p>feature:
 *      本地的
 * </p>
 * <p>创建时间: 2020/11/5 10:57 </p>
 * @author <a href="mail to: cnrivkaer@outlook.com" rel="nofollow">lumo</a>
 * @version v1.0
 */
class LocalDependencies {

    companion object {

        const val v_okhttp_interceptor_logger = "3.11.0"
    }


    val okhttp_interceptor_logger =
        Dependencies.buildDependency("com.squareup.okhttp3:logging-interceptor", DepVersions.okhttp)
}