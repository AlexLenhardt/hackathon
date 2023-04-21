package com.hackathon.ticket.domain.entities

import java.util.UUID

class Reason(
    var uuid: UUID? = null,
    var description: String? = null,
    var isInfrastructure: Boolean? = null,
    var needsApproval: Boolean? = null,
    var subjects: List<Subject>? = null,
)