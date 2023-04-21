package com.hackathon.ticket.infrastructure.repository.database

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date

object DescriptionTicketDatabase : Table("descriptionTicket") {
    var uuid = uuid("uuid").uniqueIndex()
    var userUUID = uuid("uuid_user")
    var create_at = date("create_at")
    var description = varchar("description", 60)
}