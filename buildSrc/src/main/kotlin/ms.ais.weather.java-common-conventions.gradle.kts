plugins {
    java
    `maven-publish`
}

repositories {
    mavenLocal()
    mavenCentral()
    jcenter()
}

dependencies {
    // guava
    implementation("com.google.guava:guava:29.0-jre")
    // Apache commons-io
    implementation("org.apache.commons:commons-lang3:3.11")
    // config
    implementation("com.typesafe:config:1.4.1")
    // jackson
    implementation("com.fasterxml.jackson.core:jackson-databind:2.12.0")
    implementation("com.fasterxml.jackson.core:jackson-core:2.12.0")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.12.0")
    // log4j2
    implementation("org.apache.logging.log4j:log4j-api:2.14.0")
    implementation("org.apache.logging.log4j:log4j-core:2.14.0")
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:2.14.0")
    // testing
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.1")
}

tasks.test {
    useJUnitPlatform()
}
