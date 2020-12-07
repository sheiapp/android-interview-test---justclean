package com.example.interviewtest.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "post_table")
data class PostEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "no")
    val no:Int,
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "userId")
    val userId: Int,
    @ColumnInfo(name = "body")
    val body: String,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean=false
)