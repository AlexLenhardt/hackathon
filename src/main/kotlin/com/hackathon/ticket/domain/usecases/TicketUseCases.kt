package com.hackathon.ticket.domain.usecases

import com.hackathon.ticket.domain.entities.Ticket

interface TicketUseCases {

    fun getAll(): List<Ticket>?
}