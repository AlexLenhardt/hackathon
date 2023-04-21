package com.hackathon.user.domain.exceptions

import com.hackathon.shared.GenericError

val USER_NOT_FOUND = UserException("USER_NOT_FOUND", "User not found.")

val USER_NOT_ALLOWED = UserException("USER_NOT_ALLOWED", "User type not allowed")

class UserException(
    code: String,
    description: String
) : GenericError("user-module", code, description)