package com.example.interviewtest.repository

import androidx.lifecycle.LiveData
import com.example.interviewtest.db.PostEntity
import com.example.interviewtest.model.CommentsResponseItem
import com.example.interviewtest.network.AppServices
import com.example.interviewtest.network.NetworkConnectionStatus
import com.example.interviewtest.utils.Resource
import com.example.interviewtest.utils.extensions.networkCallHelper
import retrofit2.Response
import javax.inject.Inject

class MainRepo @Inject constructor(private val appServices: AppServices,   private val networkConnectionStatus: NetworkConnectionStatus) : MainRepository {

    override suspend fun getPosts(): List<PostEntity>? {
        return networkCallHelper(appServices.getPost())
    }

    override suspend fun getComments(post_id: Int): List<CommentsResponseItem>? {
        return networkCallHelper(appServices.getComments(post_id))
    }

    override fun getNetworkConnectionStatus(): LiveData<Boolean> {
        return networkConnectionStatus
    }
}