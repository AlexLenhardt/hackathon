package com.hackathon.ticket.domain.usecases

import com.hackathon.ticket.domain.entities.Ticket
import com.hackathon.ticket.domain.usecases.response.TicketResponse

interface TicketUseCases {

    fun getAll(): List<Ticket>?

    fun addTicket(ticket: Ticket?): TicketResponse
}