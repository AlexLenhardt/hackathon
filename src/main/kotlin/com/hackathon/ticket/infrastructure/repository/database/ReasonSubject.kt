package com.hackathon.ticket.infrastructure.repository.database

import org.jetbrains.exposed.sql.Table

object ReasonSubjectDatabase : Table("reason_subject") {
    var reasonUUID = reference("reason_uuid", ReasonDatabase.uuid)
    var subjectUUID = reference("subject_uuid", SubjectDatabase.uuid)
}