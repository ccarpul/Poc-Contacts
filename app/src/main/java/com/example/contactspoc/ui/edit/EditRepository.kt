package com.example.contactspoc.ui.edit

import android.app.Application
import com.example.contactspoc.data.db.ContactsDao
import com.example.contactspoc.data.db.ContactsEntity
import com.example.contactspoc.data.model.Contact
import com.example.contactspoc.data.network.ApiContacts
import com.example.contactspoc.di.component.DaggerRepositoryComponent
import com.example.contactspoc.di.modules.ApiModule
import com.example.contactspoc.di.modules.RoomModule
import com.example.contactspoc.extensions.ResultWrapper
import com.example.contactspoc.extensions.safeApiCall
import javax.inject.Inject

class EditRepository(app: Application) {

    @Inject lateinit var apiContacts: ApiContacts

    @Inject lateinit var contactDao: ContactsDao

    init {
        DaggerRepositoryComponent.builder()
            .apiModule(ApiModule())
            .roomModule(RoomModule(app))
            .build()
            .injectEditRepository(this)
    }

    suspend fun postContact(contact: Contact): ResultWrapper<Contact> =
        safeApiCall { apiContacts.saveContact(contact) }

    suspend fun putContact(id: String?, contactUpdate: Contact?) =
        safeApiCall { apiContacts.updateContact(id, contactUpdate) }

    suspend fun deleteContact(id: String) = safeApiCall { apiContacts.deleteContact(id) }



    //Room
    suspend fun insert(contact: Contact) {
//        contactDao.insert(contact)
    }

    suspend fun update(contactsEntity: ContactsEntity) {
        contactDao.update(contactsEntity)
    }

    suspend fun delete(contactsEntity: ContactsEntity) {
           contactDao.delete(contactsEntity)
    }

}