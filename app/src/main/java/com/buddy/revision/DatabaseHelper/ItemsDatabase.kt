package com.buddy.revision.DatabaseHelper

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.buddy.revision.Daos.ItemDao
import com.buddy.revision.Daos.RegistrationDao
import com.buddy.revision.Entities.ItemsEntity
import com.buddy.revision.Entities.RegistrationEntities


@Database(
    entities = [ItemsEntity::class, RegistrationEntities::class],
    exportSchema = false,
    version = 1
)
abstract class ItemsDatabase : RoomDatabase() {
    abstract fun getDao(): ItemDao
    abstract fun getRegistrationDao(): RegistrationDao

    companion object {
        private var INSTANCE: ItemsDatabase? = null
        private val LOCK = Any()

        fun invoke(context: Context) = INSTANCE ?: synchronized(LOCK) {
            INSTANCE ?: roomBuilder(context).also {
                INSTANCE = it
            }
        }

        fun roomBuilder(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            ItemsDatabase::class.java,
            "todoListDb"
        ).build()
    }
}