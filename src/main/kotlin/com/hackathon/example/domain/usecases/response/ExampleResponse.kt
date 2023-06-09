package com.hackathon.example.domain.usecases.response

import com.hackathon.example.domain.entities.Example
import com.hackathon.shared.GenericError

class ExampleResponse(
    val example: Example? = null,
    val error: GenericError? = null,
)