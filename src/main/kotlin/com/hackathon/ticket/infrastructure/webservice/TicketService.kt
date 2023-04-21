package com.hackathon.ticket.infrastructure.webservice

import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/ticket")
class TicketService {

    @GetMapping
    fun test() : String{
        return "hello";
    }

   // @PostMapping
  //  fun addTicket(@RequestBody ticket: Ticket?) {
        //
   // }
//}

}