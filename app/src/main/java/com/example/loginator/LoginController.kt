package com.example.loginator

import android.arch.lifecycle.LifecycleOwner

interface LoginController : LifecycleOwner {
    var emailError: Int?
    var passwordError: Int?

    fun showProgress(show: Boolean)
    fun shutdown()
}
