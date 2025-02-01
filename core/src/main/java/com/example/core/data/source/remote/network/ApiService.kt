package com.example.core.data.source.remote.network


import com.example.core.data.source.remote.response.TourismResponse
import retrofit2.http.GET

interface ApiService {
    @GET("list")
    suspend fun getList(): TourismResponse // hapus call, tambahkan suspend
}