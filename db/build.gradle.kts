plugins {
    id("ms.ais.weather.java-library-conventions")
}

dependencies {
    implementation(project(":model"))
    implementation("org.xerial:sqlite-jdbc:3.34.0")
    implementation("org.apache.commons:commons-dbcp2:2.8.0")
}
