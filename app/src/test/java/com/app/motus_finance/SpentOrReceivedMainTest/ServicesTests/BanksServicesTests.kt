package com.app.motus_finance.SpentOrReceivedMainTest.ServicesTests

import com.app.motus_finance.Models.DTO.BankDTO
import com.app.motus_finance.Models.DTO.ExpensesDTO
import com.app.motus_finance.Models.Repositories.RepositoriesBank
import com.app.motus_finance.Service.BankService
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
    private lateinit var mockRepositoriesBank: RepositoriesBank
    private lateinit var service: BankService

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        mockRepositoriesBank = mockk()
        service = BankService(mockRepositoriesBank)
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
            img = "url xxx",
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

        // Mock da interação com o repositório (não precisa realmente alterar o banco de dados no teste)
        coEvery { mockRepositoriesBank.updateBalance(any(), any()) } returns Unit

        // Act
        val result = service.updateBalance(bank, expenses)

        // Assert
        // O saldo após o gasto de 30 será 170 (200 - 30)
        assertEquals(bank.copy(balance = 170.00), result)

        // Verifique se o repositório foi chamado para atualizar o saldo
        coVerify { mockRepositoriesBank.updateBalance(1, 170.00) }
    }

    @Test
    fun `should update balance correctly for received transaction`() = runTest {
        // Arrange
        val bank = BankDTO(
            name = "Nubank",
            color = "Purple",
            img = "url xxx",
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
        val result = service.updateBalance(bank, expenses)

        // Assert
        // O saldo após o gasto de 30 será 170 (200 - 30)
        assertEquals(bank.copy(balance = 1600.00), result)

        // Verifique se o repositório foi chamado para atualizar o saldo
        coVerify { mockRepositoriesBank.updateBalance(1, 1600.00) }
    }

    @Test
    fun `should update date correctly when date is equal now date`() = runTest {
        //Assert
        val bank = BankDTO(
            name = "Nubank",
            color = "Purple",
            img = "url xxx",
            balance = 200.00,
            colorSpentsOrReceived = "Light Purple",
            date = LocalDate.now().toString(),
            sum = null
        )

        coEvery { mockRepositoriesBank.updateBankDate(any(), any()) } returns LocalDate.now().plusMonths(1).toString()

        //Act
        val result = service.updateBankDate(1, bank)
        //Assert
        assertEquals( LocalDate.now().plusMonths(1).toString(), result)

        coVerify { mockRepositoriesBank.updateBankDate(1,  LocalDate.now().plusMonths(1).toString()) }
    }
}