package com.hackathon.ticket.domain.repository

import com.hackathon.ticket.domain.entities.Priority
import java.util.*

interface PriorityRepository {

    fun list(): List<Priority>?
}