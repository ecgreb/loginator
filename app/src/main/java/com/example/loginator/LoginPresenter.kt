package com.example.loginator

import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.Observer
import android.text.TextUtils

class LoginPresenter(private val controller: LoginController,
        private val viewModel: LoginViewModel) : LoginCallback, LifecycleObserver {

    init {
        Loginator.callback = this
        viewModel.isProgressVisible.observe(controller, Observer<Boolean> {
            isProgressVisible -> controller.showProgress(isProgressVisible ?: false)
        })
    }

    fun onLoginButtonClick(email: String, password: String) {
        controller.emailError = null
        controller.passwordError = null

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            controller.emailError = R.string.error_field_required
            return
        } else if (!isEmailValid(email)) {
            controller.emailError = R.string.error_invalid_email
            return
        }

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
            controller.passwordError = R.string.error_invalid_password
            return
        }

        controller.showProgress(true)
        viewModel.isProgressVisible.value = true
        Loginator.login(email, password)
    }

    private fun isEmailValid(email: String): Boolean {
        return email.contains("@")
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length > 4
    }

    override fun onLoginComplete(success: Boolean) {
        controller.showProgress(false)
        viewModel.isProgressVisible.postValue(false)
        if (success) {
            controller.shutdown()
        } else {
            controller.passwordError = R.string.error_incorrect_password
        }
    }
}
