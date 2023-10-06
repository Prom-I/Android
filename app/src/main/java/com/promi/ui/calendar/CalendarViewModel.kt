package com.promi.ui.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CalendarViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Calender Fragment"
    }
    val text: LiveData<String> = _text
}