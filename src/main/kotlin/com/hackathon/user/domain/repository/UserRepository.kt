package com.hackathon.user.domain.repository

import com.hackathon.user.domain.entities.User

interface UserRepository {
    fun get(userName: String): User?
}