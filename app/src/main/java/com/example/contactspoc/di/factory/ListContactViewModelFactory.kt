package com.example.contactspoc.di.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.contactspoc.ui.home.ListContactsRepository
import com.example.contactspoc.ui.home.ListContactsViewModel
import javax.inject.Inject

class ListContactViewModelFactory
@Inject constructor(private var listContactsRepository: ListContactsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        TODO("Not yet implemented")
    }

    //override fun <T : ViewModel?> create(modelClass: Class<T>): T = ListContactsViewModel(listContactsRepository) as T

}