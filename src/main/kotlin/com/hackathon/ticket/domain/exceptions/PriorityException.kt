package com.hackathon.ticket.domain.exceptions

import com.hackathon.shared.GenericError

val PRIORITY_DATABASE_ERROR = PriorityException("PRIORITY_DATABASE_ERROR", "An error occurred on priority database.")

class PriorityException(
    code: String,
    description: String
) : GenericError("reason-module", code, description)