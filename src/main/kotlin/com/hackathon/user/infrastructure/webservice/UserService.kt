package com.hackathon.user.infrastructure.webservice

import com.hackathon.example.domain.usecases.UserUseCase
import com.hackathon.user.domain.usecases.response.UserResponse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseCookie
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.WebUtils

@RestController
@RequestMapping("/user")
class UserService(
    val userUseCase: UserUseCase
) {
    @PostMapping("/login/{userName}")
    fun login(
        @PathVariable("userName") userName: String,
        request: HttpServletRequest
    ): ResponseEntity<UserResponse> {

        print(WebUtils.getCookie(request, "user")?.run { value })

        val userResponse = userUseCase.get(userName)
        println(userResponse.error)
        return if (userResponse.error == null) {
            ResponseEntity.ok()
                .header(
                    HttpHeaders.SET_COOKIE,
                    ResponseCookie
                        .from("user")
                        .path("/")
                        .build()
                        .toString()
                )
                .body("logged out")
            ResponseEntity.ok()
                .header(
                    HttpHeaders.SET_COOKIE,
                    ResponseCookie.from("user", userResponse.user?.uuid.toString())
                        .path("/")
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

    @PostMapping("/logout")
    fun logout(): ResponseEntity<String> {
        return ResponseEntity.ok()
            .header(
                HttpHeaders.SET_COOKIE,
                ResponseCookie
                    .from("user")
                    .path("/")
                    .build()
                    .toString()
            )
            .body("logged out")
    }
}