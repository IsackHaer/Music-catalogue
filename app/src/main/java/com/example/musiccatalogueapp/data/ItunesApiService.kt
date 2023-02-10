package com.example.musiccatalogueapp.data

import com.example.musiccatalogueapp.data.model.ServerResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://itunes.apple.com/"

val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface ItunesApiService{
    @GET("search?")
    suspend fun getSongs(@Query("term") term: String, @Query("media") media: String): ServerResponse
}

object ItunesApi{
    val retrofitService: ItunesApiService by lazy { retrofit.create(ItunesApiService::class.java) }
}