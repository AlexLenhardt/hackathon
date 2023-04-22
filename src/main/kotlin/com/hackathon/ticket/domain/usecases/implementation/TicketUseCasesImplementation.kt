package com.hackathon.ticket.domain.usecases.implementation

import com.hackathon.ticket.domain.entities.Reprove
import com.hackathon.ticket.domain.entities.Situation
import com.hackathon.ticket.domain.entities.Situation.Companion.approved
import com.hackathon.ticket.domain.entities.Situation.Companion.excluded
import com.hackathon.ticket.domain.entities.Situation.Companion.pendentApproval
import com.hackathon.ticket.domain.entities.Ticket
import com.hackathon.ticket.domain.exception.SUBJECT_DATABASE_ERROR
import com.hackathon.ticket.domain.exceptions.*
import com.hackathon.ticket.domain.repository.ReasonRepository
import com.hackathon.ticket.domain.repository.TicketRepository
import com.hackathon.ticket.domain.usecases.TicketUseCases
import com.hackathon.ticket.domain.usecases.response.ListTicketResponse
import com.hackathon.ticket.domain.usecases.response.TicketResponse
import com.hackathon.user.domain.entities.User
import com.hackathon.user.domain.exceptions.USER_NOT_ALLOWED
import com.hackathon.user.domain.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.*
import java.time.LocalDateTime
import java.util.UUID

@Service
class TicketUseCasesImplementation(
        val ticketRepository: TicketRepository,
        val reasonRepository: ReasonRepository,
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
            return TicketResponse(error = TICKET_NOT_FOUND)
        }
        if (ticket.title == null) {
            return TicketResponse(error = TITLE_NOT_FOUND)
        }
        if (ticket.priority!!.uuid == null) {
            return TicketResponse(error = PRIORITY_NOT_FOUND)
        }
        if (ticket.user!!.uuid == null) {
            return TicketResponse(error = USER_NOT_FOUND)
        }
        if (ticket.contact == null) {
            return TicketResponse(error = CONTACT_NOT_FOUND)
        }
        if (ticket.reason!!.uuid == null) {
            return TicketResponse(error = REASON_NOT_FOUND)
        }
        if (ticket.subject!!.uuid == null) {
            return TicketResponse(error = SUBJECT_DATABASE_ERROR)
        }

        val reason = reasonRepository.get(ticket.reason!!.uuid!!)

        if (reason!!.needsApproval!!) {
            ticket.situation = ticketRepository.getSituationByCode(pendentApproval)!!
        } else {
            ticket.situation = ticketRepository.getSituationByCode(approved)!!
        }

        ticketRepository.addTicket(ticket.copy(uuid = UUID.randomUUID()))
        return TicketResponse()
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
            return TicketResponse(error = TICKET_DATABASE_ERROR)
        }
    }

    override fun editTicket(uuid: UUID, userName: String, ticket: Ticket): TicketResponse {
        try {
            val user = userRepository.get(userName)
            if (user!!.isManager()) {
                return TicketResponse(error = TICKET_DOESNT_MANAGER_UPDATE)
            }

            val bdTicket = ticketRepository.findByUUID(uuid) ?: return TicketResponse(error = TICKET_NOT_FOUND)

            if (user.uuid != bdTicket.user!!.uuid) {
                return TicketResponse(error = TICKET_USER_DIFFERENT)
            }
            if (bdTicket.situation!!.code == approved || bdTicket.situation!!.code == excluded){
                return TicketResponse(error = SITUATION_NOT_ACCEPTED)
            }
            val response = validadesTicket(ticket)
            if (response != null) {
                return response
            }
            ticketRepository.updateTicket(ticket)
            return TicketResponse()
        } catch (e: Exception) {
            return TicketResponse(error = TICKET_DATABASE_ERROR)
        }
    }

    private fun validadesTicket(ticket: Ticket): TicketResponse? {

        if (ticket.reason?.uuid == null) {
            return TicketResponse(error = REASON_NOT_FOUND)
        }
        if (ticket.title == null) {
            return TicketResponse(error = TITLE_NOT_FOUND)
        }
        if (ticket.priority?.uuid == null) {
            return TicketResponse(error = PRIORITY_NOT_FOUND)
        }
        if (ticket.contact == null) {
            return TicketResponse(error = CONTACT_NOT_FOUND)
        }
        return null
    }
}