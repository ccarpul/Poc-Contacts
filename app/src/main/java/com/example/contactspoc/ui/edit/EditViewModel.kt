package com.example.contactspoc.ui.edit

import android.app.Application
import androidx.lifecycle.*
import com.example.contactspoc.data.db.ContactsEntity
import com.example.contactspoc.data.model.Contact
import com.example.contactspoc.di.component.DaggerVewModelComponent
import com.example.contactspoc.di.modules.AppModule
import com.example.contactspoc.di.modules.ViewModelModule
import com.example.contactspoc.extensions.GenericError
import com.example.contactspoc.extensions.NetworkError
import com.example.contactspoc.extensions.ResultWrapper
import com.example.contactspoc.extensions.Success
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class EditViewModel(app: Application) : AndroidViewModel(app) {

    @Inject lateinit var repository: EditRepository

    private var _stateData = MutableLiveData<StateLiveData>()
    val stateData: LiveData<StateLiveData>
        get() = _stateData


    init {
        DaggerVewModelComponent.builder()
            .appModule(AppModule(getApplication()))
            .viewModelModule(ViewModelModule())
            .build()
            .injectEditViewModel(this)
    }

    //val allContacts: LiveData<List<Contact>> = repository.allContacts

    fun insert(contact: Contact) = viewModelScope.launch(Dispatchers.IO) {
        //repository.insert(contact)
    }

    fun update(contactsEntity: ContactsEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(contactsEntity)
    }

    fun delete(contactsEntity: ContactsEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(contactsEntity)
    }

    fun pushContact(newContact: Contact) = launchDataLoad { repository.postContact(newContact) }
    fun putContact(id: String?, contactUpdate: Contact) = launchDataLoad { repository.putContact(id, contactUpdate) }
    fun deleteContact(id: String) = launchDataLoad { repository.deleteContact(id) }

    private fun launchDataLoad(block: suspend () -> ResultWrapper<Any?>): Job =
        viewModelScope.launch {
            _stateData.value = Loading
            when (val result = block()) {
                is Success -> {
                    _stateData.value = SavedStateData(result.value as? ContactsEntity)
                }
                is NetworkError -> println("NetworkError ${result.throwable}")
                is GenericError -> println("GenericError ${result.code} ${result.error}")
            }
            _stateData.value = PostCall
        }
}

sealed class StateLiveData
object Loading : StateLiveData()
class SavedStateData(val contactsEntity: ContactsEntity?) : StateLiveData()
object PostCall : StateLiveData()