package com.codes_roots.golden_coupon.presentation.homefragment.mvi

import kotlinx.coroutines.Job



sealed interface UserError {
    data class NetworkError(var throwable: Throwable) : UserError
    data class UserNotFound(val id: String) : UserError
    data class InvalidId(val id: String) : UserError
    object ValidationFailed : UserError
    object ServerError : UserError
    object Unexpected : UserError
}






