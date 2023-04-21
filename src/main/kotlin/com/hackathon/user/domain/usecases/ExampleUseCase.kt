package com.hackathon.user.domain.usecases

import com.hackathon.example.domain.entities.Example
import com.hackathon.example.domain.usecases.response.ExampleResponse


interface ExampleUseCase {
     fun create (example: Example?): ExampleResponse
}