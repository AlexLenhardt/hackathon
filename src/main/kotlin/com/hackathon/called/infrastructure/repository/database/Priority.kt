package com.hackathon.called.infrastructure.repository.database

import org.jetbrains.exposed.sql.Table
object PriorityDatabase : Table("priority") {
    var uuid = uuid("uuid").uniqueIndex()
    var description = varchar("description", 30)
}