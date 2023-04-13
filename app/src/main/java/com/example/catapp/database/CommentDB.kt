package com.example.catapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.catapp.dao.CommentDAO
import com.example.catapp.model.Comment


@Database(entities = [Comment::class], version = 1, exportSchema = false)
abstract class CommentDB : RoomDatabase() {

    abstract fun commentDao(): CommentDAO


    companion object {

        @Volatile
        private var CommentDBInstance: CommentDB? = null

        fun getDatabase(context: Context): CommentDB {

            val tempInstance = CommentDBInstance

            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                //if the db instance does not exist, create one
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CommentDB::class.java,
                    "comment"
                ).build()
                CommentDBInstance = instance
                return instance
            }
        }
    }
}