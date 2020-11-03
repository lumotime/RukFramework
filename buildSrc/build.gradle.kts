repositories {
    google()
    jcenter()
    maven("http://repo.novakj.cn/repository/maven-releases/")
}

plugins {
    `kotlin-dsl`
}

dependencies{
    // 基础Java工具集合
    implementation("com.j3dream:core:0.1.1")
}