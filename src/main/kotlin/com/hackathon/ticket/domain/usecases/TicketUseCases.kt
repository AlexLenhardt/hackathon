package com.hackathon.ticket.domain.usecases

import com.hackathon.ticket.domain.entities.Ticket
import com.hackathon.ticket.domain.usecases.response.ListTicketResponse
import com.hackathon.ticket.domain.usecases.response.TicketResponse
import com.hackathon.user.domain.entities.User
import java.util.*

interface TicketUseCases {

    fun getAll(user: User): ListTicketResponse

    fun getTicket(uuid: UUID): TicketResponse

    fun addTicket(ticket: Ticket?): TicketResponse
}