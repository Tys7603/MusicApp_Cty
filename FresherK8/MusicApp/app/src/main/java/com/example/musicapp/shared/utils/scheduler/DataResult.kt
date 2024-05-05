package com.example.musicapp.shared.utils.scheduler

sealed class DataResult<out R> {

    data class Success<out T>(val data: T) : DataResult<T>()
    data class Error(val exception: Exception) : DataResult<Nothing>()
    data class Failure(val message: String) : DataResult<Nothing>()

    inline fun executeIfFailed(block: (ex: Exception) -> Unit): DataResult<R> {
        if (this is Error) block(this.exception)
        return this
    }
}
