package com.example.interviewtest.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_table")
data class FavoriteEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "userId")
    val userId: Int,
    @ColumnInfo(name = "body")
    val body: String,
    @ColumnInfo(name = "title")
    val title: String
)