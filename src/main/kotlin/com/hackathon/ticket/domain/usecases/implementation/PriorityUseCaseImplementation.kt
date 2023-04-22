package com.hackathon.ticket.domain.usecases.implementation

import com.hackathon.ticket.domain.exceptions.PRIORITY_DATABASE_ERROR
import com.hackathon.ticket.domain.repository.PriorityRepository
import com.hackathon.ticket.domain.usecases.PriorityUseCase
import com.hackathon.ticket.domain.usecases.response.PriorityResponse
import org.springframework.stereotype.Service
import java.util.*

@Service
class PriorityUseCaseImplementation (
    val priorityRepository: PriorityRepository
): PriorityUseCase{

    override fun list(): PriorityResponse {
        return try {
            PriorityResponse(priority = priorityRepository.list())
        }catch (e: Exception){
            return PriorityResponse(error = PRIORITY_DATABASE_ERROR)
        }
    }
}