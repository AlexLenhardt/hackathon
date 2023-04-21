package com.hackathon.ticket.infrastructure.repository.database

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date

object TicketDatabase : Table("ticket") {
    var uuid = uuid("uuid").uniqueIndex()
    var number = integer("number").autoIncrement()
    var reasonUUID =  uuid("uuid_reason")
    var title = varchar("title", 30)
    var priorityUUID = uuid("uuid_priority")
    var userUUID = uuid("uuid_user")
    var situationUUID = uuid("uuid_situation")
    var modified_at = date("modified_at")
    var create_at = date("create_at")
}