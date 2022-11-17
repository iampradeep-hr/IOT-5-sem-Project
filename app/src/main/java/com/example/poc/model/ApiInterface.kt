package com.example.poc.model

import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("pirdata")
    fun getData():Call<DataModelItem>

}
