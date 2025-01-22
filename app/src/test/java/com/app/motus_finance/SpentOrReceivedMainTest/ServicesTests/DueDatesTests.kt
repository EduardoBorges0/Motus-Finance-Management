package com.app.motus_finance.SpentOrReceivedMainTest.ServicesTests

import com.app.motus_finance.Models.Repositories.RepositoriesBank
import com.app.motus_finance.Models.Repositories.RepositoriesDueDates
import com.app.motus_finance.Service.BankService
import com.app.motus_finance.Service.DueDateService
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class DueDatesTests {

    private val testDispatcher = Dispatchers.Unconfined
    private lateinit var mockRepositoriesDueDates: RepositoriesDueDates
    private lateinit var mockRepositoriesBank: RepositoriesBank

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