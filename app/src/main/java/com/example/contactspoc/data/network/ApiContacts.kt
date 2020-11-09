package com.example.contactspoc.data.network

import com.example.contactspoc.Constants
import com.example.contactspoc.data.db.ContactsEntity
import com.example.contactspoc.data.model.Contact
import retrofit2.http.*

interface ApiContacts {

    @GET("${Constants.ENDPOINT}${Constants.API_KEY}")
    suspend fun  getContacts(): ArrayList<ContactsEntity>

    @Headers("Content-Type: application/json")
    @POST("${Constants.ENDPOINT}${Constants.API_KEY}")
    suspend fun saveContact(@Body contact: Contact): Contact

    @Headers("Content-Type: application/json")
    @PUT("${Constants.ENDPOINT}${Constants.API_KEY}/{id}")
    suspend fun updateContact(@Path("id")id: String?, @Body contact: Contact?)

    @DELETE("${Constants.ENDPOINT}${Constants.API_KEY}/{id}")
    suspend fun deleteContact(@Path("id")id: String?)

}