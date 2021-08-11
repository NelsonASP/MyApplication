package com.nasp.myapplication.data.remote


sealed class Result<out R> {

    data class Error(val exception: Exception) : Result<Nothing>()
    data class Success<out T>(val data: T) : Result<T>()
    object Loading : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            Loading -> "Loading"
        }
    }
}

val Result<*>.succeeded
    get() = this is Result.Success && data != null
