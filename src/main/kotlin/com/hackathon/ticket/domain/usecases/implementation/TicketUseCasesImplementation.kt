package com.hackathon.ticket.domain.usecases.implementation

import com.hackathon.ticket.domain.entities.Ticket
import com.hackathon.ticket.domain.exceptions.*
import com.hackathon.ticket.domain.repository.TicketRepository
import com.hackathon.ticket.domain.usecases.TicketUseCases
import com.hackathon.ticket.domain.usecases.response.TicketResponse
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service

@Repository
class TicketUseCasesImplementation(
        val ticketRepository: TicketRepository
) : TicketUseCases {
    override fun getAll(): List<Ticket>? {
        TODO("Not yet implemented")
    }

    override fun addTicket(ticket: Ticket?): TicketResponse {

        if(ticket == null){
            TICKET_NOT_FOUND
        }else{
            if(ticket.title == null){
                TITLE_NOT_FOUND
            }
            if(ticket.priority == null){
                PRIORITY_NOT_FOUND
            }
            if(ticket.user == null){
                USER_NOT_FOUND
            }
            if(ticket.contact == null){
                CONTACT_NOT_FOUND
            }
            if (ticket.reason == null){
                REASON_NOT_FOUND
            }
        }
        ticketRepository.addTicket(ticket!!)

        TODO("Not yet implemented")
    }
}