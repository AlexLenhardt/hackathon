package com.hackathon.ticket.domain.usecases.implementation

import com.hackathon.ticket.domain.entities.Reprove
import com.hackathon.ticket.domain.entities.Situation
import com.hackathon.ticket.domain.entities.Situation.Companion.pendentApproval
import com.hackathon.ticket.domain.entities.Ticket
import com.hackathon.ticket.domain.exceptions.TICKET_DATABASE_ERROR
import com.hackathon.ticket.domain.exceptions.TICKET_NOT_FOUND
import com.hackathon.ticket.domain.exceptions.*
import com.hackathon.ticket.domain.repository.TicketRepository
import com.hackathon.ticket.domain.usecases.TicketUseCases
import com.hackathon.ticket.domain.usecases.response.ListTicketResponse
import com.hackathon.user.domain.entities.User
import com.hackathon.user.domain.exceptions.USER_NOT_ALLOWED
import com.hackathon.ticket.domain.usecases.response.TicketResponse
import com.hackathon.ticket.infrastructure.repository.database.DescriptionTicketDatabase
import com.hackathon.user.domain.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class TicketUseCasesImplementation(
    val ticketRepository: TicketRepository,
    val userRepository: UserRepository
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
                println("else")
                ListTicketResponse(error = USER_NOT_ALLOWED)
            }
        } catch (e: Exception) {
            println(e)
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
            TicketResponse(error = TICKET_NOT_FOUND)
        } else {
            if (ticket.title == null) {
                TITLE_NOT_FOUND
            }
            if (ticket.priority == null) {
                PRIORITY_NOT_FOUND
            }
            if (ticket.user == null) {
                USER_NOT_FOUND
            }
            if (ticket.contact == null) {
                CONTACT_NOT_FOUND
            }
            if (ticket.reason == null) {
                REASON_NOT_FOUND
            }
        }
        ticketRepository.addTicket(ticket!!)

        TODO("Not yet implemented")
    }

    override fun approval(uuid: UUID, userName: String): TicketResponse {
        return try {
            val user = userRepository.get(userName)
            if (!user!!.isManager()) {
                return TicketResponse(error = USER_NOT_ALLOWED)
            }

            val ticket = ticketRepository.findByUUID(uuid)
            if (ticket!!.situation!!.code != pendentApproval) {
                return TicketResponse(error = TICKET_DOESNT_NEED_APPROVAL)
            }

            ticketRepository.approval(uuid, user.uuid!!)

            TicketResponse()
        } catch (e: Exception) {
            return TicketResponse(error = TICKET_DATABASE_ERROR)
        }
    }

    override fun reprove(uuid: UUID, userName: String, reprove: Reprove?): TicketResponse {
        return try {
            val user = userRepository.get(userName)
            if (!user!!.isManager()) {
                return TicketResponse(error = USER_NOT_ALLOWED)
            }

            val ticket = ticketRepository.findByUUID(uuid)
            if (ticket!!.situation!!.code != pendentApproval) {
                return TicketResponse(error = TICKET_DOESNT_NEED_APPROVAL)
            }

            if (reprove == null) {
                return TicketResponse(error = SITUATION_NOT_ACCEPTED)
            }

            when (reprove.situation!!.code) {
                Situation.reproved -> if (reprove.description.isNullOrBlank()) {
                    return TicketResponse(error = DESCRIPTION_CANNOT_BE_EMPTY)
                } else {
                    ticketRepository.reprove(uuid, user.uuid!!, reprove.description!!)
                }
                Situation.excluded -> ticketRepository.exclude(uuid, user.uuid!!, reprove.description!!)
                Situation.approved -> ticketRepository.approval(uuid, user.uuid!!)

                else -> return TicketResponse(error = SITUATION_NOT_ACCEPTED)
            }

            TicketResponse()
        } catch (e: Exception) {
            println(e)
            return TicketResponse(error = TICKET_DATABASE_ERROR)
        }
    }

    override fun editTicket(uuid: UUID, userName: String, ticket: Ticket?): TicketResponse {
        try {
            val user = userRepository.get(userName)
            if (user!!.isManager()) {
                return TicketResponse(error = TICKET_DOESNT_MANAGER_UPDATE)
            }
            val ticket = ticketRepository.findByUUID(uuid)
            if (ticket == null) {
                return TicketResponse(error = TICKET_NOT_FOUND)
            } else {

            }
        } catch (e: Exception) {
            return TicketResponse(error = TICKET_DATABASE_ERROR)
        }

        return TicketResponse()
    }
}