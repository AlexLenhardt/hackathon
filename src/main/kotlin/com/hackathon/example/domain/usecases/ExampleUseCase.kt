package com.hackathon.example.domain.usecases

import com.hackathon.user.domain.usecases.response.UserResponse


interface UserUseCase {
     fun get(userName: String?): UserResponse
}