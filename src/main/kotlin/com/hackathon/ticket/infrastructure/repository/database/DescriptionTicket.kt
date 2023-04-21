package com.hackathon.ticket.infrastructure.repository.database

import com.hackathon.user.infrastructure.repository.database.UserDatabase
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date

object DescriptionTicketDatabase : Table("description_ticket") {
    var uuid = uuid("uuid").uniqueIndex()
    var ticketUUID = reference("uuid_ticket", TicketDatabase.uuid)
    var userUUID = reference("uuid_user", UserDatabase.uuid)
    var create_at = date("create_at")
    var description = varchar("description", 60)
}