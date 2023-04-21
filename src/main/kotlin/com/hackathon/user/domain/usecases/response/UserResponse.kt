package com.hackathon.user.domain.usecases.response

import com.hackathon.user.domain.entities.User
import com.hackathon.shared.GenericError

class UserResponse(
    val user: User? = null,
    val error: GenericError? = null,
)