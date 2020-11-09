package com.example.contactspoc.di.modules

import android.app.Application
import com.example.contactspoc.data.db.ContactDataBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RoomModule (app: Application){

    val contactsDataBase: ContactDataBase = ContactDataBase.getDatabase(app)

    @Singleton
    @Provides
    fun providesDao () = contactsDataBase.contactsDao()

}