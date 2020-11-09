package com.example.contactspoc.extensions

import com.example.contactspoc.data.db.ContactsEntity
import com.example.contactspoc.data.model.Contact

fun Contact.toEntityContacts() = ContactsEntity(
    _id ?: "",
    name,
    phone,
    address,
    birthday
)

fun ContactsEntity.toContact() = Contact(
        _id,
        name,
        phone,
        address,
        birthday)


