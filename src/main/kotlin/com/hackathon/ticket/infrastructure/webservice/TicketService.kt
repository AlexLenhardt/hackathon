package com.hackathon.ticket.infrastructure.webservice

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/ticket")
class TicketService {

    @GetMapping
    fun test() : String{
        return "hello";
    }
}