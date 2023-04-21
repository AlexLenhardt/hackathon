package com.hackathon.ticket.domain.entities

import com.hackathon.user.domain.entities.User
import java.util.*

class Ticket (
        var uuid: UUID?,
        var number: Int?,
        var reasonUUID: Reason?,
        var title: String?,
        var priorityUUID: Priority?,
        var userUUID: User?,
        var situationUUID: Situation?,
        var modified_at: Date?,
        var create_at: Date?
)