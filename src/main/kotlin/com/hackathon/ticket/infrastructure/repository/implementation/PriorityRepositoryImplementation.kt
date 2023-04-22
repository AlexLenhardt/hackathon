package com.hackathon.ticket.infrastructure.repository.implementation

import com.hackathon.ticket.domain.entities.Priority
import com.hackathon.ticket.domain.repository.PriorityRepository
import com.hackathon.ticket.infrastructure.repository.database.PriorityDatabase
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class PriorityRepositoryImplementation : PriorityRepository{

    override fun list(): List<Priority>? {
        return transaction {
            PriorityDatabase
                .slice(
                    PriorityDatabase.uuid,
                    PriorityDatabase.description
                )
                .selectAll()
                .map {
                    Priority(
                        uuid = it[PriorityDatabase.uuid],
                        description = it[PriorityDatabase.description]
                    )
                }
        }
    }
}