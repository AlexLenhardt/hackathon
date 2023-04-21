package com.hackathon.ticket.infrastructure.repository.implementation

import com.hackathon.ticket.domain.entities.Priority
import com.hackathon.ticket.domain.entities.Reason
import com.hackathon.ticket.domain.entities.Situation
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
import org.springframework.stereotype.Service
import java.util.*

@Service
class TicketRepositoryImplementation : TicketRepository {
    override fun listAllTicket(): List<Ticket> {
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
                .selectAll()
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


}