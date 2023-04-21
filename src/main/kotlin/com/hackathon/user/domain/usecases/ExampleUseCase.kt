package com.hackathon.user.domain.usecases

import com.hackathon.example.domain.entities.Example
import com.hackathon.example.domain.usecases.response.ExampleResponse
import com.hackathon.user.domain.entities.User
import com.hackathon.example.domain.usecases.response.UserResponse


interface ExampleUseCase {
     fun create (example: Example?): ExampleResponse
}