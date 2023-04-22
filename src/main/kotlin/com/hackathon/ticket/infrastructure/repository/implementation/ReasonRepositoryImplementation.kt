package com.hackathon.ticket.infrastructure.repository.implementation

import com.hackathon.ticket.domain.entities.Reason
import com.hackathon.ticket.domain.entities.Subject
import com.hackathon.ticket.domain.repository.ReasonRepository
import com.hackathon.ticket.infrastructure.repository.database.ReasonDatabase
import com.hackathon.ticket.infrastructure.repository.database.ReasonSubjectDatabase
import com.hackathon.ticket.infrastructure.repository.database.SubjectDatabase
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.innerJoin
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class ReasonRepositoryImplementation : ReasonRepository {
    override fun list(isInfrastructure: Boolean?): List<Reason>? {
        return transaction {
            ReasonDatabase
                .select {
                    isInfrastructure?.let {
                        ReasonDatabase.isInfrastructure.eq(it)
                    } ?: Op.TRUE
                }
                .map {
                    Reason(
                        uuid = it[ReasonDatabase.uuid],
                        description = it[ReasonDatabase.description],
                        isInfrastructure = it[ReasonDatabase.isInfrastructure],
                        needsApproval = it[ReasonDatabase.needsApproval],
                        subjects = getSubjectFromReason(it[ReasonDatabase.uuid])
                    )
                }
        }
    }

    private fun getSubjectFromReason(reasonUUID: UUID): List<Subject> {
        return transaction {
            ReasonSubjectDatabase
                .innerJoin(SubjectDatabase, { SubjectDatabase.uuid }, { ReasonSubjectDatabase.subjectUUID })
                .innerJoin(ReasonDatabase, { ReasonDatabase.uuid }, { ReasonSubjectDatabase.reasonUUID })
                .select {
                    ReasonSubjectDatabase.reasonUUID eq reasonUUID
                }
                .map {
                    Subject(
                        uuid = it[SubjectDatabase.uuid],
                        description = it[SubjectDatabase.description]
                    )
                }
        }
    }

}