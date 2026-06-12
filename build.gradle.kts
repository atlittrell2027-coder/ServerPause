plugins {
    java
}

group = "org.example"
version = "1.0"

repositories {
    mavenCentral()
    // This is the active official repository url for PaperMC
    maven("https://papermc.io") 
}

dependencies {
    // Correct target mapping for the modern 26.1.x snapshot architecture
    compileOnly("io.papermc.paper:paper-api:26.1.2-R0.1-SNAPSHOT")
}

tasks.jar {
    archiveFileName.set("ServerPause.jar")
}