package com.hackathon.ticket.domain.repository

import com.hackathon.ticket.domain.entities.Reason

interface ReasonRepository {
    fun list(): List<Reason>?
}