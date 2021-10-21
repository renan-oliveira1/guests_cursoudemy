package com.example.guests.service.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.guests.service.model.GuestModel


@Database(entities = arrayOf(GuestModel::class), version = 1)
abstract class GuestDataBase : RoomDatabase() {
    abstract  fun guestDAO(): GuestDAO

    companion object{
        private lateinit var INSTANCE: GuestDataBase

        fun getDataBase(context: Context): GuestDataBase{
            if(!::INSTANCE.isInitialized){
                synchronized(GuestDataBase::class) {
                    INSTANCE = Room.databaseBuilder(context, GuestDataBase::class.java, "guestDB")
                        .addMigrations(MIGRATION_1_2)
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE
        }

        private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("DELETE FROM Guest")
            }
        }
    }

}