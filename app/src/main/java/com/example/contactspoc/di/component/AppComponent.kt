package com.example.contactspoc.di.component

import com.example.contactspoc.di.modules.AppModule
import com.example.contactspoc.ui.home.ListContactsFragment
import dagger.Component

@Component(modules = [AppModule::class])
interface AppComponent {

   // fun injectListFragment(fragment: ListContactsFragment)
}