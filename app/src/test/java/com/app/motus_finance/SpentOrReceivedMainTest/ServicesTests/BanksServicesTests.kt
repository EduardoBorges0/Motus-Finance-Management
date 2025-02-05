package com.app.motus_finance.SpentOrReceivedMainTest.ServicesTests

import com.app.motus_finance.Models.DTO.BankDTO
import com.app.motus_finance.Models.DTO.ExpensesDTO
import com.app.motus_finance.Models.Repositories.RepositoriesMarket
import com.app.motus_finance.Service.MarketService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

class BanksServicesTests {
    private val testDispatcher = Dispatchers.Unconfined
    private lateinit var mockRepositoriesBank: RepositoriesMarket
    private lateinit var service: MarketService

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        mockRepositoriesBank = mockk()
        service = MarketService(mockRepositoriesBank)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
    @Test
    fun `should update balance correctly for spent transaction`() = runTest {
        // Arrange
        val bank = BankDTO(
            name = "Nubank",
            color = "Purple",
            img = 50,
            balance = 200.00,
            colorSpentsOrReceived = "Light Purple",
            date = "03/02/2025",
            sum = null
        )
        val expenses = ExpensesDTO(
            bankId = 1,
            expenseDescription = "Netflix",
            value = 30.00,
            spentOrReceived = "Spent", // Esta é uma despesa
            fixedOrVariable = "Fixed",
            date = "10/01/2025",
            dueDate = "2025-02-22",
            classification = "Streams",
            readyForDeletion = false
        )

        coEvery { mockRepositoriesBank.updateBalance(any(), any()) } returns Unit

        // Act
        val result = service.updateBalance(15, expenses)

        // Assert
        assertEquals(bank.copy(balance = 170.00), result)

        coVerify { mockRepositoriesBank.updateBalance(1, 170.00) }
    }

    @Test
    fun `should update balance correctly for received transaction`() = runTest {
        // Arrange
        val bank = BankDTO(
            name = "Nubank",
            color = "Purple",
            img = 50,
            balance = 200.00,
            colorSpentsOrReceived = "Light Purple",
            date = "03/02/2025",
            sum = null
        )
        val expenses = ExpensesDTO(
            bankId = 1,
            expenseDescription = "Payment",
            value = 1400.00,
            spentOrReceived = "Received", // Esta é uma despesa
            fixedOrVariable = "Fixed",
            date = "05/02/2025",
            dueDate = "2025-02-22",
            classification = "",
            readyForDeletion = false
        )

        coEvery { mockRepositoriesBank.updateBalance(any(), any()) } returns Unit

        // Act
        val result = service.updateBalance(15, expenses)

        // Assert
        assertEquals(bank.copy(balance = 1600.00), result)

        coVerify { mockRepositoriesBank.updateBalance(1, 1600.00) }
    }

    @Test
    fun `should update date correctly when date is equal now date`() = runTest {
        //Assert
        val bank = BankDTO(
            name = "Nubank",
            color = "Purple",
            img = 50,
            balance = 200.00,
            colorSpentsOrReceived = "Light Purple",
            date = LocalDate.now().toString(),
            sum = null
        )


        //Act
        val result = service.updateBankDate(1, bank)
        //Assert
        assertEquals( LocalDate.now().plusMonths(1).toString(), result)

        coVerify { mockRepositoriesBank.updateBankDate(1,  LocalDate.now().plusMonths(1).toString()) }
    }
}