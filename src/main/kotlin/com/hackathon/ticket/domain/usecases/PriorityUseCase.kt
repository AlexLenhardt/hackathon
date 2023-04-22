package com.hackathon.ticket.domain.usecases

import com.hackathon.ticket.domain.usecases.response.PriorityResponse
import java.util.*

interface PriorityUseCase {

    fun list(): PriorityResponse
}