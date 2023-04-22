package com.hackathon.ticket.infrastructure.webservice

import com.hackathon.ticket.domain.usecases.PriorityUseCase
import com.hackathon.ticket.domain.usecases.response.PriorityResponse
import com.hackathon.ticket.domain.usecases.response.SubjectResponse
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@CrossOrigin("*")
@RequestMapping("/priority")
class PriorityService (
    val priorityUseCase: PriorityUseCase
){
    @GetMapping
    fun list(): PriorityResponse {
        return priorityUseCase.list()
    }
}