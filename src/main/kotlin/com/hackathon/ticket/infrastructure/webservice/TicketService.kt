package com.hackathon.ticket.infrastructure.webservice

import com.hackathon.ticket.domain.entities.Ticket
import com.hackathon.ticket.domain.usecases.TicketUseCases
import com.hackathon.ticket.domain.usecases.response.TicketResponse
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/ticket")
class TicketService(
        val ticketUseCases: TicketUseCases
) {
    @PostMapping
    fun addTicket(@RequestBody ticket: Ticket?): TicketResponse {
        return ticketUseCases.addTicket(ticket)
    }
}