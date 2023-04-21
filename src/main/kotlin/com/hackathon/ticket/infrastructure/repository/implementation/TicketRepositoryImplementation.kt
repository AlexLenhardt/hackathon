package com.hackathon.ticket.infrastructure.repository.implementation

import com.hackathon.ticket.domain.entities.Priority
import com.hackathon.ticket.domain.entities.Reason
import com.hackathon.ticket.domain.entities.Situation
import com.hackathon.ticket.domain.entities.Situation.Companion.pendentApproval
import com.hackathon.ticket.domain.entities.Ticket
import com.hackathon.ticket.domain.repository.TicketRepository
import com.hackathon.ticket.infrastructure.repository.database.PriorityDatabase
import com.hackathon.ticket.infrastructure.repository.database.ReasonDatabase
import com.hackathon.ticket.infrastructure.repository.database.SituationDatabase
import com.hackathon.ticket.infrastructure.repository.database.TicketDatabase
import com.hackathon.user.domain.entities.User
import com.hackathon.user.infrastructure.repository.database.UserDatabase
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*
import org.springframework.stereotype.Repository

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
                        contact = it[TicketDatabase.contact]
                    )
                }
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