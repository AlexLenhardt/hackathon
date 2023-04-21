package com.hackathon.ticket.domain.exceptions

import com.hackathon.shared.GenericError

val TICKET_NOT_FOUND = TicketExceptions("TICKET_NOT_FOUND", "Ticket not found")

val TICKET_DATABASE_ERROR = TicketExceptions("TICKET_DATABASE_ERROR", "An error occurred on ticket database.")


class TicketExceptions(
    code: String,
    description: String
) : GenericError("user-module", code, description)