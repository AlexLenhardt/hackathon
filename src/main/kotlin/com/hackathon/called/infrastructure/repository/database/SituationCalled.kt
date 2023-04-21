package com.hackathon.called.infrastructure.repository.database

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date

object SituationCalledDatabase : Table("situationCalled")  {
    var uuid = uuid("uuid").uniqueIndex()
    var calledUUID = uuid("uuid_called")
    var situationUUID = uuid("uuid_situation")
    var userUUID = uuid("uuid_user")
    var create_at = date("create_at")
}