plugins {
    id("java-library")
}


dependencies {
    api(project(":api"))
    implementation(libs.guava)
    implementation(libs.gson)
    implementation(libs.caffeine)
    compileOnly(libs.adventure)
}

