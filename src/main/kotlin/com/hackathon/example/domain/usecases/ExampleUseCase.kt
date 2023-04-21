package com.hackathon.example.domain.usecases

import com.hackathon.example.domain.entities.Example
import com.hackathon.example.domain.usecases.response.UserResponse
import java.util.UUID


interface UserUseCase {
     fun get(userName: String?): UserResponse
}