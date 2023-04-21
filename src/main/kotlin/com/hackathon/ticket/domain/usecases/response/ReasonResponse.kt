package com.hackathon.ticket.domain.usecases.response

import com.hackathon.shared.GenericError
import com.hackathon.ticket.domain.entities.Reason

class ListReasonResponse (
    var reasons: List<Reason>? = null,
    var error: GenericError? = null
)