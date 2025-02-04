import java.util.*

val appName: String by project
val appVersion: String by project
val appMain: String by project
val osName = System.getProperty("os.name").lowercase()

tasks.register<Exec>("packageApp") {
    group = "distribution"

    dependsOn("installDist") // Ensures the application is built first

    val buildDir = layout.buildDirectory.get()

    val distFolder = "${buildDir}/dist"
    if (File(distFolder).exists()) {
        File(distFolder).deleteRecursively()
    }

    val additionalArgs = mutableListOf<String>()
    // if windows add the win-console flag
    if (osName.contains("windows")) {
        additionalArgs.add("--win-console")
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
        *additionalArgs.toTypedArray()
    )
    println("Packaging application")

}