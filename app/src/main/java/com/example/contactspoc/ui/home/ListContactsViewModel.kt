package com.example.contactspoc.ui.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.contactspoc.data.db.ContactsEntity
import com.example.contactspoc.extensions.GenericError
import com.example.contactspoc.extensions.NetworkError
import com.example.contactspoc.extensions.ResultWrapper
import com.example.contactspoc.extensions.Success
import com.example.contactspoc.data.model.Contact
import com.example.contactspoc.di.component.DaggerVewModelComponent
import com.example.contactspoc.di.modules.AppModule
import com.example.contactspoc.di.modules.ViewModelModule
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class ListContactsViewModel(app: Application) : AndroidViewModel(app) {

    @Inject
    lateinit var repository: ListContactsRepository

    private var _stateData = MutableLiveData<StateLiveData>()
    private var restoreData: MutableList<ContactsEntity>? = null
    val stateData: LiveData<StateLiveData>
        get() {
            if (_stateData.value == null) refresh()
            else _stateData.value = ListContactsState(restoreData)
            return _stateData
        }

    init {
        DaggerVewModelComponent.builder()
            .appModule(AppModule(getApplication()))
            .viewModelModule(ViewModelModule())
            .build()
            .injectListViewModel(this)
    }

    private fun refresh() = launchDataLoadApi { repository.getContacts() }

    private fun launchDataLoadApi(block: suspend () -> ResultWrapper<Any>): Job =
        viewModelScope.launch {
            _stateData.value = Loading
            when (val result = block()) {
                is Success -> {
                    restoreData = result.value as? MutableList<ContactsEntity>

                }
                is NetworkError -> {
                    restoreData = repository.contactsDao.getContact().toMutableList()
                    Log.d("carpul", " NetworkError ${result.throwable} ")
                    Log.i("carpul", "$restoreData ")
                }

                is GenericError -> {
                    Log.d("carpul", "GenericError ${result.code} ${result.error}")
                    restoreData = repository.contactsDao.getContact().toMutableList()
                    Log.i("carpul", "$restoreData ")
                }
        }
            restoreData?.toList()?.let { repository.insert(it) }
            _stateData.value = ListContactsState(restoreData)
            _stateData.value = PostCall
        }
}

sealed class StateLiveData
object Loading : StateLiveData()
class ListContactsState(val contacts: MutableList<ContactsEntity>?) : StateLiveData()
object PostCall : StateLiveData()