package com.collinsceleb.money_app.viewmodel

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.collinsceleb.money_app.model.Account
import com.collinsceleb.money_app.repository.AccountRepositoryInterface
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@OptIn(ExperimentalCoroutinesApi::class)
class AccountViewModelTest {
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var application: Application
    private lateinit var accountRepository: AccountRepositoryInterface
    private lateinit var viewModel: AccountViewModel
    private lateinit var accountsObserver: Observer<List<Account>>

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        application = mockk(relaxed = true)
        accountRepository = mockk(relaxed = true)
        viewModel = AccountViewModel(application, accountRepository)

        Dispatchers.setMain(testDispatcher)

        accountsObserver = mockk(relaxed = true)

        viewModel.accounts.observeForever(accountsObserver)
    }

    @Test
    fun `insert should call repository insert`() = runTest {
        val account = Account("3", "11223", "Alice", 200.0)
        coEvery { accountRepository.insert(account) } just Runs
        viewModel.insert(account)
        advanceUntilIdle()
        coVerify { accountRepository.insert(account) }
    }

    @Test
    fun `update should call repository update`() = runTest {
        val account = Account("1", "12345", "John Doe", 700.0)
        coEvery { accountRepository.update(account) } just Runs
        viewModel.update(account)
        advanceUntilIdle()
        coVerify { accountRepository.update(account) }
    }

    @After
    fun tearDown() {
        viewModel.accounts.removeObserver(accountsObserver)
        Dispatchers.resetMain()
    }
}
