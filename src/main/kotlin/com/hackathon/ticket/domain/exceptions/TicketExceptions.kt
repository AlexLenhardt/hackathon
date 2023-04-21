package com.hackathon.ticket.domain.exceptions

import com.hackathon.shared.GenericError

val TICKET_NOT_FOUND = TicketExceptions("TICKET_NOT_FOUND", "Ops, chamado inválido!]")

val TITLE_NOT_FOUND = TicketExceptions("TITLE_NOT_FOUND", "Ops, título deve ser preenchido.")

val CONTACT_NOT_FOUND = TicketExceptions("CONTACT_NOT_FOUND", "Contato deve ser informado.")

val PRIORITY_NOT_FOUND = TicketExceptions("PRIORITY_NOT_FOUND", "Prioridade deve ser preenchida.")

val USER_NOT_FOUND = TicketExceptions("USER_NOT_FOUND", "Usuário deve ser informado.")

val REASON_NOT_FOUND = TicketExceptions("REASON_NOT_FOUND", "Assunto deve ser informado.")

class TicketExceptions(
        code: String,
        description: String
) : GenericError("user-module", code, description)