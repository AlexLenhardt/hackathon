package com.hackathon.ticket.infrastructure.repository.database

import com.hackathon.user.infrastructure.repository.database.UserDatabase
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

object TicketDatabase : Table("ticket") {
    var uuid = uuid("uuid").uniqueIndex()
    var number = integer("number").autoIncrement()
    var reasonUUID =  reference("uuid_reason", ReasonDatabase.uuid)
    var title = varchar("title", 30)
    var priorityUUID = reference("uuid_priority", PriorityDatabase.uuid)
    var userUUID = reference("uuid_user", UserDatabase.uuid)
    var situationUUID = reference("uuid_situation", SituationDatabase.uuid)
    var modified_at = datetime("modified_at")
    var create_at = datetime("create_at")
}