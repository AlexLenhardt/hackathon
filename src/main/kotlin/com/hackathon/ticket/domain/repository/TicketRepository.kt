package com.hackathon.ticket.domain.repository

import com.hackathon.ticket.domain.entities.Ticket

interface TicketRepository {

    fun listAllTicket(): List<Ticket>?
}