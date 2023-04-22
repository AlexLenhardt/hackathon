package com.hackathon.ticket.domain.usecases.response

import com.hackathon.shared.GenericError
import com.hackathon.ticket.domain.entities.Priority
class PriorityResponse(
    var priority: List<Priority>? = null,
    var error: GenericError? = null,
)