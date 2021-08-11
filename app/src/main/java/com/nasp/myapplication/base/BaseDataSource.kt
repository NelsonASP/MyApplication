package com.nasp.myapplication.base

import retrofit2.Response
import com.nasp.myapplication.data.remote.Result

/**
 * errors
 */
abstract class BaseDataSource {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Result<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Result.Success(body)
            }
            return Result.Error(Exception(response.message()))
        } catch (e: Exception) {
            return Result.Error(e)
        }
    }
}