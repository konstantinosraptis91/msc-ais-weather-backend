plugins {
    id("msc.ais.weather.java-library-conventions")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "ms.ais.weather"
            artifactId = "db"
            version = "1.0.1"

            from(components["java"])
        }
    }
}

dependencies {
    implementation(project(":model"))
    implementation("org.xerial:sqlite-jdbc:3.34.0")
    implementation("org.apache.commons:commons-dbcp2:2.8.0")
}
