package com.example.flowrspot.domain.repository

import com.example.flowrspot.domain.model.FlowerModel
import com.example.flowrspot.utility.network.Resource

interface FlowrSpotRepository {

    suspend fun retrieveFlowers(page: Int): Resource<List<FlowerModel>>

    suspend fun searchFlowers(value: String): Resource<List<FlowerModel>>
}