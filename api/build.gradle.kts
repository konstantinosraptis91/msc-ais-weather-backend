plugins {
    id("ms.ais.weather.java-application-conventions")
}

dependencies {
    implementation(project(":model"))
    implementation(project(":retriever"))
    implementation("io.javalin:javalin:3.12.0")
    implementation("org.slf4j:slf4j-simple:1.7.30")
}

application {
    mainClass.set("ms.ais.weather.api.Application")
}
