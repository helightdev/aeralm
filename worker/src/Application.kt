package dev.helight.aeralm

import com.microsoft.playwright.BrowserType
import com.microsoft.playwright.Playwright
import com.microsoft.playwright.impl.driver.Driver
import io.ktor.server.application.*
import org.ktorm.database.Database
import java.io.File
import java.io.IOException
import java.util.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    val db = Database.connect(
        "jdbc:sqlite:aeralm.db",
    )

    val playwrightEnv = mapOf(
        "PLAYWRIGHT_BROWSERS_PATH" to File("browsers").absolutePath,
        "PLAYWRIGHT_SKIP_BROWSER_DOWNLOAD" to "1"
    )

    println("Installing playwright")
    playwrightMain(arrayOf("install-deps"), playwrightEnv)
    println("Installed playwright dependencies")
    playwrightMain(arrayOf("install", "chromium"), playwrightEnv)
    println("Installed chromium")
    Playwright.create(Playwright.CreateOptions().setEnv(playwrightEnv)).use {
        val chrome = it
            .chromium()
            .launch(BrowserType.LaunchOptions()
                .setEnv(System.getenv() + playwrightEnv)
                .setHeadless(true))
        val page = chrome.newPage()
        page.waitForLoadState()
        page.navigate("https://html.duckduckgo.com/html?q=Schwarzwald&s=10&kl=de-de")
        println(page.content())
    }


    configureSecurity()
    configureHTTP()
    configureSerialization()
    configureSockets()
    configureRouting()
}

private fun playwrightMain(args: Array<String>, env: Map<String, String>) {
    val driver = Driver.ensureDriverInstalled(emptyMap(), false)
    val pb = driver.createProcessBuilder()
    pb.command().addAll(listOf(*args))
    val version = Playwright::class.java.getPackage().implementationVersion
    if (version != null) {
        pb.environment()["PW_CLI_DISPLAY_VERSION"] = version
    }
    pb.environment().putAll(env)
    pb.inheritIO()
    val process = pb.start()
    process.waitFor()
}
