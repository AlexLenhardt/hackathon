package com.hackathon.ticket.domain.exception

import com.hackathon.shared.GenericError

val REASON_DATABASE_ERROR = ReasonException("REASON_DATABASE_ERROR", "An error occurred on reason database.")


class ReasonException(
    code: String,
    description: String
) : GenericError("reason-module", code, description)