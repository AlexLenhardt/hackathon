package com.hackathon.ticket.domain.entities

import com.hackathon.user.domain.entities.User
import java.time.LocalDateTime
import java.util.UUID

class Ticket(
    var uuid : UUID? = null,
    var number: Int? = null,
    var reason: Reason,
    var title: String? = null,
    var priority: Priority,
    var user: User,
    var situation: Situation,
    var modified_at: LocalDateTime? = null,
    var create_at: LocalDateTime? = null
)