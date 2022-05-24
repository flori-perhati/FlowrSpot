package com.example.flowrspot.data.repository

import com.example.flowrspot.data.service.FlowrSpotService
import com.example.flowrspot.domain.model.FlowerModel
import com.example.flowrspot.utility.MockResponseFileReader
import com.google.gson.GsonBuilder
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import org.junit.Assert.*


class FlowrSpotRepositoryTest {
    private val mockWebServer = MockWebServer()
    private lateinit var okHttpClient: OkHttpClient
    private lateinit var flowrSpotService: FlowrSpotService
    private lateinit var flowrSpotRepositoryImpl: FlowrSpotRepositoryImpl

    @Before
    fun setup() {
        okHttpClient = OkHttpClient.Builder()
            .connectTimeout(2, TimeUnit.SECONDS)
            .readTimeout(2, TimeUnit.SECONDS)
            .writeTimeout(2, TimeUnit.SECONDS)
            .build()

        flowrSpotService  = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))    //this is the local host url (where are tests should be performed)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
            .create(FlowrSpotService::class.java)

        flowrSpotRepositoryImpl = FlowrSpotRepositoryImpl(flowrSpotService)
    }


    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `maps retrieveFlowers response to FlowerModel`() = runBlocking {
        mockWebServer.apply {
            enqueue(MockResponse().setBody(MockResponseFileReader("retrieve_flowers.json").content))
        }

        val actualResponse = flowrSpotRepositoryImpl.retrieveFlowers(1).data!!
        assertFlowerModel(actualResponse[0])
    }

    private fun assertFlowerModel(flowerModel: FlowerModel) {
        assertEquals("bb", flowerModel.name)
        assertEquals("bb", flowerModel.latinName)
        assertEquals("https://flowrspot-api.herokuapp.com/images/medium/missing.jpg", flowerModel.profilePicture)
        assertEquals(false, flowerModel.favorite)
        assertEquals(6, flowerModel.sightings)
    }
}