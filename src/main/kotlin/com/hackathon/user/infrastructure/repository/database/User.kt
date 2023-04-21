package com.hackathon.user.infrastructure.repository.database


import org.jetbrains.exposed.sql.*


object UserDatabase : Table("user") {
    var uuid = uuid("uuid").uniqueIndex()
    var name = varchar("name", 100)
}