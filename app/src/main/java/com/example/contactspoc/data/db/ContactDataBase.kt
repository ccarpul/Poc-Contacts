package com.example.contactspoc.data.db

import android.content.Context
import android.provider.ContactsContract
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.contactspoc.Constants
import com.example.contactspoc.data.model.Contact


@Database (entities = [ContactsEntity::class], version=5, exportSchema = false)
abstract class ContactDataBase: RoomDatabase() {

    abstract fun contactsDao(): ContactsDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: ContactDataBase? = null

        fun getDatabase(context: Context): ContactDataBase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ContactDataBase::class.java,
                    Constants.contactsDataBase
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

}