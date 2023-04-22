package com.hackathon.ticket.domain.usecases.response

import com.hackathon.shared.GenericError
import com.hackathon.ticket.domain.entities.Subject

class SubjectResponse(
    var subject: List<Subject>? = null,
    var error: GenericError? = null,
)