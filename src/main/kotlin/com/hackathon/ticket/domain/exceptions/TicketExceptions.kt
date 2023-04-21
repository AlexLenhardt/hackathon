package com.hackathon.ticket.domain.exceptions

import com.hackathon.shared.GenericError

val TICKET_NOT_FOUND = TicketExceptions("TICKET_NOT_FOUND", "This error is an example.")


class TicketExceptions(
    code: String,
    description: String
) : GenericError("user-module", code, description)