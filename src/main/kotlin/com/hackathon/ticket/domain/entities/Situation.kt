package com.hackathon.ticket.domain.entities

import java.util.*

class Situation (
    var uuid: UUID? = null,
    var code: Int? = null,
    var description: String? = null
){
    companion object{
        const val approved: Int = 1
        const val reproved: Int = 2
        const val pendentApproval: Int = 3
        const val opened: Int = 5
    }
}