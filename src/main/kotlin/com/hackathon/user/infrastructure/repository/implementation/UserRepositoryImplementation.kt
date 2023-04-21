package com.hackathon.user.infrastructure.repository.implementation

import com.hackathon.user.infrastructure.repository.database.UserDatabase
import com.hackathon.user.domain.entities.User
import com.hackathon.user.domain.repository.UserRepository
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Service

@Service
class UserRepositoryImplementation : UserRepository {
    override fun get(userName: String): User? {
        return transaction {
            UserDatabase
                .slice(
                    UserDatabase.uuid,
                    UserDatabase.name,
                    UserDatabase.type,
                )
                .select(
                    UserDatabase.name eq userName
                )
                .map {
                    User(
                        uuid = it[UserDatabase.uuid],
                        name = it[UserDatabase.name],
                        type = it[UserDatabase.type],
                    )
                }
                .firstOrNull()
        }
    }
}