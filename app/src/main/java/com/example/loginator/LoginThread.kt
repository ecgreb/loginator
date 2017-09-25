package com.example.loginator

class LoginThread(private val email: String, private val password: String,
        private val callback: LoginCallback) : Thread() {

    override fun run() {
        // TODO: attempt authentication against a network service.

        try {
            // Simulate network access.
            Thread.sleep(3000)
        } catch (e: InterruptedException) {
            callback.onLoginComplete(false)
        }

        callback.onLoginComplete(LoginThread.DUMMY_CREDENTIALS
                .map { it.split(":") }
                .firstOrNull { it[0] == email }
                ?.let {
                    // Account exists, return true if the password matches.
                    it[1] == password
                }
                ?: true)
    }

    companion object {
        /**
         * A dummy authentication store containing known user names and passwords.
         * TODO: remove after connecting to a real authentication system.
         */
        private val DUMMY_CREDENTIALS = arrayOf("foo@example.com:hello", "bar@example.com:world")
    }
}
