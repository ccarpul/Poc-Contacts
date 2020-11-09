package com.example.contactspoc.di.component

import com.example.contactspoc.di.modules.AppModule
import com.example.contactspoc.di.modules.ViewModelModule
import com.example.contactspoc.ui.edit.EditViewModel
import com.example.contactspoc.ui.home.ListContactsViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ViewModelModule::class, AppModule::class])
interface VewModelComponent {

    fun injectListViewModel(viewModel: ListContactsViewModel)
    fun injectEditViewModel(viewModel: EditViewModel)
}