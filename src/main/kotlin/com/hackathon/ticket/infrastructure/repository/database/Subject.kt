package com.hackathon.ticket.infrastructure.repository.database

import org.jetbrains.exposed.sql.Table

object SubjectDatabase : Table("subject") {
    var uuid = uuid("uuid").uniqueIndex()
    var description = varchar("description", 100)
}