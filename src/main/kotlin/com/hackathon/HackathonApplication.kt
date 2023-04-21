package com.hackathon

import com.hackathon.called.infrastructure.repository.database.CalledDatabase
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HackathonApplication

fun main(args: Array<String>) {
	transaction {
		SchemaUtils.create(CalledDatabase)
	}
	runApplication<HackathonApplication>(*args)
}
