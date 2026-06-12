plugins {
    java
}

group = "org.example"
version = "1.0"

repositories {
    mavenCentral()
    // Official active repository database
    maven("https://repo.papermc.io/repository/maven-public/") 
}

dependencies {
    // Uses the modern target architecture required for version 26.1.2
    compileOnly("io.papermc.paper:paper-api:26.1.2.build.+")
}

tasks.jar {
    archiveFileName.set("ServerPause.jar")
}