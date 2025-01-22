package com.app.motus_finance.SpentOrReceivedMainTest

import com.app.motus_finance.Models.DTO.ExpensesDTO
import com.app.motus_finance.Service.ExpensesService
import com.app.motus_finance.ViewModel.ExpensesViewModel
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

class ExpensesTests {

    private val testDispatcher = Dispatchers.Unconfined // Use o dispatcher apropriado para testes
    private lateinit var mockService: ExpensesService
    private lateinit var viewModel: ExpensesViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher) // Configura o dispatcher principal para testes
        mockService = mockk()
        viewModel = ExpensesViewModel(mockService)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reseta o dispatcher principal após os testes
    }
   @Test
    fun `when add a expenses should return true`() = runTest {
        //Arrange
        val expenses = ExpensesDTO(
            bankId = 1,
            expenseDescription = "Netflix",
            value = 50.00,
            spentOrReceived = "Spent",
            fixedOrVariable = "Fixed",
            date = "10/01/2025",
            dueDate = "2025-02-22",
            classification = "Streams",
            readyForDeletion = false
        )

        coEvery { mockService.insertExpenses(expenses) } returns true

        //Act
        val result = viewModel.insertExpenses(expenses)

        //Asserts
        coVerify { mockService.insertExpenses(expenses) }
        assertEquals(true, result)

    }

    @Test
    fun `when add a expenses should return false because some fields is null`() = runTest {
        //Arrange
        val expenses = ExpensesDTO(
            bankId = null,
            expenseDescription = "Netflix",
            value = 50.00,
            spentOrReceived = "Spent",
            fixedOrVariable = "Fixed",
            date = "10/01/2025",
            dueDate = "2025-02-22",
            classification = "Streams",
            readyForDeletion = false
        )

        coEvery { mockService.insertExpenses(expenses) } returns true

        //Act
        val result = viewModel.insertExpenses(expenses)

        //Asserts
        coVerify(exactly = 0) { mockService.insertExpenses(expenses) }
        assertEquals(false, result)

    }
}