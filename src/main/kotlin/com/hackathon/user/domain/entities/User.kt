package com.hackathon.user.domain.entities

import java.util.*

class User (
    var uuid: UUID? = null,
    var name: String? = null,
    var type: Int? = null,
){
    fun isColaborator(): Boolean{
        return this.type == 1
    }

    fun isManager(): Boolean{
        return this.type == 2
    }
}
