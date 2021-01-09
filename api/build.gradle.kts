plugins {
    id("ms.ais.weather.java-application-conventions")
}

dependencies {
    implementation("io.javalin:javalin:3.12.0")
}

application {
    mainClass.set("ms.ais.weather.api.Application")
}
