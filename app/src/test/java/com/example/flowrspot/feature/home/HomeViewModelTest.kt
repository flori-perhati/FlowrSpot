package com.example.flowrspot.feature.home

import app.cash.turbine.test
import com.example.flowrspot.TestDispatchers
import com.example.flowrspot.data.repository.FakeFlowrSpotRepositoryImpl
import com.example.flowrspot.domain.use_case.FlowrSpotUseCases
import com.example.flowrspot.domain.use_case.RetrieveFlowersUseCase
import com.example.flowrspot.domain.use_case.SearchFlowersUseCase
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    private lateinit var viewModel: HomeViewModel
    private lateinit var fakeFlowrSpotRepositoryImpl: FakeFlowrSpotRepositoryImpl

    @Before
    fun setup() {
        fakeFlowrSpotRepositoryImpl = FakeFlowrSpotRepositoryImpl()
        //
        val testDispatchers = TestDispatchers()
        val searchFlowersUseCase = SearchFlowersUseCase(fakeFlowrSpotRepositoryImpl)
        val retrieveFlowersUseCase = RetrieveFlowersUseCase(fakeFlowrSpotRepositoryImpl)
        val flowrSpotUseCases = FlowrSpotUseCases(searchFlowersUseCase, retrieveFlowersUseCase)
        //
        viewModel = HomeViewModel(
            testDispatchers,
            flowrSpotUseCases
        )
    }

    @Test
    fun `retrieve available flowers, state equal to HomeState_ToastMessage`() = runBlocking {
        fakeFlowrSpotRepositoryImpl.setShouldReturnNetworkError(true)
        //
        viewModel.retrieveFlowers()
        val job = launch {
            viewModel.state.test {
                val emission = awaitItem()
                val expected = HomeState.ToastMessage("Error")
                assertThat(emission).isEqualTo(expected)
                cancelAndConsumeRemainingEvents()
            }
        }
        job.join()
        job.cancel()
    }

    @Test
    fun `retrieve available flowers, state equal to HomeState_UpdateFlowersView`() = runBlocking {
        fakeFlowrSpotRepositoryImpl.setShouldReturnNetworkError(false)
        //
        viewModel.retrieveFlowers()
        val job = launch {
            viewModel.state.test {
                val emission = awaitItem()
                val expected = HomeState.UpdateFlowersView
                assertThat(emission).isEqualTo(expected)
                cancelAndConsumeRemainingEvents()
            }
        }
        job.join()
        job.cancel()
    }

    @Test
    fun `search flowers, state equal to HomeState_ToastMessage, value is empty`() = runBlocking {
        fakeFlowrSpotRepositoryImpl.setShouldReturnNetworkError(false)
        //
        viewModel.searchFlowers("")
        val job = launch {
            viewModel.state.test {
                val emission = awaitItem()
                val expected = HomeState.ToastMessage("Search field is empty!!!")
                assertThat(emission).isEqualTo(expected)
                cancelAndConsumeRemainingEvents()
            }
        }
        job.join()
        job.cancel()
    }

    @Test
    fun `search flowers, state equal to HomeState_ToastMessage`() = runBlocking {
        fakeFlowrSpotRepositoryImpl.setShouldReturnNetworkError(false)
        //
        viewModel.searchFlowers("Veronica")
        val job = launch {
            viewModel.state.test {
                val emission = awaitItem()
                val expected = HomeState.ToastMessage("Error")
                assertThat(emission).isEqualTo(expected)
                cancelAndConsumeRemainingEvents()
            }
        }
        job.join()
        job.cancel()
    }

    @Test
    fun `search flowers, state equal to HomeState_UpdateFlowersView`() = runBlocking {
        fakeFlowrSpotRepositoryImpl.setShouldReturnNetworkError(false)
        //
        viewModel.retrieveFlowers()
        val job = launch {
            viewModel.state.test {
                val emission = awaitItem()
                val expected = HomeState.UpdateFlowersView
                assertThat(emission).isEqualTo(expected)
                cancelAndConsumeRemainingEvents()
            }
        }
        job.join()
        job.cancel()
    }
}