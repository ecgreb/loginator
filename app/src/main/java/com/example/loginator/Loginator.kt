package com.example.loginator

object Loginator : LoginCallback {
    var callback: LoginCallback? = null

    fun login(email: String, password: String) {
        if (callback != null) {
            LoginThread(email, password, this).start()
        }
    }

    override fun onLoginComplete(success: Boolean) {
        callback?.onLoginComplete(success)
    }
}
