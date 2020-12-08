package com.example.interviewtest.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "post_table")
data class PostEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "no")
    val no: Int,
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "userId")
    val userId: Int,
    @ColumnInfo(name = "body")
    val body: String,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false
) {
    override fun equals(other: Any?): Boolean {
        if (javaClass != other?.javaClass) {
            return false
        }
        other as PostEntity
        if (no != other.no) {
            return false
        }
        if (id != other.id) {
            return false
        }
        if (userId != other.userId) {
            return false
        }
        if (body != other.body) {
            return false
        }
        if (title != other.title) {
            return false
        }
        if (isFavorite != other.isFavorite) {
            return false
        }
        return true
    }
}