package com.hackathon.ticket.domain.exceptions

import com.hackathon.shared.GenericError

val SITUATION_DATABASE_ERROR = SituationException("SITUATION_DATABASE_ERROR", "Aguarde aprovação do gestor.")

val SITUATION_DENIED_DATABASE_ERROR = SituationException("SITUATION_DENIED_DATABASE_ERRORgit", "Chamado reprovado pelo gestor.")

class SituationException (
    code: String,
    description: String
    ) : GenericError("situation-module", code, description)