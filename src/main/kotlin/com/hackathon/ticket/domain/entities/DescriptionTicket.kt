package com.hackathon.ticket.domain.entities

import com.hackathon.user.domain.entities.User
import java.time.LocalDateTime
import java.util.UUID

class DescriptionTicket (
    var uuid: UUID? = null,
    var user: User? = null,
    var situation: Situation? = null,
    var create_at: LocalDateTime? = null,
    var description: String? = null
)