package com.hackathon.ticket.domain.usecases.implementation

import com.hackathon.ticket.domain.entities.Ticket
import com.hackathon.ticket.domain.exceptions.TICKET_NOT_FOUND
import com.hackathon.ticket.domain.repository.TicketRepository
import com.hackathon.ticket.domain.usecases.TicketUseCases
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class TicketUseCasesImplementation(val ticketRepository: TicketRepository) : TicketUseCases{
    override fun getAll(): List<Ticket> {
        return ticketRepository.listAllTicket()
    }

    override fun getTicket(uuid: UUID): Ticket {
        val ticket = ticketRepository.findByUUID(uuid);
        if(ticket == null){
            TICKET_NOT_FOUND
        }

        return ticket!!
    }
}