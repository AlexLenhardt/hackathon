package com.hackathon.ticket.domain.exceptions

import com.hackathon.shared.GenericError

val TICKET_NOT_FOUND = TicketExceptions("TICKET_NOT_FOUND", "Ops, chamado inválido!]")

val TITLE_NOT_FOUND = TicketExceptions("TITLE_NOT_FOUND", "Ops, título deve ser preenchido.")

val CONTACT_NOT_FOUND = TicketExceptions("CONTACT_NOT_FOUND", "Contato deve ser informado.")

val PRIORITY_NOT_FOUND = TicketExceptions("PRIORITY_NOT_FOUND", "Prioridade deve ser preenchida.")

val SITUATION_NOT_FOUND = TicketExceptions("SITUATION_NOT_FOUND", "Situação deve ser preenchida.")

val USER_NOT_FOUND = TicketExceptions("USER_NOT_FOUND", "Usuário deve ser informado.")

val REASON_NOT_FOUND = TicketExceptions("REASON_NOT_FOUND", "Assunto deve ser informado.")

val TICKET_DATABASE_ERROR = TicketExceptions("TICKET_DATABASE_ERROR", "An error occurred on ticket database.")

val SITUATION_NOT_ACCEPTED = TicketExceptions("SITUATION_NOT_ACCEPTED", "Situation is not accepted")

val TICKET_DOESNT_MANAGER_UPDATE = TicketExceptions("TICKET_DOESNT_MANAGER_UPDATE", "Manager cannot update ticket")

val DESCRIPTION_CANNOT_BE_EMPTY = TicketExceptions("DESCRIPTION_CANNOT_BE_EMPTY", "Description cannot be empty.")

val TICKET_USER_DIFFERENT = TicketExceptions("TICKET_USER_DIFFERENT", "The user's don't match")

val TICKET_DOESNT_NEED_APPROVAL = TicketExceptions("TICKET_DOESNT_NEED_APPROVAL", "This ticket doesn't need to be approval")

class TicketExceptions(
        code: String,
        description: String
) : GenericError("user-module", code, description)