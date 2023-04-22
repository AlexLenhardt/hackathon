package com.hackathon.ticket.domain.exception

import com.hackathon.shared.GenericError

val SUBJECT_DATABASE_ERROR = ReasonException("SITUATION_DATABASE_ERROR", "You need to inform the subject.")

class SubjectException (
    code: String,
    description: String
) : GenericError("subject-module", code, description)