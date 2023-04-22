package com.hackathon.ticket.domain.usecases.implementation

import com.hackathon.ticket.domain.entities.Reason
import com.hackathon.ticket.domain.entities.Situation.Companion.opened
import com.hackathon.ticket.domain.entities.Situation.Companion.pendentApproval
import com.hackathon.ticket.domain.entities.Ticket
import com.hackathon.ticket.domain.exceptions.TICKET_DATABASE_ERROR
import com.hackathon.ticket.domain.exceptions.TICKET_NOT_FOUND
import com.hackathon.ticket.domain.exception.SITUATIONDENIED_DATABASE_ERROR
import com.hackathon.ticket.domain.exception.SITUATION_DATABASE_ERROR
import com.hackathon.ticket.domain.exceptions.*
import com.hackathon.ticket.domain.repository.TicketRepository
import com.hackathon.ticket.domain.usecases.TicketUseCases
import com.hackathon.ticket.domain.usecases.response.ListTicketResponse
import com.hackathon.user.domain.entities.User
import com.hackathon.user.domain.exceptions.USER_NOT_ALLOWED
import com.hackathon.ticket.domain.usecases.response.TicketResponse
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import java.util.UUID

@Repository
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

    override fun getTicket(uuid: UUID): TicketResponse {
        return try {
            val ticket = ticketRepository.findByUUID(uuid) ?: return TicketResponse(error = TICKET_NOT_FOUND);
            TicketResponse(ticket)
        } catch (e: Exception) {
            TicketResponse(error = TICKET_DATABASE_ERROR)
        }
    }

    override fun addTicket(ticket: Ticket?): TicketResponse {

        if (ticket == null) {
            return TicketResponse(error = TICKET_NOT_FOUND)
        }

        if (ticket.title == null) {
            TITLE_NOT_FOUND
        }
        if (ticket.priority.uuid == null) {
            PRIORITY_NOT_FOUND
        }
        if (ticket.user!!.uuid == null) {
            USER_NOT_FOUND
        }
        if (ticket.contact == null) {
            CONTACT_NOT_FOUND
        }
        if (ticket.reason!!.uuid == null) {
            REASON_NOT_FOUND
        }

        val reason = Reason()

        if (reason.needsApproval!!) {
            ticket.situation!!.code = pendentApproval
        } else {
            ticket.situation!!.code = opened
        }

        ticketRepository.addTicket(ticket)
        return TicketResponse()
    }
}