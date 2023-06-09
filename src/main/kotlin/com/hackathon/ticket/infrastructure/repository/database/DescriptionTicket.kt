package com.hackathon.ticket.infrastructure.repository.database

import com.hackathon.user.infrastructure.repository.database.UserDatabase
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.CurrentDateTime
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.javatime.datetime

object DescriptionTicketDatabase : Table("description_ticket") {
    var uuid = uuid("uuid").uniqueIndex()
    var ticketUUID = reference("uuid_ticket", TicketDatabase.uuid)
    var situationUUID = reference("uuid_situation", SituationDatabase.uuid)
    var userUUID = reference("uuid_user", UserDatabase.uuid)
    var create_at = datetime("create_at").defaultExpression(CurrentDateTime)
    var description = text("description")
}