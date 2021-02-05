plugins {
    id("ms.ais.weather.java-application-conventions")
}

dependencies {
    implementation(project(":model"))
    implementation(project(":service"))
    implementation("io.javalin:javalin:3.12.0")
    // implementation("org.slf4j:slf4j-simple:1.7.30")
}

application {
    mainClass.set("ms.ais.weather.api.Application")
}

tasks.register<Jar>("uberJar") {
    archiveClassifier.set("uber")
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    from(sourceSets.main.get().output)

    manifest {
        attributes(mapOf("Main-Class" to "ms.ais.weather.api.Application"))
    }

    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
}
