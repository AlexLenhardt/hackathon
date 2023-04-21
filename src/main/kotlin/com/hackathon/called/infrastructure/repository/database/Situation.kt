package com.hackathon.called.infrastructure.repository.database

import org.jetbrains.exposed.sql.Table
object SituationDatabase : Table("situation") {
    var uuid = uuid("uuid").uniqueIndex()
    var description = varchar("description", 30)
}