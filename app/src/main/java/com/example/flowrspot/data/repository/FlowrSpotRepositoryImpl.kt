package com.example.flowrspot.data.repository

import com.example.flowrspot.data.dto.toFlowerModelList
import com.example.flowrspot.data.service.FlowrSpotService
import com.example.flowrspot.domain.model.FlowerModel
import com.example.flowrspot.domain.repository.FlowrSpotRepository
import com.example.flowrspot.utility.network.BaseRepository
import com.example.flowrspot.utility.network.Resource
import javax.inject.Inject

class FlowrSpotRepositoryImpl @Inject constructor(
    private val flowrSpotService: FlowrSpotService
): BaseRepository(), FlowrSpotRepository {

    override suspend fun retrieveFlowers(page: Int): Resource<List<FlowerModel>> {
        return safeApiCall { flowrSpotService.retrieveFlowers(page).toFlowerModelList() }
    }

    override suspend fun searchFlowers(value: String): Resource<List<FlowerModel>> {
        return safeApiCall { flowrSpotService.searchFlowers(value).toFlowerModelList() }
    }
}