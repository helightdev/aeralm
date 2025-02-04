package dev.helight.aeralm

import io.github.bonigarcia.wdm.WebDriverManager
import io.github.bonigarcia.wdm.webdriver.WebDriverCreator
import io.ktor.server.application.*
import org.ktorm.database.Database
import org.ktorm.support.sqlite.SQLiteDialect
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    val db = Database.connect(
        "jdbc:sqlite:aeralm.db",
    )

    WebDriverManager.chromedriver()
        .setup()
    val driver = ChromeDriver(ChromeOptions().apply {
        addArguments("--headless")
    })
    driver.get("https://html.duckduckgo.com/html?q=test&s=10&kl=de-de")
    val content = driver.executeScript("return document.documentElement.outerHTML") as String
    println(content)
    driver.quit()
    println("Driver quit")

    configureSecurity()
    configureHTTP()
    configureSerialization()
    configureSockets()
    configureRouting()
}
