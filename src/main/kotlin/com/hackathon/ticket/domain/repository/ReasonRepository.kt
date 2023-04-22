package com.hackathon.ticket.domain.repository

import com.hackathon.ticket.domain.entities.Reason
import java.util.UUID

interface ReasonRepository {
    fun list(isInfrastructure: Boolean?): List<Reason>?

    fun get(uuid: UUID): Reason?
}