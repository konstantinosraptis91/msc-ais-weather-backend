plugins {
    id("ms.ais.weather.java-library-conventions")
}

dependencies {
    implementation(project(":model"))
    implementation("org.apache.httpcomponents:httpclient:4.5.13")
}
