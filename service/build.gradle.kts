plugins {
    id("msc.ais.weather.java-library-conventions")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "msc.ais.weather"
            artifactId = "service"
            version = "1.0.1"

            from(components["java"])
        }
    }
}

dependencies {
    implementation(project(":model"))
    implementation("org.apache.httpcomponents:httpclient:4.5.13")
    implementation("org.ehcache:ehcache:3.8.1")
}
