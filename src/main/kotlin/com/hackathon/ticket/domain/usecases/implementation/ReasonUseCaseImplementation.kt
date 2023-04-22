package com.hackathon.ticket.domain.usecases.implementation

import com.hackathon.ticket.domain.exceptions.REASON_DATABASE_ERROR
import com.hackathon.ticket.domain.repository.ReasonRepository
import com.hackathon.ticket.domain.usecases.ReasonUseCase
import com.hackathon.ticket.domain.usecases.response.ListReasonResponse
import org.springframework.stereotype.Service

@Service
class ReasonUseCaseImplementation(
    var reasonRepository: ReasonRepository,
) : ReasonUseCase {
    override fun list(isInfrastructure: Boolean?): ListReasonResponse {
        return try {
            ListReasonResponse(reasons = reasonRepository.list(isInfrastructure))
        }catch (e: Exception){
            return ListReasonResponse(error = REASON_DATABASE_ERROR)
        }
    }
}