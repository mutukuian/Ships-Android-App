package com.example.shipsapi.retrofit

import com.example.shipsapi.retrofit.shipsresponse.ShipsResponseList
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("ships")
    suspend fun getShipsList():Response<ShipsResponseList>
}