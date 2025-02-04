package dev.helight.aeralm

import io.ktor.server.application.*
import org.ktorm.database.Database
import org.ktorm.support.sqlite.SQLiteDialect

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    val db = Database.connect(
        "jdbc:sqlite:aeralm.db",
    )

    configureSecurity()
    configureHTTP()
    configureSerialization()
    configureSockets()
    configureRouting()
}
