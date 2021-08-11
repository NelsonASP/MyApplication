package com.nasp.myapplication.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Items::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun gifDao(): DbParameters
}