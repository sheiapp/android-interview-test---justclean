package com.example.interviewtest.network

import android.util.Log
import com.example.interviewtest.utils.extensions.ApiExceptions
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import java.lang.Exception

abstract class APICallHandler {
    private val TAG: String = "APICallHandler"
    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): T {
        try {
            val response = call.invoke()
            if (response.isSuccessful) {
                Log.d(TAG, "response successful")
                return response.body()!!
            } else {
                val error = response.errorBody()?.string()
                val message = StringBuilder()
                error?.let {
                    try {
                        message.append(JSONObject(it).getString("message"))
                    } catch (e: JSONException) {
                    }
                    message.append("\n")
                }
                message.append(("Error Code: ${response.code()}"))
                Log.d(TAG, "throwing exception")
                throw ApiExceptions(message.toString())
            }
        } catch (e: Exception) {
            Log.d("exception", e.toString())
            throw  ApiExceptions(e.toString())
        }
    }
}