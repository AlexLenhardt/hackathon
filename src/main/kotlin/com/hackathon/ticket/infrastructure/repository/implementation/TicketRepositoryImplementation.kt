package com.hackathon.ticket.infrastructure.repository.implementation

import com.hackathon.ticket.domain.entities.*
import com.hackathon.ticket.domain.entities.Situation.Companion.approved
import com.hackathon.ticket.domain.entities.Situation.Companion.excluded
import com.hackathon.ticket.domain.entities.Situation.Companion.pendentApproval
import com.hackathon.ticket.domain.entities.Situation.Companion.reproved
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
                .toSliceTicket()
                .select(
                    TicketDatabase.userUUID eq userUUID
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
                .toSliceTicket()
                .select(
                    TicketDatabase.uuid eq uuid
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
                        contact = it[TicketDatabase.contact],
                        descriptions = getDescriptionByTicket(it[TicketDatabase.uuid])
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
                .map {
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
                .toSliceTicket()
                .select(
                    SituationDatabase.code eq pendentApproval
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

    override fun addTicket(ticket: Ticket) {
        transaction {
            TicketDatabase.insert {
                it[uuid] = ticket.uuid!!
                it[reasonUUID] = ticket.reason!!.uuid!!
                it[priorityUUID] = ticket.priority!!.uuid!!
                it[userUUID] = ticket.user!!.uuid!!
                it[situationUUID] = ticket.situation!!.uuid!!
                it[title] = ticket.title!!
                it[contact] = ticket.contact!!
                it[subject] = ticket.subject!!.uuid!!
            }.resultedValues!!

            DescriptionTicketDatabase
                    .insert {
                        it[uuid] = UUID.randomUUID()
                        it[ticketUUID] = ticket.uuid!!
                        it[userUUID] = ticket.user!!.uuid!!
                        it[situationUUID] = ticket.situation!!.uuid!!
                        it[description] = ticket.descriptions?.get(0)!!.description!!
                    }
        }
    }

    override fun approval(ticketUUID: UUID, userUUID: UUID) {
        val situationUUID = getSituationByCode(approved)!!.uuid!!
        transaction {
            TicketDatabase
                .update({
                    TicketDatabase.uuid eq ticketUUID
                }) {
                    it[TicketDatabase.situationUUID] = situationUUID
                }
        }
    }

    override fun getSituationByCode(situationCode: Int): Situation? {
        return transaction {
            SituationDatabase
                .select {
                    SituationDatabase.code eq situationCode
                }
                .map {
                    it.toSituation()
                }
                .firstOrNull()
        }
    }


    override fun reprove(ticketUUID: UUID, userUUID: UUID, description: String) {
        val situationUUID = getSituationByCode(reproved)!!.uuid!!
        transaction {
            TicketDatabase
                .update({
                    TicketDatabase.uuid eq ticketUUID
                }) {
                    it[TicketDatabase.situationUUID] = situationUUID
                }

            DescriptionTicketDatabase
                .insert {
                    it[uuid] = UUID.randomUUID()
                    it[DescriptionTicketDatabase.ticketUUID] = ticketUUID
                    it[DescriptionTicketDatabase.userUUID] = userUUID
                    it[DescriptionTicketDatabase.situationUUID] = situationUUID
                    it[DescriptionTicketDatabase.description] = description
                }
        }
    }

    override fun exclude(ticketUUID: UUID, userUUID: UUID, description: String) {
        val situationUUID = getSituationByCode(excluded)!!.uuid!!
        transaction {
            TicketDatabase
                .update({
                    TicketDatabase.uuid eq ticketUUID
                }) {
                    it[TicketDatabase.situationUUID] = situationUUID
                }

            DescriptionTicketDatabase
                .insert {
                    it[uuid] = UUID.randomUUID()
                    it[DescriptionTicketDatabase.ticketUUID] = ticketUUID
                    it[DescriptionTicketDatabase.userUUID] = userUUID
                    it[DescriptionTicketDatabase.situationUUID] = situationUUID
                    it[DescriptionTicketDatabase.description] = description
                }
        }
    }

    override fun updateTicket(ticket: Ticket) {
        transaction {
            val situationUUID = getSituationByCode(pendentApproval)!!.uuid!!
            TicketDatabase
                .update({TicketDatabase.uuid eq ticket.uuid!!}){
                    it[reasonUUID] = ticket.reason!!.uuid!!
                    it[title] = ticket.title!!
                    it[priorityUUID] = ticket.priority!!.uuid!!
                    it[contact] = ticket.contact!!
                    it[TicketDatabase.situationUUID] = situationUUID
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

fun ColumnSet.toSliceTicket(): FieldSet {
    val columns: MutableList<Expression<*>> = mutableListOf()
    columns.add(TicketDatabase.uuid)
    columns.add(TicketDatabase.number)
    columns.add(ReasonDatabase.uuid)
    columns.add(ReasonDatabase.isInfrastructure)
    columns.add(ReasonDatabase.needsApproval)
    columns.add(ReasonDatabase.description)
    columns.add(TicketDatabase.title)
    columns.add(PriorityDatabase.uuid)
    columns.add(PriorityDatabase.description)
    columns.add(UserDatabase.uuid)
    columns.add(UserDatabase.name)
    columns.add(UserDatabase.type)
    columns.add(SituationDatabase.uuid)
    columns.add(SituationDatabase.code)
    columns.add(SituationDatabase.description)
    columns.add(TicketDatabase.modified_at)
    columns.add(TicketDatabase.create_at)
    columns.add(TicketDatabase.contact)

    return Slice(
        this,
        columns.toList()
    )
}

fun ResultRow.toUser(): User {
    return User(
        uuid = this[UserDatabase.uuid],
        name = this[UserDatabase.name],
        type = this[UserDatabase.type]
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
        isInfrastructure = this[ReasonDatabase.isInfrastructure],
        needsApproval = this[ReasonDatabase.needsApproval]
    )
}