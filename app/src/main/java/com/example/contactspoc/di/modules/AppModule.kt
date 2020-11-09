package com.example.contactspoc.di.modules

import android.app.Application
import com.example.contactspoc.di.factory.ListContactViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(application: Application) {

    val app: Application = application

    @Provides
    @Singleton
    fun providesApp() = app

}