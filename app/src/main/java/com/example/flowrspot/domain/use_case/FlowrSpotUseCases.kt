package com.example.flowrspot.domain.use_case

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
data class FlowrSpotUseCases @Inject constructor(
    val searchFlowersUseCase: SearchFlowersUseCase,
    val retrieveFlowersUseCase: RetrieveFlowersUseCase
)
