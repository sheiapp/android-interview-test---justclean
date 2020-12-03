package com.example.interviewtest.utils.extensions

import com.example.interviewtest.utils.Constants
import retrofit2.Response


suspend fun <T> apiValidator(call: suspend () -> Response<T>): Resource<T> {
    return try {
        val response = call.invoke()
        if (response.isSuccessful) {
            response.body()?.let {
                return@let Resource.success(it)
            } ?: Resource.error(Constants.unknownError, null)
        } else {
            Resource.error(Constants.unknownError, null)
        }
    } catch (e: Exception) {
        Resource.error(Constants.failedToReachServer, null)
    }
}

