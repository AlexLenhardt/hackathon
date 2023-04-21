package com.hackathon.ticket.infrastructure.webservice

import com.hackathon.example.domain.usecases.UserUseCase
import com.hackathon.ticket.domain.entities.Ticket
import com.hackathon.ticket.domain.usecases.TicketUseCases
import com.hackathon.ticket.domain.usecases.response.ListTicketResponse
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@CrossOrigin("*")
@RequestMapping("/ticket")
class TicketService(
    val ticketUseCases: TicketUseCases,
    val userUseCase: UserUseCase,
) {

    @GetMapping
    fun listAllTickets(
        @RequestHeader("Authorization") authorization: String,
    ): ListTicketResponse {
        val user = userUseCase.get(authorization).user
        return ticketUseCases.getAll(user!!)
    }

    @GetMapping("/{uuid}")
    fun listTicket(@PathVariable(value = "uuid") uuid: UUID): Ticket {
        return ticketUseCases.getTicket(uuid)
    }
}