package com.hackathon.ticket.domain.repository

import com.hackathon.ticket.domain.entities.Reason

interface ReasonRepository {
    fun list(isInfrastructure: Boolean?): List<Reason>?
}