package com.example.contactspoc.ui.home

import android.app.Application
import android.provider.ContactsContract
import androidx.lifecycle.LiveData
import com.example.contactspoc.data.db.ContactsDao
import com.example.contactspoc.data.db.ContactsEntity
import com.example.contactspoc.data.model.Contact
import com.example.contactspoc.extensions.ResultWrapper
import com.example.contactspoc.extensions.safeApiCall
import com.example.contactspoc.data.network.ApiContacts
import com.example.contactspoc.di.component.DaggerRepositoryComponent
import com.example.contactspoc.di.modules.ApiModule
import com.example.contactspoc.di.modules.RoomModule
import kotlinx.coroutines.withTimeout
import javax.inject.Inject

class ListContactsRepository(app: Application) {


    @Inject lateinit var apiContacts: ApiContacts

    @Inject lateinit var contactsDao: ContactsDao

    init {
        DaggerRepositoryComponent.builder()
            .apiModule(ApiModule())
            .roomModule(RoomModule(app))
            .build()
            .injectListRepository(this)
    }

    suspend fun getContacts(): ResultWrapper<List<ContactsEntity>> =
        safeApiCall { apiContacts.getContacts() }

    //Room
    suspend fun insert(contactsEntity: List<ContactsEntity>) = contactsDao.insert(contactsEntity)

}