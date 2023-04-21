package com.hackathon.ticket.infrastructure.webservice

import com.hackathon.ticket.domain.entities.Ticket
import com.hackathon.ticket.domain.usecases.TicketUseCases
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@CrossOrigin("*")
@RequestMapping("/ticket")
class TicketService (val ticketUseCases: TicketUseCases) {

    @GetMapping
    fun listAllTickets() : List<Ticket>{
        return ticketUseCases.getAll()
    }

    @GetMapping("/{uuid}")
    fun listTicket(@PathVariable(value = "uuid") uuid : UUID): Ticket{
        return ticketUseCases.getTicket(uuid)
    }
}