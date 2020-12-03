package com.example.interviewtest.utils.objects

import android.util.Log
import com.example.interviewtest.utils.extensions.ApiExceptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

object Coroutines {

    private const val TAG: String = "Coroutines"
    fun <T : Any> ioThenMain(
        work: suspend (() -> T?),
        callback: ((T?) -> Unit),
        exception: (String?) -> Unit
    ) =
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val data = CoroutineScope(Dispatchers.IO).async rt@{
                    Log.d(TAG, "IO processing")
                    return@rt work()
                }.await()
                Log.d(TAG, "main processing")
                callback(data)
            } catch (e: ApiExceptions) {
                if ((e.message != null) && (e.message.contains("unKnownHostException", true))) {
                    exception("You are not connected to network.")
                    return@launch
                } else {
                    exception("Something went wrong.")
                    return@launch
                }
            }
        }
}