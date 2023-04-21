package com.hackathon

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(
	scanBasePackages = [
		"com.hackathon",
		"org.jetbrains.exposed.spring",
	],
)
class HackathonApplication

fun main(args: Array<String>) {
	runApplication<HackathonApplication>(*args)
}
