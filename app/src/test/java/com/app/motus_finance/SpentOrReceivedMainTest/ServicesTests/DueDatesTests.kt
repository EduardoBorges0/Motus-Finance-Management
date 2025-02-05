package com.app.motus_finance.SpentOrReceivedMainTest.ServicesTests

import com.app.motus_finance.Models.Repositories.RepositoriesMarket
import com.app.motus_finance.Models.Repositories.RepositoriesDueDates
import com.app.motus_finance.Service.DueDateService
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before

class DueDatesTests {

    private val testDispatcher = Dispatchers.Unconfined
    private lateinit var mockRepositoriesDueDates: RepositoriesDueDates
    private lateinit var mockRepositoriesBank: RepositoriesMarket

    private lateinit var service: DueDateService

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        mockRepositoriesDueDates = mockk()
        mockRepositoriesBank = mockk()
        service = DueDateService(mockRepositoriesBank, mockRepositoriesDueDates)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}