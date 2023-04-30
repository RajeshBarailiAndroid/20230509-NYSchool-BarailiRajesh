package com.jp.nycschools.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jp.nycschoolapp.util.Response
import com.jp.nycschools.model.School
import com.jp.nycschools.network.SchoolRepository
import com.jp.nysandroidapp.ui.model.Sat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SchoolViewModel @Inject constructor(private val repository: SchoolRepository) : ViewModel() {
    private val _schools = MutableLiveData<Response<List<School>>>()
    val schools: LiveData<Response<List<School>>>
        get() = _schools

    init {
        fentchSchools()
    }

    fun fentchSchools() {
        _schools.postValue(Response.Loading())
        viewModelScope.launch {
            try {
                _schools.postValue(Response.Success(repository.getSchool()))
            } catch (e: Exception) {
                _schools.postValue(Response.Error(e.message.toString()))
            }
        }
    }
}
