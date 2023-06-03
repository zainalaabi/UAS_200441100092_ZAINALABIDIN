package com.example.projectakhir.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [InvoiceActivityEntity::class], version = 1)
abstract class InvoiceActivityRoomDatabase : RoomDatabase() {
    abstract fun InvoiceActivityDao(): InvoiceActivityDao

    companion object {
        @Volatile
        private var INSTANCE: InvoiceActivityRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): InvoiceActivityRoomDatabase {
            if (INSTANCE == null) {
                synchronized(InvoiceActivityRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        InvoiceActivityRoomDatabase::class.java, "InvoiceActivity_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE as InvoiceActivityRoomDatabase
        }
    }
}