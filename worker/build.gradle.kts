val appName: String by project
val appVersion: String by project
val appMain: String by project

tasks.register<Exec>("packageApp") {
    group = "distribution"

    dependsOn("installDist") // Ensures the application is built first

    val buildDir = layout.buildDirectory.get()

    val distFolder = "${buildDir}/dist"
    if (File(distFolder).exists()) {
        File(distFolder).deleteRecursively()
    }

    // Get main class
    println("Main class: $appMain")
    commandLine(
        "jpackage",
        "--name", appName,
        "--input", "${buildDir}/install/$appName/lib/",
        "--main-jar", "${appName}-jvm.jar",
        "--main-class", appMain,
        "--type", "app-image",
        "--dest", "${buildDir}/dist",
        "--win-console",
    )
    println("Packaging application")

}