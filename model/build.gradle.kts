plugins {
    id("msc.ais.weather.java-library-conventions")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "msc.ais.weather"
            artifactId = "model"
            version = "1.0.1"

            from(components["java"])
        }
    }
}

dependencies {

}
