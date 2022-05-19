package com.example.flowrspot.di

import com.example.flowrspot.data.repository.FlowrSpotRepositoryImpl
import com.example.flowrspot.data.service.FlowrSpotService
import com.example.flowrspot.domain.repository.FlowrSpotRepository
import com.example.flowrspot.domain.use_case.FlowrSpotUseCases
import com.example.flowrspot.domain.use_case.RetrieveFlowersUseCase
import com.example.flowrspot.domain.use_case.SearchFlowersUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FlowrSpotNetworkModule {

    @Provides
    @Singleton
    fun provideFlowrSpotService(retrofit: Retrofit): FlowrSpotService {
        return retrofit.create(FlowrSpotService::class.java)
    }

    @Provides
    @Singleton
    fun provideFlowrSpotRepository(flowrSpotRepositoryImpl: FlowrSpotRepositoryImpl): FlowrSpotRepository {
        return flowrSpotRepositoryImpl
    }

    @Provides
    @Singleton
    fun provideFlowrSpotUseCases(
        searchFlowersUseCase: SearchFlowersUseCase,
        retrieveFlowersUseCase: RetrieveFlowersUseCase
    ): FlowrSpotUseCases {
        return FlowrSpotUseCases(
            searchFlowersUseCase,
            retrieveFlowersUseCase
        )
    }
}