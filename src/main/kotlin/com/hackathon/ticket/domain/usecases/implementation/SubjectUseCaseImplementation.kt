package com.hackathon.ticket.domain.usecases.implementation

import com.hackathon.ticket.domain.exception.SUBJECT_DATABASE_ERROR
import com.hackathon.ticket.domain.repository.SubjectRepository
import com.hackathon.ticket.domain.usecases.SubjectUseCase
import com.hackathon.ticket.domain.usecases.response.SubjectResponse
import org.springframework.stereotype.Service
import java.util.*

@Service
class SubjectUseCaseImplementation (
    val subjectRepository: SubjectRepository
): SubjectUseCase {
    override fun list(reasonUUID: UUID): SubjectResponse {
        return try {
            SubjectResponse(subject = subjectRepository.list(reasonUUID))
        }catch (e: Exception){
            return SubjectResponse(error = SUBJECT_DATABASE_ERROR)
        }
    }
}