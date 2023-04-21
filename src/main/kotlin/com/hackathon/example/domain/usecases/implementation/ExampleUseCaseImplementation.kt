package com.hackathon.example.domain.usecases.implementation

import com.hackathon.example.domain.entities.Example
import com.hackathon.example.domain.exceptions.EXAMPLE_ERROR
import com.hackathon.example.domain.repository.ExampleRepository
import com.hackathon.example.domain.usecases.ExampleUseCase
import com.hackathon.example.domain.usecases.response.ExampleResponse
import org.springframework.stereotype.Service
import java.util.*

@Service
class ExampleUseCaseImplementation(
    val exampleRepository: ExampleRepository
) : ExampleUseCase {
    override fun create(example: Example?): ExampleResponse {
        if (example == null) {
            return ExampleResponse(
                error = EXAMPLE_ERROR
            )
        }
        return ExampleResponse(
            example = exampleRepository.create(UUID.randomUUID())
        )
    }
}