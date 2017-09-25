package com.example.loginator

class LoginPresenter(val controller: LoginController) {

    fun onLoginButtonClick() {
        controller.attemptLogin()
    }
}
