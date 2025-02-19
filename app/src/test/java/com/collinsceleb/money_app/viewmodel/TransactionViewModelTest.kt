package com.collinsceleb.money_app.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.collinsceleb.money_app.model.Transaction
import com.collinsceleb.money_app.repository.TransactionRepository
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.rules.TestRule
@OptIn(ExperimentalCoroutinesApi::class)
class TransactionViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var transactionRepository: TransactionRepository
    private lateinit var viewModel: TransactionViewModel

    private lateinit var transactionObserver: Observer<List<Transaction>>
    private lateinit var transferStatusObserver: Observer<Boolean>

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        transactionRepository = mockk(relaxed = true)
        viewModel = TransactionViewModel(transactionRepository)

        Dispatchers.setMain(testDispatcher)

        transactionObserver = mockk(relaxed = true)
        transferStatusObserver = mockk(relaxed = true)

        viewModel.fetchAllTransactions.observeForever(transactionObserver)
        viewModel.transferStatus.observeForever(transferStatusObserver)
    }

    @Test
    fun `fetchAllTransactions should return list of transactions`() {
        val transactions = listOf(

            Transaction(1, "12345", "67890", 100.0, System.currentTimeMillis()),
            Transaction(2, "67890", "12345", 50.0, System.currentTimeMillis())
        )

        every { transactionRepository.getAllTransactions() } returns mockk(relaxed = true)
        viewModel.fetchAllTransactions.observeForever(transactionObserver)
        verify { transactionRepository.getAllTransactions() }
    }

    @Test
    fun `transferMoney should update transferStatus when successful`() = runTest {
        coEvery { transactionRepository.transferMoney("12345", "67890", 100.0) } returns true
        viewModel.transferMoney("12345", "67890", 100.0)

        advanceUntilIdle()
        verify { transferStatusObserver.onChanged(true) }
        coVerify { transactionRepository.transferMoney("12345", "67890", 100.0) }
    }

    @Test
    fun `transferMoney should update transferStatus when failed`() = runTest {
        coEvery { transactionRepository.transferMoney("12345", "67890", 500.0) } returns false
        viewModel.transferMoney("12345", "67890", 500.0)
        advanceUntilIdle()
        verify { transferStatusObserver.onChanged(false) }
        coVerify { transactionRepository.transferMoney("12345", "67890", 500.0) }
    }

    @After
    fun tearDown() {
        viewModel.fetchAllTransactions.removeObserver(transactionObserver)
        viewModel.transferStatus.removeObserver(transferStatusObserver)
        Dispatchers.resetMain()
    }
}