package com.hackathon.ticket.infrastructure.repository.implementation

import com.hackathon.ticket.domain.entities.*
import com.hackathon.ticket.domain.entities.Situation.Companion.pendentApproval
import com.hackathon.ticket.domain.repository.TicketRepository
import com.hackathon.ticket.infrastructure.repository.database.*
import com.hackathon.user.domain.entities.User
import com.hackathon.user.infrastructure.repository.database.UserDatabase
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class TicketRepositoryImplementation : TicketRepository {
    override fun listAllTicket(userUUID: UUID): List<Ticket> {
        return transaction {
            TicketDatabase
                .innerJoin(SituationDatabase, { SituationDatabase.uuid }, { TicketDatabase.situationUUID })
                .innerJoin(ReasonDatabase, { ReasonDatabase.uuid }, { TicketDatabase.reasonUUID })
                .innerJoin(PriorityDatabase, { PriorityDatabase.uuid }, { TicketDatabase.priorityUUID })
                .innerJoin(UserDatabase, { UserDatabase.uuid }, { TicketDatabase.userUUID })
                .slice(
                    TicketDatabase.uuid,
                    TicketDatabase.number,
                    ReasonDatabase.uuid,
                    ReasonDatabase.description,
                    TicketDatabase.title,
                    PriorityDatabase.uuid,
                    PriorityDatabase.description,
                    UserDatabase.uuid,
                    UserDatabase.name,
                    SituationDatabase.uuid,
                    SituationDatabase.description,
                    TicketDatabase.modified_at,
                    TicketDatabase.create_at,
                    TicketDatabase.contact
                )
                .select(
                    (TicketDatabase.userUUID eq userUUID) and
                            (SituationDatabase.code eq pendentApproval)
                )
                .map {
                    Ticket(
                        uuid = it[TicketDatabase.uuid],
                        number = it[TicketDatabase.number],
                        reason = it.toReason(),
                        title = it[TicketDatabase.title],
                        priority = it.toPriority(),
                        user = it.toUser(),
                        situation = it.toSituation(),
                        modified_at = it[TicketDatabase.modified_at],
                        create_at = it[TicketDatabase.create_at],
                        contact = it[TicketDatabase.contact]
                    )
                }
        }
    }

    override fun findByUUID(uuid: UUID): Ticket? {
        return transaction {
            TicketDatabase
                .innerJoin(SituationDatabase, { SituationDatabase.uuid }, { TicketDatabase.situationUUID })
                .innerJoin(ReasonDatabase, { ReasonDatabase.uuid }, { TicketDatabase.reasonUUID })
                .innerJoin(PriorityDatabase, { PriorityDatabase.uuid }, { TicketDatabase.priorityUUID })
                .innerJoin(UserDatabase, { UserDatabase.uuid }, { TicketDatabase.userUUID })
                .slice(
                    TicketDatabase.uuid,
                    TicketDatabase.number,
                    ReasonDatabase.uuid,
                    ReasonDatabase.description,
                    TicketDatabase.title,
                    PriorityDatabase.uuid,
                    PriorityDatabase.description,
                    UserDatabase.uuid,
                    UserDatabase.name,
                    SituationDatabase.uuid,
                    SituationDatabase.description,
                    TicketDatabase.modified_at,
                    TicketDatabase.create_at,
                    TicketDatabase.contact
                )
                .select(
                    TicketDatabase.uuid eq uuid
                )
                .map {
                    Ticket(
                        uuid = it[TicketDatabase.uuid],
                        number = it[TicketDatabase.number],
                        reason = Reason(
                            uuid = it[ReasonDatabase.uuid],
                            description = it[ReasonDatabase.description]
                        ),
                        title = it[TicketDatabase.title],
                        priority = Priority(
                            uuid = it[PriorityDatabase.uuid],
                            description = it[PriorityDatabase.description]
                        ),
                        user = User(
                            uuid = it[UserDatabase.uuid],
                            name = it[UserDatabase.name]
                        ),
                        situation = Situation(
                            uuid = it[SituationDatabase.uuid],
                            description = it[SituationDatabase.description]
                        ),
                        modified_at = it[TicketDatabase.modified_at],
                        create_at = it[TicketDatabase.create_at],
                        contact = it[TicketDatabase.contact]
                    )
                }
                .firstOrNull()
        }
    }

    private fun getDescriptionByTicket(ticketUUID: UUID): List<DescriptionTicket> {
        return transaction {
            DescriptionTicketDatabase
                .innerJoin(UserDatabase, { UserDatabase.uuid }, { DescriptionTicketDatabase.userUUID })
                .slice(
                    DescriptionTicketDatabase.uuid,
                    UserDatabase.uuid,
                    UserDatabase.name,
                    DescriptionTicketDatabase.create_at,
                    DescriptionTicketDatabase.description
                )
                .select(
                    DescriptionTicketDatabase.ticketUUID eq ticketUUID
                )
                .map{
                    DescriptionTicket(
                        uuid = it[DescriptionTicketDatabase.uuid],
                        user = User(
                            uuid = it[UserDatabase.uuid],
                            name = it[UserDatabase.name]
                        ),
                        create_at = it[DescriptionTicketDatabase.create_at],
                        description = it[DescriptionTicketDatabase.description]
                    )
                }
        }
    }

    override fun listAllTicketApproval(userUUID: UUID): List<Ticket> {
        return transaction {
            TicketDatabase
                .innerJoin(SituationDatabase, { SituationDatabase.uuid }, { TicketDatabase.situationUUID })
                .innerJoin(ReasonDatabase, { ReasonDatabase.uuid }, { TicketDatabase.reasonUUID })
                .innerJoin(PriorityDatabase, { PriorityDatabase.uuid }, { TicketDatabase.priorityUUID })
                .innerJoin(UserDatabase, { UserDatabase.uuid }, { TicketDatabase.userUUID })
                .slice(
                    TicketDatabase.uuid,
                    TicketDatabase.number,
                    ReasonDatabase.uuid,
                    ReasonDatabase.description,
                    TicketDatabase.title,
                    PriorityDatabase.uuid,
                    PriorityDatabase.description,
                    UserDatabase.uuid,
                    UserDatabase.name,
                    SituationDatabase.uuid,
                    SituationDatabase.description,
                    TicketDatabase.modified_at,
                    TicketDatabase.create_at,
                    TicketDatabase.contact
                )
                .select(
                    ReasonDatabase.needsApproval eq true
                )
                .map {
                    Ticket(
                        uuid = it[TicketDatabase.uuid],
                        number = it[TicketDatabase.number],
                        reason = Reason(
                            uuid = it[ReasonDatabase.uuid],
                            description = it[ReasonDatabase.description],
                        ),
                        title = it[TicketDatabase.title],
                        priority = Priority(
                            uuid = it[PriorityDatabase.uuid],
                            description = it[PriorityDatabase.description]
                        ),
                        user = User(
                            uuid = it[UserDatabase.uuid],
                            name = it[UserDatabase.name]
                        ),
                        situation = Situation(
                            uuid = it[SituationDatabase.uuid],
                            description = it[SituationDatabase.description]
                        ),
                        modified_at = it[TicketDatabase.modified_at],
                        create_at = it[TicketDatabase.create_at],
                        contact = it[TicketDatabase.contact],
                        descriptions = getDescriptionByTicket(it[TicketDatabase.uuid])
                    )
                }
        }
    }

    override fun addTicket(ticket: Ticket) {
        transaction {
            TicketDatabase.insert {
                it[uuid] = ticket.uuid!!
                it[number] = ticket.number!!
                it[reasonUUID] = ticket.reason!!.uuid!!
                it[userUUID] = ticket.user!!.uuid!!
                it[situationUUID] = ticket.situation!!.uuid!!
                it[title] = ticket.title!!
                it[modified_at] = ticket.modified_at!!
                it[create_at] = ticket.create_at!!
                it[contact] = ticket.contact!!
            }.resultedValues!!
        }
    }

}

fun ResultRow.toSituation(): Situation {
    return Situation(
        uuid = this[SituationDatabase.uuid],
        code = this[SituationDatabase.code],
        description = this[SituationDatabase.description]
    )
}

fun ResultRow.toUser(): User {
    return User(
        uuid = this[UserDatabase.uuid],
        name = this[UserDatabase.name]
    )
}

fun ResultRow.toPriority(): Priority {
    return Priority(
        uuid = this[PriorityDatabase.uuid],
        description = this[PriorityDatabase.description]
    )
}

fun ResultRow.toReason(): Reason {
    return Reason(
        uuid = this[ReasonDatabase.uuid],
        description = this[ReasonDatabase.description],
    )
}