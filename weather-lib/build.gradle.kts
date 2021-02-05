plugins {
    id("ms.ais.weather.java-library-conventions")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "ms.ais.weather"
            artifactId = "weather-lib"
            version = "1.0.0"

            from(components["java"])
        }
    }
}

dependencies {
    api(project(":model"))
    api(project(":db"))
    api(project(":service"))
}
