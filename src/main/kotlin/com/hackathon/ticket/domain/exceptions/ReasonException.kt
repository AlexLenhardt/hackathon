package com.hackathon.ticket.domain.exceptions

import com.hackathon.shared.GenericError

val REASON_DATABASE_ERROR = ReasonException("REASON_DATABASE_ERROR", "An error occurred on reason database.")


class ReasonException(
    code: String,
    description: String
) : GenericError("reason-module", code, description)