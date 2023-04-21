package com.hackathon.ticket.infrastructure.repository.database

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date

object SituationTicketDatabase : Table("situationTicket")  {
    var uuid = uuid("uuid").uniqueIndex()
    var ticketUUID = uuid("uuid_ticket")
    var situationUUID = uuid("uuid_situation")
    var userUUID = uuid("uuid_user")
    var create_at = date("create_at")
}