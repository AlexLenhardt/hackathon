package com.hackathon.ticket.infrastructure.repository.database

import org.jetbrains.exposed.sql.Table
object ReasonDatabase : Table("reason") {
    var uuid = uuid("uuid").uniqueIndex()
    var description = varchar("description", 30)
    var isInfrastructure = bool("is_infrastructure")
    var needsApproval = bool("needs_approval")
}