plugins {
    java
}

group = "org.example"
version = "2.9"

repositories {
    mavenCentral()
    // Official active repository database
    maven("https://repo.papermc.io/repository/maven-public/") 
}

dependencies {
    // Uses the modern target architecture required for version 26.2
    compileOnly("io.papermc.paper:paper-api:26.2.build.+")
}

tasks.jar {
    archiveFileName.set("ServerPause.jar")
}