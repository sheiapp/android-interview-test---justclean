package com.example.interviewtest.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class MainWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {
    private val TAG = this.javaClass.simpleName
    override fun doWork(): Result {
        Log.d(TAG, "syncing data initiated")
        return Result.success()
    }
}