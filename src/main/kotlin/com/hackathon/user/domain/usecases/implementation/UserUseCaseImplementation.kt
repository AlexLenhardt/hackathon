package com.hackathon.user.domain.usecases.implementation

import com.hackathon.example.domain.usecases.UserUseCase
import com.hackathon.example.domain.usecases.response.UserResponse
import com.hackathon.user.domain.exceptions.USER_NOT_FOUND
import com.hackathon.user.domain.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserUseCaseImplementation(
    val userRepository: UserRepository
) : UserUseCase {
    override fun get(userName: String?): UserResponse {
        if (userName.isNullOrBlank()) {
            return UserResponse(
                error = USER_NOT_FOUND
            )
        }
        val user = userRepository.get(userName)
        return UserResponse(user = user)
    }
}