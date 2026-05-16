package com.example.nit3213app

import com.example.nit3213app.login.LoginViewModel
import com.example.nit3213app.model.LoginResponse
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
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    private lateinit var viewModel: LoginViewModel
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
    fun `login success updates loginState with keypass`() = runTest(testDispatcher) {
        // Given
        coEvery { repository.login("testUser", "testPass") } returns LoginResponse(keypass = "topicName")

        // When
        viewModel = LoginViewModel(repository)
        viewModel.login("testUser", "testPass")
        advanceUntilIdle()

        // Then
        assertEquals("topicName", viewModel.loginState.value)
        assertNull(viewModel.errorState.value)
    }

    @Test
    fun `login failure updates errorState with message`() = runTest(testDispatcher) {
        // Given
        coEvery { repository.login("wrong", "wrong") } throws Exception("Invalid credentials")

        // When
        viewModel = LoginViewModel(repository)
        viewModel.login("wrong", "wrong")
        advanceUntilIdle()

        // Then
        assertNull(viewModel.loginState.value)
        assertNotNull(viewModel.errorState.value)
    }

    @Test
    fun `resetLoginState clears the keypass`() = runTest(testDispatcher) {
        // Given
        coEvery { repository.login("user", "pass") } returns LoginResponse(keypass = "key123")
        viewModel = LoginViewModel(repository)
        viewModel.login("user", "pass")
        advanceUntilIdle()

        // When
        viewModel.resetLoginState()

        // Then
        assertNull(viewModel.loginState.value)
    }
    @Test
    fun `login with empty username shows error`() = runTest(testDispatcher) {
        // When
        viewModel = LoginViewModel(repository)
        viewModel.login("", "password")
        advanceUntilIdle()

        // Then
        assertNotNull(viewModel.errorState.value)
        assertNull(viewModel.loginState.value)
    }
}