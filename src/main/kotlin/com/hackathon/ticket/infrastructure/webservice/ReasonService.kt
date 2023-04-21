package com.hackathon.ticket.infrastructure.webservice

import com.hackathon.ticket.domain.usecases.ReasonUseCase
import com.hackathon.ticket.domain.usecases.response.ListReasonResponse
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin("*")
@RequestMapping("/reason")
class ReasonService(
    var reasonUseCase: ReasonUseCase
) {
    @GetMapping("/list")
    fun list(): ListReasonResponse {
        return reasonUseCase.list()
    }
}