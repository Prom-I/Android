package com.promi.ui.calendar

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CalendarViewModel : ViewModel() {
        private val _positionLiveData: MutableLiveData<Int> = MutableLiveData()
        val positionLiveData: LiveData<Int>
            get() = _positionLiveData

        private val _yearMonthLiveData: MutableLiveData<String> = MutableLiveData()
        val yearMonthLiveData: LiveData<String>
            get() = _yearMonthLiveData

        fun setPosition(index:Int){
            Log.d("posLiveDataa", "$index MyViewModel")
            _positionLiveData.postValue(index)
        }
    }