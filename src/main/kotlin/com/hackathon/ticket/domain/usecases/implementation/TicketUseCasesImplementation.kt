package com.hackathon.ticket.domain.usecases.implementation

import com.hackathon.ticket.domain.entities.Ticket
import com.hackathon.ticket.domain.repository.TicketRepository
import com.hackathon.ticket.domain.usecases.TicketUseCases
import org.springframework.stereotype.Service

@Service
class TicketUseCasesImplementation(val ticketRepository: TicketRepository) : TicketUseCases{
    override fun getAll(): List<Ticket>? {
        TODO("Not yet implemented")
    }
}