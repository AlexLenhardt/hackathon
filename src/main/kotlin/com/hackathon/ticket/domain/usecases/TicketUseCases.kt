package com.hackathon.ticket.domain.usecases

import com.hackathon.ticket.domain.entities.Ticket
import java.util.UUID

interface TicketUseCases {

    fun getAll(): List<Ticket>

    fun getTicket(uuid: UUID): Ticket
}