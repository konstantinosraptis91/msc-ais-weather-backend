plugins {
    id("ms.ais.weather.java-library-conventions")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "ms.ais.weather"
            artifactId = "model"
            version = "1.0.1"

            from(components["java"])
        }
    }
}

dependencies {

}
