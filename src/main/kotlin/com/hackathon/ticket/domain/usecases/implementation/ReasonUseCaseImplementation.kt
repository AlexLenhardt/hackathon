package com.hackathon.ticket.domain.usecases.implementation

import com.hackathon.ticket.domain.exception.REASON_DATABASE_ERROR
import com.hackathon.ticket.domain.repository.ReasonRepository
import com.hackathon.ticket.domain.usecases.ReasonUseCase
import com.hackathon.ticket.domain.usecases.response.ListReasonResponse
import org.springframework.stereotype.Service

@Service
class ReasonUseCaseImplementation(
    var reasonRepository: ReasonRepository,
) : ReasonUseCase {
    override fun list(): ListReasonResponse {
        return try {
            ListReasonResponse(reasons = reasonRepository.list())
        }catch (e: Exception){
            return ListReasonResponse(error = REASON_DATABASE_ERROR)
        }
    }
}