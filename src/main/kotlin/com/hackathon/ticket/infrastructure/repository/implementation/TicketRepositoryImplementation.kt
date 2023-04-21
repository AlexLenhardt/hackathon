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
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.innerJoin
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository

@Repository
class TicketRepositoryImplementation : TicketRepository {
    override fun listAllTicket(): List<Ticket> {
        return transaction {
            TicketDatabase
                .innerJoin(SituationDatabase, { SituationDatabase.uuid }, { TicketDatabase.situationUUID })
                .innerJoin(ReasonDatabase, { ReasonDatabase.uuid }, { TicketDatabase.reasonUUID })
                .innerJoin(PriorityDatabase, { PriorityDatabase.uuid }, { TicketDatabase.priorityUUID })
                .innerJoin(UserDatabase, { UserDatabase.uuid }, { TicketDatabase.userUUID })
                .selectAll()
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
                        create_at = it[TicketDatabase.create_at]
                    )
                }
        }
    }
}