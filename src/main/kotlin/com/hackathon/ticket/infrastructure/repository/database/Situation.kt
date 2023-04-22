package com.hackathon.ticket.infrastructure.repository.database

import org.jetbrains.exposed.sql.Table
object SituationDatabase : Table("situation") {
    var uuid = uuid("uuid").uniqueIndex()
    var code = integer("code").uniqueIndex()
    var description = varchar("description", 30)
}