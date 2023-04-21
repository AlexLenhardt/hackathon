package com.hackathon.ticket.domain.usecases

import com.hackathon.ticket.domain.usecases.response.ListReasonResponse

interface ReasonUseCase {
    fun list(): ListReasonResponse
}