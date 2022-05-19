package com.example.flowrspot.data.service

import com.example.flowrspot.data.dto.FlowerDto
import retrofit2.http.GET
import retrofit2.http.Query

interface FlowrSpotService {

    @GET("api/v1/flowers")
    suspend fun retrieveFlowers(@Query("page") page: Int): FlowerDto

    @GET("api/v1/flowers/search")
    suspend fun searchFlowers(@Query("query") value: String): FlowerDto
}