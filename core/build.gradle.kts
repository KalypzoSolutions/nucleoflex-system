plugins {
    id("java-library")
}


dependencies {
    api(project(":api"))
    implementation(libs.guava)
    implementation(libs.gson)
    implementation(libs.jedis)
    implementation(libs.caffeine)
    compileOnly(libs.adventure)
    compileOnly(libs.luckperms )
}

