package com.hackathon.example.infrastructure.repository.database


import org.jetbrains.exposed.sql.*


object ExampleDatabase : Table("example") {
    var uuid = uuid("uuid").uniqueIndex()
}