package com.rajeshbaraili.rajeshbaraili_nycshools.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jp.nycschoolapp.util.Response
import com.jp.nycschools.model.School
import com.jp.nycschools.network.SchoolRepository
import com.jp.nycschools.viewmodel.SchoolViewModel
import com.jp.nysandroidapp.ui.model.Sat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SchoolViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()
    private val testDispatcher = TestCoroutineDispatcher()
    private lateinit var viewModel: SchoolViewModel
    private lateinit var repository: SchoolRepository

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        viewModel = SchoolViewModel(repository)
    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `test fetchSchool with success response`() {
        // mock response when getSchool is called
        val response = listOf(School("1", "test1", "100", "100", "100", "300","tx","1","1","2","eee"))
        coEvery { repository.getSchool() } returns response
        viewModel.schools.observeForever {}

        // Call ViewModelScope
        viewModel.fetchSchools()

        // verifying the result
        assert(viewModel.schools.value is Response.Success)
        assert((viewModel.schools.value as Response.Success<List<Sat>>).data == response)
        viewModel.schools.removeObserver {}
    }

    @Test
    fun `test fetchSchool with error response`() {
        // mock Error Message when called getSchool()
        val errorMsg = "error message"
        coEvery { repository.getSchool() } throws Exception(errorMsg)
       //use the observer on livedata schools
        viewModel.schools.observeForever {}

        // Call ViewModelScope
        viewModel.fetchSchools()

        // verifying the result
        assert(viewModel.schools.value is Response.Error)
        assert((viewModel.schools.value as Response.Error).errorMsg == errorMsg)
        //removing observer
        viewModel.schools.removeObserver {}
    }
}