package com.app.motus_finance.SpentOrReceivedMainTest.ServicesTests

import com.app.motus_finance.Models.Repositories.RepositoriesExpenses
import com.app.motus_finance.Service.ExpensesService
import com.app.motus_finance.ViewModel.ExpensesViewModel
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class ExpensesServicesTests {
    private val testDispatcher = Dispatchers.Unconfined // Use o dispatcher apropriado para testes
    private lateinit var mockRepositoriesExpenses: RepositoriesExpenses
    private lateinit var service: ExpensesService

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher) // Configura o dispatcher principal para testes
        mockRepositoriesExpenses = mockk()
        service = ExpensesService(mockRepositoriesExpenses)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reseta o dispatcher principal após os testes
    }
}