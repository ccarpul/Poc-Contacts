package com.example.contactspoc.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts_entity")
data class ContactsEntity (
    @PrimaryKey @ColumnInfo(name = "id")
    var _id: String,
    val name: String?,
    val phone: String?,
    val address: String?,
    val birthday: String?
)