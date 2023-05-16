package com.rajeshbaraili.rajeshbaraili_nycshools.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jp.nycschoolapp.util.Response
import com.jp.nycschools.network.SchoolRepository
import com.jp.nycschools.viewmodel.SatViewModel
import com.jp.nysandroidapp.ui.model.Sat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class SatViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()
    private val testDispatcher = TestCoroutineDispatcher()
    private lateinit var viewModel: SatViewModel
    private lateinit var repository: SchoolRepository

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        viewModel = SatViewModel(repository)
    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `test fentchSat with success response`() {
        // Given
        val response = listOf(Sat("1", "200", "100", "100", "100", "300"))
        coEvery { repository.getSat() } returns response

        //use the observer on livedata schools
        viewModel.sat.observeForever { }

        // Call ViewModelScope
        viewModel.fetchSat()

        // verifying the result
        assert(viewModel.sat.value is Response.Success)
        assert((viewModel.sat.value as Response.Success<List<Sat>>).data == response)
        //removing the observer
        viewModel.sat.removeObserver {}
    }

    @Test
    fun `test fentchSat with error response`() {
        // mock Error Message when called getSat()
        val errorMsg = "error message"
        coEvery { repository.getSat() } throws Exception(errorMsg)

        //use the observer on livedata sat
        viewModel.sat.observeForever {}
        // Call ViewModelScope
        viewModel.fetchSat()

        // verifying the result
        assert(viewModel.sat.value is Response.Error)
        assert((viewModel.sat.value as Response.Error).errorMsg == errorMsg)

        //removing the observer
        viewModel.sat.removeObserver {}
    }
}