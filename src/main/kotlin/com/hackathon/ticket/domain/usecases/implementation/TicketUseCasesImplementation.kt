package com.hackathon.ticket.domain.usecases.implementation

import com.hackathon.ticket.domain.entities.Ticket
import com.hackathon.ticket.domain.exceptions.TICKET_DATABASE_ERROR
import com.hackathon.ticket.domain.exceptions.TICKET_NOT_FOUND
import com.hackathon.ticket.domain.repository.TicketRepository
import com.hackathon.ticket.domain.usecases.TicketUseCases
import com.hackathon.ticket.domain.usecases.response.ListTicketResponse
import com.hackathon.user.domain.entities.User
import com.hackathon.user.domain.exceptions.USER_NOT_ALLOWED
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class TicketUseCasesImplementation(
    val ticketRepository: TicketRepository
) : TicketUseCases {
    override fun getAll(user: User): ListTicketResponse {
        return try {

            if (user.isColaborator()) {
                ListTicketResponse(
                    tickets = ticketRepository.listAllTicket(user.uuid!!)
                )
            } else if (user.isManager()) {
                ListTicketResponse(
                    tickets = ticketRepository.listAllTicketApproval(user.uuid!!)
                )
            } else {
                ListTicketResponse(error = USER_NOT_ALLOWED)
            }
        } catch (e: Exception) {
            ListTicketResponse(error = TICKET_DATABASE_ERROR)
        }
    }

    override fun getTicket(uuid: UUID): Ticket {
        val ticket = ticketRepository.findByUUID(uuid);
        if (ticket == null) {
            TICKET_NOT_FOUND
        }

        return ticket!!
    }
}