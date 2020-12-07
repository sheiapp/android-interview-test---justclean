package com.example.interviewtest.db

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [PostEntity::class], version = 5, exportSchema = false)
abstract class PostDataBase : RoomDatabase() {
    abstract fun getPostDao(): PostDAO
}