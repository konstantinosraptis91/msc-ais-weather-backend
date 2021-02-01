plugins {
    id("ms.ais.weather.java-library-conventions")
}

dependencies {
    implementation(project(":model"))
    implementation(project(":db"))
    implementation("org.apache.httpcomponents:httpclient:4.5.13")
    implementation("org.ehcache:ehcache:3.8.1")
}
