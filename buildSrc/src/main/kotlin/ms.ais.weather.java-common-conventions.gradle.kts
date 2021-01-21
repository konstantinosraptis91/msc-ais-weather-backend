plugins {
    java
}

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    // guava
    implementation("com.google.guava:guava:29.0-jre")
    // config
    implementation("com.typesafe:config:1.4.1")
    // jackson

    implementation("com.fasterxml.jackson.core:jackson-databind:2.12.0")
    implementation("com.fasterxml.jackson.core:jackson-core:2.12.0")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.12.0")
    // testing
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.test {
    useJUnitPlatform()
}
