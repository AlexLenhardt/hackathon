package com.hackathon.ticket.infrastructure.webservice

import com.hackathon.example.domain.usecases.UserUseCase
import com.hackathon.ticket.domain.entities.Ticket
import com.hackathon.ticket.domain.usecases.TicketUseCases
import com.hackathon.ticket.domain.usecases.response.ListTicketResponse
import com.hackathon.ticket.domain.usecases.response.TicketResponse
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@CrossOrigin("*")
@RequestMapping("/ticket")
class TicketService(
    val ticketUseCases: TicketUseCases,
    val userUseCase: UserUseCase,
) {
    @PostMapping
    fun addTicket(@RequestBody ticket: Ticket?): TicketResponse {
        return ticketUseCases.addTicket(ticket)
    }

    @GetMapping
    fun listAllTickets(
        @RequestHeader("Authorization") authorization: String,
    ): ListTicketResponse {
        val user = userUseCase.get(authorization).user
        return ticketUseCases.getAll(user!!)
    }

    @GetMapping("/{uuid}")
    fun listTicket(@PathVariable(value = "uuid") uuid: UUID): TicketResponse {
        return ticketUseCases.getTicket(uuid)
    }

    @PostMapping("/approval/{uuid}")
    fun approvalTicket(
        @PathVariable("uuid") uuid: UUID,
        @RequestHeader("Authorization") user: String,
        ): TicketResponse{
        return ticketUseCases.approval(uuid, user)
    }

    @PutMapping("/{uuid}")
    fun editTicket(
        @PathVariable("uuid") uuid: UUID,
        @RequestHeader("Authorization") user: String,
        @RequestBody ticket: Ticket
    ) : TicketResponse{
        return ticketUseCases.editTicket(uuid, user, ticket)
    }
}