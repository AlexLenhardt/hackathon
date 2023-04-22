package com.hackathon.ticket.domain.exception

import com.hackathon.shared.GenericError

val SITUATION_DATABASE_ERROR = ReasonException("SITUATION_DATABASE_ERROR", "Aguarde aprovação do gestor.")

val SITUATIONDENIED_DATABASE_ERROR = ReasonException("SITUATIONDENIED_DATABASE_ERROR", "Chamado reprovado pelo gestor.")

class SituationException (
    code: String,
    description: String
    ) : GenericError("situation-module", code, description)