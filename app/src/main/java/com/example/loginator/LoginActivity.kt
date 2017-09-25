package com.example.loginator

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_login.login_form
import kotlinx.android.synthetic.main.activity_login.login_progress
import kotlinx.android.synthetic.main.login_form.email
import kotlinx.android.synthetic.main.login_form.email_sign_in_button
import kotlinx.android.synthetic.main.login_form.password

/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : AppCompatActivity(), LoginController {
    private lateinit var presenter: LoginPresenter

    override var emailError: Int? = null
        set(value) {
            runOnUiThread({
                email.error = if (value == null) null else getString(value)
                email.requestFocus()
            })
        }

    override var passwordError: Int? = null
        set(value) {
            runOnUiThread({
                password.error = if (value == null) null else getString(value)
                password.requestFocus()
            })
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        presenter = LoginPresenter(this)
        initSoftKeyboard()
        initLoginForm()
    }

    private fun initSoftKeyboard() {
        password.setOnEditorActionListener(TextView.OnEditorActionListener { _, id, _ ->
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                onLoginButtonClick()
                return@OnEditorActionListener true
            }
            false
        })
    }

    private fun initLoginForm() {
        email_sign_in_button.setOnClickListener {
            onLoginButtonClick()
        }
    }

    private fun onLoginButtonClick() {
        presenter.onLoginButtonClick(email.text.toString(), password.text.toString())
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    override fun showProgress(show: Boolean) {
        runOnUiThread({
            showProgressInternal(show)
        })
    }

    private fun showProgressInternal(show: Boolean) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            val shortAnimTime = resources.getInteger(android.R.integer.config_shortAnimTime)
                    .toLong()

            login_form.visibility = if (show) View.GONE else View.VISIBLE
            login_form.animate()
                    .setDuration(shortAnimTime)
                    .alpha((if (show) 0 else 1).toFloat())
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            login_form.visibility = if (show) View.GONE else View.VISIBLE
                        }
                    })

            login_progress.visibility = if (show) View.VISIBLE else View.GONE
            login_progress.animate()
                    .setDuration(shortAnimTime)
                    .alpha((if (show) 1 else 0).toFloat())
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            login_progress.visibility = if (show) View.VISIBLE else View.GONE
                        }
                    })
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            login_progress.visibility = if (show) View.VISIBLE else View.GONE
            login_form.visibility = if (show) View.GONE else View.VISIBLE
        }
    }

    override fun shutdown() {
        finish()
    }
}
