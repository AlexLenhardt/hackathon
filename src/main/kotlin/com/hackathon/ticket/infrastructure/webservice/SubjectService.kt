package com.hackathon.ticket.infrastructure.webservice

import com.hackathon.ticket.domain.usecases.SubjectUseCase
import com.hackathon.ticket.domain.usecases.response.SubjectResponse
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@CrossOrigin("*")
@RequestMapping("/subject")
class SubjectService (
    var subjectUseCase: SubjectUseCase
){
    @GetMapping("/{uuid}")
    fun list(
        @PathVariable("uuid") responseUUID: UUID,
    ): SubjectResponse {
        return subjectUseCase.list(responseUUID)
    }
}