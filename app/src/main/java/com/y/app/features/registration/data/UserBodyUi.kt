package com.y.app.features.registration.data

import android.util.Patterns
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.y.app.R

data class UserBodyUi(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val repeatedPassword: String,

    val avatarColor: ProfileColorEnum,
    val isValidating: Boolean,
) {
    var emailTaken: Boolean = false

    fun toUserBody(): UserBody {
        return UserBody(firstName, lastName, email, password, avatarColor.toHex())
    }

    @Composable
    fun validateNotEmpty(field: String): String? =
        if (field.isBlank() && isValidating)
            stringResource(R.string.validate_not_empty_message)
        else null

    @Composable
    fun validateEmail(): String? {
        if (!isValidating) return null
        return if (email.isBlank())
            stringResource(R.string.validate_not_empty_message)
        else if (!isValidEmail(email))
            stringResource(R.string.validate_email_message)
        else if(emailTaken) {
            emailTaken = false
            "This email address is already registered!"
        }
        else null
    }

    private fun isValidEmail(email: String) = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    @Composable
    fun validatePasswordRepeat(): String? {
        if (!isValidating) return null
        return if (repeatedPassword.isBlank())
            stringResource(R.string.validate_not_empty_message)
        else if (password != repeatedPassword)
            stringResource(R.string.validate_password_message)
        else null
    }

    fun isAllValidated(): Boolean {
        val validationList: MutableList<Boolean> = mutableListOf()
        validationList.add(firstName.isNotBlank())
        validationList.add(lastName.isNotBlank())
        validationList.add(isValidEmail(email))
        validationList.add(password.isNotBlank())
        validationList.add(password == repeatedPassword)
        return validationList.all { it }
    }

    companion object {
        val EMPTY = UserBodyUi(
            "", "", "", "",
            "", ProfileColorEnum.entries.first(), isValidating = false
        )
    }
}
