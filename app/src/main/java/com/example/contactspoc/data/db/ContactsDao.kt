package com.example.contactspoc.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.contactspoc.data.model.Contact

@Dao
interface ContactsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(contact: List<ContactsEntity>)

    @Query("SELECT * FROM contacts_entity ORDER BY name ASC")
    suspend fun getContact(): List<ContactsEntity>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(contact: ContactsEntity)

    @Delete
    suspend fun delete(contactsEntity: ContactsEntity)
}