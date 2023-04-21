package com.hackathon.example.domain.exceptions

import com.hackathon.shared.GenericError

val EXAMPLE_ERROR = ExampleException("EXAMPLE_ERROR", "This error is an example.")


class ExampleException(
    code: String,
    description: String
) : GenericError("example-module", code, description)