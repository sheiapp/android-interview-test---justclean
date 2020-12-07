package com.example.interviewtest.utils.extensions

import com.example.interviewtest.db.PostEntity


fun PostEntity.setupFavoriteEntity(): List<PostEntity> {
    val postEntity: MutableList<PostEntity> = mutableListOf()
    postEntity.add(
        PostEntity(
            no=this.no,
            id = this.id,
            body = this.body,
            title = this.title,
            userId = this.userId,
            isFavorite = true
        )
    )
    return postEntity
}