package com.hackathon.ticket.infrastructure.webservice

import com.hackathon.ticket.domain.usecases.TicketUseCases
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/ticket")
class TicketService (val ticketUseCases: TicketUseCases) {

    @GetMapping
    fun test() : String{
        return "hello";
    }
}