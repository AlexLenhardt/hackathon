package com.hackathon.ticket.infrastructure.repository.implementation

import com.hackathon.ticket.domain.entities.Subject
import com.hackathon.ticket.domain.repository.SubjectRepository
import com.hackathon.ticket.domain.usecases.SubjectUseCase
import com.hackathon.ticket.infrastructure.repository.database.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.innerJoin
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class SubjectRepositoryImplementation : SubjectRepository{

    override fun list(reasonUUID: UUID): List<Subject>? {
        return transaction {
            ReasonSubjectDatabase
                .innerJoin(SubjectDatabase, {SubjectDatabase.uuid}, { ReasonSubjectDatabase.subjectUUID})
                .slice(
                    SubjectDatabase.uuid,
                    SubjectDatabase.description
                )
                .select(
                    ReasonSubjectDatabase.reasonUUID eq reasonUUID
                )
                .map {
                    Subject(
                        uuid = it[SubjectDatabase.uuid],
                        description = it[SubjectDatabase.description]
                    )
                }
        }
    }
}