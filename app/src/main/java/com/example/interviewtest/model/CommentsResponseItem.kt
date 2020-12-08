package com.example.interviewtest.model


import com.example.interviewtest.db.PostEntity
import com.google.gson.annotations.SerializedName

data class CommentsResponseItem(
    @SerializedName("body")
    val body: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("postId")
    val postId: Int
){
    override fun equals(other: Any?): Boolean {
        if (javaClass != other?.javaClass) {
            return false
        }
        other as CommentsResponseItem
        if (body != other.body) {
            return false
        }
        if (id != other.id) {
            return false
        }
        if (email != other.email) {
            return false
        }
        if (name != other.name) {
            return false
        }
        if (postId != other.postId) {
            return false
        }
        return true
    }
}