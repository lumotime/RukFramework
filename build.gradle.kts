repositories {
    google()
    jcenter()
    maven("http://47.105.177.125:8081/repository/maven-snapshots/")
}

plugins {
    `kotlin-dsl`
}

dependencies{
    // 基础Java工具集合
    implementation("com.j3dream:core:0.0.1-SNAPSHOT")
}