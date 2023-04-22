package com.hackathon.ticket.domain.repository

import com.hackathon.ticket.domain.entities.Ticket
import java.util.UUID

interface TicketRepository {

    fun listAllTicket(userUUID: UUID): List<Ticket>

    fun findByUUID(uuid: UUID): Ticket?

    fun listAllTicketApproval(userUUID: UUID): List<Ticket>

    fun addTicket(ticket: Ticket)

    fun approval(ticketUUID: UUID, userUUID: UUID)

    fun updateTicket(ticket: Ticket)

    fun reprove(ticketUUID: UUID, userUUID: UUID, description: String)

    fun exclude(ticketUUID: UUID, userUUID: UUID, description: String)
}