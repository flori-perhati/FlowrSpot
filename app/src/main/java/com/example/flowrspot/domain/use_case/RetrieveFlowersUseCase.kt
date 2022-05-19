package com.example.flowrspot.domain.use_case

import com.example.flowrspot.domain.model.FlowerModel
import com.example.flowrspot.domain.repository.FlowrSpotRepository
import com.example.flowrspot.utility.network.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RetrieveFlowersUseCase @Inject constructor(
    private val flowrSpotRepository: FlowrSpotRepository
) {
    operator fun invoke(page: Int): Flow<Resource<List<FlowerModel>>> = flow {
        emit(Resource.Loading())
        val response = flowrSpotRepository.retrieveFlowers(page)
        emit(response)
    }
}