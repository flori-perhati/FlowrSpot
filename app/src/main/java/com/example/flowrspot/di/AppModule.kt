package com.example.flowrspot.di

import com.example.flowrspot.utility.dispatcher.DefaultDispatchers
import com.example.flowrspot.utility.dispatcher.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDispatcherProvider() : DispatcherProvider {
        return DefaultDispatchers()
    }
}