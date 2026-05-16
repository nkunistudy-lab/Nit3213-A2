package com.example.nit3213app

import com.example.nit3213app.dashboard.DashboardViewModel
import com.example.nit3213app.model.DashboardResponse
import com.example.nit3213app.repository.AppRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DashboardViewModelTest {

    private lateinit var viewModel: DashboardViewModel
    private lateinit var repository: AppRepository
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        repository = mockk()
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getDashboard success updates entitiesState`() = runTest(testDispatcher) {
        // Given
        val mockEntities = listOf(
            mapOf<String, Any?>("name" to "Item 1", "description" to "Desc 1"),
            mapOf<String, Any?>("name" to "Item 2", "description" to "Desc 2")
        )
        val mockResponse = DashboardResponse(entities = mockEntities, entityTotal = 2)
        coEvery { repository.getDashboard("testKey") } returns mockResponse

        // When
        viewModel = DashboardViewModel(repository)
        viewModel.getDashboard("testKey")
        advanceUntilIdle()

        // Then
        assertEquals(2, viewModel.entitiesState.value.size)
        assertEquals("Item 1", viewModel.entitiesState.value[0].properties["name"])
    }

    @Test
    fun `getDashboard failure updates errorState`() = runTest(testDispatcher) {
        // Given
        coEvery { repository.getDashboard("badKey") } throws Exception("Not found")

        // When
        viewModel = DashboardViewModel(repository)
        viewModel.getDashboard("badKey")
        advanceUntilIdle()

        // Then
        assertNotNull(viewModel.errorState.value)
        assertEquals(0, viewModel.entitiesState.value.size)
    }
}