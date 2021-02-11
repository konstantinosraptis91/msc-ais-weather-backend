plugins {
    id("msc.ais.weather.java-library-conventions")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "msc.ais.weather"
            artifactId = "weather-lib"
            version = "1.0.1"

            from(components["java"])
        }
    }
}

dependencies {
    api(project(":model"))
    api(project(":service"))
}
