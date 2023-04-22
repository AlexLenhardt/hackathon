package com.hackathon.ticket.domain.repository

import com.hackathon.ticket.domain.entities.Subject
import java.util.UUID

interface SubjectRepository {

    fun list(reasonUUID: UUID): List<Subject>?
}