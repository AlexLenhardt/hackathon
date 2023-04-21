package com.hackathon.example.infrastructure.webservice

import com.hackathon.example.domain.entities.Example
import com.hackathon.example.domain.usecases.ExampleUseCase
import com.hackathon.example.domain.usecases.response.ExampleResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/example")
class ExampleService(
    val exampleUseCase: ExampleUseCase
) {
    @PostMapping("")
    fun saveExample(@RequestBody example: Example?): ExampleResponse {
        return exampleUseCase.create(example)
    }

}