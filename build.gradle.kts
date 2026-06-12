plugins {
    java
}
group = "org.example"
version = "1.0"
repositories {
    mavenCentral()
    maven("https://papermc.io") 
}
dependencies {
    compileOnly("io.papermc.paper:paper-api:26.1.2-R0.1-SNAPSHOT")
}
tasks.jar {
    archiveFileName.set("ServerPause.jar")
}
