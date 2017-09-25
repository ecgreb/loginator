package com.example.loginator

interface LoginController {
    var emailError: Int?
    var passwordError: Int?

    fun showProgress(show: Boolean)
    fun shutdown()
}
