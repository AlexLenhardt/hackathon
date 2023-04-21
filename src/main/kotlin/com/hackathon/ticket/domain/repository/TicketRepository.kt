package com.hackathon.ticket.domain.repository

import com.hackathon.ticket.domain.entities.Ticket
import java.util.UUID

interface TicketRepository {

    fun listAllTicket(userUUID: UUID): List<Ticket>

    fun findByUUID(uuid: UUID): Ticket?

    fun listAllTicketApproval(userUUID: UUID): List<Ticket>
}