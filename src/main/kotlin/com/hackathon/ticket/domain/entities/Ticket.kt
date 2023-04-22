package com.hackathon.ticket.domain.entities

import com.hackathon.user.domain.entities.User
import java.time.LocalDateTime
import java.util.UUID

class Ticket(
    var uuid : UUID? = null,
    var number: Int? = null,
    var reason: Reason? = null,
    var title: String? = null,
    var priority: Priority? = null,
    var user: User? = null,
    var situation: Situation? = null,
    var modified_at: LocalDateTime? = null,
    var create_at: LocalDateTime? = null,
    var contact: String? = null,
    var descriptions: List<DescriptionTicket>? = null
)