package com.example.contactspoc.di.modules

import com.example.contactspoc.data.network.ApiContacts
import com.example.contactspoc.data.network.retrofit
import com.example.contactspoc.ui.home.ListContactsRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApiModule {

    @Singleton
    @Provides
    fun provideContactsApi(): ApiContacts = retrofit
}
