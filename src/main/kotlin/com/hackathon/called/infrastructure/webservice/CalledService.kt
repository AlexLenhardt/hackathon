package com.hackathon.called.infrastructure.webservice

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/called")
class CalledService {

    @GetMapping
    fun test() : String{
        return "hello";
    }
}