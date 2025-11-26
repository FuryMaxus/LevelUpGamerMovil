package com.example.levelupmovil.util

object EmailValidator {
    private val EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()

    fun isValid(email: String): Boolean {
        return email.matches(EMAIL_REGEX)
    }
}