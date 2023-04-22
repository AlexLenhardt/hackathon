package com.hackathon.ticket.infrastructure.webservice

import com.hackathon.ticket.domain.usecases.ReasonUseCase
import com.hackathon.ticket.domain.usecases.response.ListReasonResponse
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@CrossOrigin("*")
@RequestMapping("/reason")
class ReasonService(
    var reasonUseCase: ReasonUseCase
) {
    @GetMapping("/list")
    fun list(
        @RequestParam("isInfrastructure", required = false, defaultValue = "") isInfrastructure: Boolean?,
    ): ListReasonResponse {
        return reasonUseCase.list(isInfrastructure)
    }
}