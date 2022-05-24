package com.example.flowrspot.data.repository

import com.example.flowrspot.domain.model.FlowerModel
import com.example.flowrspot.domain.repository.FlowrSpotRepository
import com.example.flowrspot.utility.network.Resource

class FakeFlowrSpotRepositoryImpl: FlowrSpotRepository {

    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }

    override suspend fun retrieveFlowers(page: Int): Resource<List<FlowerModel>> {
        return if (shouldReturnNetworkError)
            Resource.Error("Error")
        else
            Resource.Success(arrayListOf())
    }

    override suspend fun searchFlowers(value: String): Resource<List<FlowerModel>> {
        return if (shouldReturnNetworkError)
            Resource.Error("Error")
        else
            Resource.Success(arrayListOf())
    }

}