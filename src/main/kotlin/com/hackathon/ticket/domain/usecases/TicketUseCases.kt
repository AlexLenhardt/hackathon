package com.hackathon.ticket.domain.usecases

import com.hackathon.ticket.domain.entities.Reprove
import com.hackathon.ticket.domain.entities.Ticket
import com.hackathon.ticket.domain.usecases.response.ListTicketResponse
import com.hackathon.ticket.domain.usecases.response.TicketResponse
import com.hackathon.user.domain.entities.User
import java.util.*

interface TicketUseCases {

    fun getAll(user: User): ListTicketResponse
    fun getTicket(uuid: UUID): TicketResponse
    fun addTicket(ticket: Ticket?): TicketResponse
    fun approval(uuid: UUID, user: String): TicketResponse
    fun reprove(uuid: UUID, user: String, reprove: Reprove?): TicketResponse
    fun editTicket(uuid: UUID, userName: String, ticket: Ticket) : TicketResponse
}