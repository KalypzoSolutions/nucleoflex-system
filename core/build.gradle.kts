plugins {
    id("java-library")
}


dependencies {
    api(project(":api"))
    api(libs.guava)
    api(libs.caffeine)
    compileOnly(libs.adventure)
}

