package com.app.motus_finance.SpentOrReceivedMainTest

import com.app.motus_finance.Models.DTO.BankDTO
import com.app.motus_finance.Models.DTO.ExpensesDTO
import com.app.motus_finance.Service.BankService
import com.app.motus_finance.ViewModel.BanksViewModel
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class BanksTests {
    private val testDispatcher = Dispatchers.Unconfined
    private lateinit var mockService: BankService
    private lateinit var viewModel: BanksViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        mockService = mockk()
        viewModel = BanksViewModel(mockService)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when I add a bank should return true`() = runTest {
        // Arrange
        val bank = BankDTO(
            name = "Nubank",
            color = "Purple",
            img = 50,
            balance = 200.12,
            colorSpentsOrReceived = "Light Purple",
            date = "03/02/2025",
            sum = null
        )

        coEvery { mockService.insertBank(bank) } returns true

        // Act
        val result = viewModel.insertBank(bank)

        // Assert
        coVerify { mockService.insertBank(bank) }
        assertEquals(true, result)
    }

    @Test
    fun `when I add a bank should return false because something field is null`() = runTest {
        // Arrange
        val bank = BankDTO(
            name = null,
            color = "Purple",
            img = 50,
            balance = 200.12,
            colorSpentsOrReceived = "Light Purple",
            date = "03/02/2025",
            sum = null
        )

        coEvery { mockService.insertBank(bank) } returns false

        // Act
        val result = viewModel.insertBank(bank)

        // Assert
        coVerify(exactly = 0) { mockService.insertBank(bank) }
        assertEquals(false, result)
    }
}
