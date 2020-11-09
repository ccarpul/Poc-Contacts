package com.example.contactspoc.di.component

import com.example.contactspoc.di.modules.ApiModule
import com.example.contactspoc.di.modules.AppModule
import com.example.contactspoc.di.modules.RoomModule
import com.example.contactspoc.ui.edit.EditRepository
import com.example.contactspoc.ui.home.ListContactsRepository
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiModule::class, AppModule::class, RoomModule::class])
interface RepositoryComponent {

    fun injectListRepository (repository: ListContactsRepository)
    fun injectEditRepository (repository: EditRepository)
}