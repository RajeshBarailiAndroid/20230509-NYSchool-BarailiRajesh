package com.rajeshbaraili.rajeshbaraili_nycshools.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jp.nycschools.model.School

class SharedViewModel : ViewModel() {

    var schools = MutableLiveData<School>()

}