package com.hackathon.example.domain.repository

import com.hackathon.example.domain.entities.Example
import java.util.*

interface ExampleRepository {
    fun create(uuid: UUID): Example
}