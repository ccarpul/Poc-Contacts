package com.example.contactspoc.di.modules

import android.app.Application
import com.example.contactspoc.di.factory.ListContactViewModelFactory
import com.example.contactspoc.ui.edit.EditRepository
import com.example.contactspoc.ui.home.ListContactsRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ViewModelModule {

    @Provides fun listContactsRepository(app: Application): ListContactsRepository = ListContactsRepository(app)
    @Provides fun editRepository(app:Application): EditRepository = EditRepository(app)


/**////////////////**************/
    //@Provides
    //@Singleton
   // fun providesViewModelFactory() = ListContactViewModelFactory(ListContactsRepository(app))
}