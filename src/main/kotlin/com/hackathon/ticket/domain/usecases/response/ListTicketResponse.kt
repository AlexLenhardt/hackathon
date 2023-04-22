package com.hackathon.ticket.domain.usecases.response

import com.hackathon.shared.GenericError
import com.hackathon.ticket.domain.entities.Ticket

class ListTicketResponse(
    var tickets: List<Ticket>? = null,
    var error: GenericError? = null,
)