package com.hackathon.example.domain.usecases.response

import com.hackathon.shared.GenericError
import com.hackathon.user.domain.entities.User

class UserResponse(
        val user: User? = null,
        val error: GenericError? = null,
)