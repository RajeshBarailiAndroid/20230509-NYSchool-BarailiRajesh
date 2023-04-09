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
class SatViewModel @Inject constructor(private val repository: SchoolRepository) : ViewModel() {

    private val _sat = MutableLiveData<Response<List<Sat>>>()
    val sat: LiveData<Response<List<Sat>>>
        get() = _sat

    init {
        fentchSat()
    }

    fun fentchSat() {
        _sat.postValue(Response.Loading())
        viewModelScope.launch {
            try {
                _sat.postValue(Response.Success(repository.getSat()))
            } catch (e: Exception) {
                _sat.postValue(Response.Error(e.message.toString()))
            }
        }
    }

}