package com.hackathon.example.infrastructure.repository.implementation

import com.hackathon.example.domain.entities.Example
import com.hackathon.example.domain.repository.ExampleRepository
import com.hackathon.example.infrastructure.repository.database.ExampleDatabase
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Service
import java.util.*

@Service
class ExampleRepositoryImplementation : ExampleRepository {
    override fun create(uuid: UUID): Example {
        return transaction {
            ExampleDatabase.insert {
                it[ExampleDatabase.uuid] = uuid
            }.resultedValues!!
            Example(uuid)
        }
    }
}