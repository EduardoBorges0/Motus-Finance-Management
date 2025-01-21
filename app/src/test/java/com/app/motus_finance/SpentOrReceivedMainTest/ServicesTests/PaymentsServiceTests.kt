package com.app.motus_finance.SpentOrReceivedMainTest.ServicesTests

import com.app.motus_finance.Models.DTO.ExpensesDTO
import com.app.motus_finance.Models.DTO.PaymentDTO
import com.app.motus_finance.Models.Repositories.RepositoriesPayments
import com.app.motus_finance.Service.PaymentsService
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

class PaymentsServiceTests {
    private val testDispatcher = Dispatchers.Unconfined
    private lateinit var mockRepositoriesPayments: RepositoriesPayments
    private lateinit var service: PaymentsService

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        mockRepositoriesPayments = mockk()
        service = PaymentsService(mockRepositoriesPayments)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when expense to spent correctly to update payments`() = runTest {
        //Arrange
        val expenses = ExpensesDTO(
            bankId = 1,
            expenseDescription = "School",
            value = 500.00,
            spentOrReceived = "Spent",
            fixedOrVariable = "Fixed",
            date = "10/01/2025",
            classification = "Streams",
            readyForDeletion = false
        )
        val payment = PaymentDTO(
            payment = 1500.00
        )

        coEvery { mockRepositoriesPayments.updatePayments(any()) } returns 1000.00
        //Act
        val result = service.updatePayment(expenses, payment)
        //Assert
        assertEquals( 1000.00, result)
        coVerify { mockRepositoriesPayments.updatePayments(1000.00) }

    }
    @Test
    fun `when expense to received correctly to update payments`() = runTest {
        //Arrange
        val expenses = ExpensesDTO(
            bankId = 1,
            expenseDescription = "School",
            value = 500.00,
            spentOrReceived = "Received",
            fixedOrVariable = "Fixed",
            date = "10/01/2025",
            classification = "Streams",
            readyForDeletion = false
        )
        val payment = PaymentDTO(
            payment = 1500.00
        )

        coEvery { mockRepositoriesPayments.updatePayments(any()) } returns 2000.00
        //Act
        val result = service.updatePayment(expenses, payment)
        //Assert
        assertEquals( 2000.00, result)
        coVerify { mockRepositoriesPayments.updatePayments(2000.00) }

    }
}