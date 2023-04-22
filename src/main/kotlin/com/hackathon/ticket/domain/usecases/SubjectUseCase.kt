package com.hackathon.ticket.domain.usecases

import com.hackathon.ticket.domain.usecases.response.SubjectResponse
import java.util.UUID

interface SubjectUseCase {

    fun list(responseUUID: UUID): SubjectResponse
}