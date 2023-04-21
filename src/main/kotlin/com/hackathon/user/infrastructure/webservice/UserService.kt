package com.hackathon.user.infrastructure.webservice

import com.hackathon.example.domain.usecases.UserUseCase
import com.hackathon.example.domain.usecases.response.UserResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseCookie
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserService(
    val userUseCase: UserUseCase
) {
    @PostMapping("/login/{userName}")
    fun login(@PathVariable("userName") userName: String): ResponseEntity<UserResponse> {
        val userResponse = userUseCase.get(userName)
        return if (userResponse.error == null) {
            ResponseEntity.ok()
                .header(
                    HttpHeaders.SET_COOKIE,
                    ResponseCookie.from("user", userResponse.user?.uuid.toString())
                        .maxAge(24 * 60 * 60)
                        .httpOnly(true)
                        .sameSite("None")
                        .secure(false)
                        .build()
                        .toString()
                )
                .body(userResponse)
        } else {
            ResponseEntity.internalServerError()
                .body(userResponse)
        }
    }

}