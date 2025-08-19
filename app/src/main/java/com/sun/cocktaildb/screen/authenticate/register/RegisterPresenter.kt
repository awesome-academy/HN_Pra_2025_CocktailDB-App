package com.sun.cocktaildb.screen.authenticate.register

import com.sun.cocktaildb.data.repository.AuthRepository
import com.sun.cocktaildb.data.repository.impl.FirebaseAuthImplement
import com.sun.cocktaildb.utils.base.BasePresenter

class RegisterPresenter constructor(
    private val authRepository: AuthRepository = FirebaseAuthImplement(),
) : RegisterContract.Presenter {
    private var view: RegisterContract.View? = null

    init {
    }

    override fun onStart() {
    }

    override fun onStop() {
    }

    override fun setView(view: RegisterContract.View?) {
        this.view = view
    }

    override fun doRegister(
        email: String,
        password: String,
    ) {
        authRepository.register(email, password) { result ->
            result.fold(
                onSuccess = {
                    view?.onRegisterSuccess()
                },
                onFailure = {
                    view?.onRegisterFailed(result.exceptionOrNull()?.message ?: "Register failed")
                },
            )
        }
    }

    override fun validateInputs(
        email: String,
        password: String,
        confirmPassword: String,
    ): Boolean {
        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            view?.showEmailInvalid("Email is required")
            view?.showPasswordInvalid("Password is required")
            view?.showConfirmPasswordInvalid("Confirm password is required")
            return false
        }
        if (!validateEmailPattern(email)) {
            view?.showEmailInvalid("Invalid email format")
            return false
        }
        if (password.length < 6) {
            view?.showPasswordInvalid("Password must be at least 6 characters")
            return false
        }
        if (password != confirmPassword) {
            view?.showConfirmPasswordInvalid("Passwords do not match")
            return false
        }
        return true
    }

    private fun validateEmailPattern(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}"
        return email.matches(emailPattern.toRegex())
    }
}
