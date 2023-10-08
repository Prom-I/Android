package com.promi.ui.myInformation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyInformationViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is myInformation Fragment"
    }
    val text: LiveData<String> = _text
}