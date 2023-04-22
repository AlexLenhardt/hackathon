package com.hackathon.ticket.domain.exceptions

import com.hackathon.shared.GenericError

val TICKET_NOT_FOUND = TicketExceptions("TICKET_NOT_FOUND", "Ops, chamado inválido!]")

val TITLE_NOT_FOUND = TicketExceptions("TITLE_NOT_FOUND", "Ops, título deve ser preenchido.")

val CONTACT_NOT_FOUND = TicketExceptions("CONTACT_NOT_FOUND", "Contato deve ser informado.")

val PRIORITY_NOT_FOUND = TicketExceptions("PRIORITY_NOT_FOUND", "Prioridade deve ser preenchida.")

val USER_NOT_FOUND = TicketExceptions("USER_NOT_FOUND", "Usuário deve ser informado.")

val REASON_NOT_FOUND = TicketExceptions("REASON_NOT_FOUND", "Assunto deve ser informado.")

val TICKET_DATABASE_ERROR = TicketExceptions("TICKET_DATABASE_ERROR", "An error occurred on ticket database.")

val TICKET_DOESNT_NEED_APPROVAL = TicketExceptions("TICKET_DOESNT_NEED_APPROVAL", "This ticket doesn't need to be approval")

val TICKET_DOESNT_MANAGER_UPDATE = TicketExceptions("TICKET_DOESNT_MANAGER_UPDATE", "Manager cannot update ticket")

class TicketExceptions(
        code: String,
        description: String
) : GenericError("user-module", code, description)