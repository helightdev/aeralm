tasks.register<Exec>("chromeRun") {
    group = "run"
    runFlutterCommand( "run", "-d", "chrome")
}

tasks.register<Exec>("pubGet") {
    group = "build"
    runFlutterCommand("pub", "get")
}

tasks.register<Exec>("pubUpgrade") {
    group = "build"
    runFlutterCommand("pub", "upgrade")
}

fun Exec.runFlutterCommand(vararg args: String) {
    if (System.getProperty("os.name").toLowerCase().contains("windows")) {
        commandLine("cmd", "/c", "flutter", *args)
    } else {
        commandLine("bash", "-c", "flutter", *args)
    }
}