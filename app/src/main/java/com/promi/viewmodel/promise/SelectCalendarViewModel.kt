package com.promi.viewmodel.promise

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SelectCalendarViewModel : ViewModel() {
    private val _startDateLiveData: MutableLiveData<String> = MutableLiveData()
    val startDateLiveData: LiveData<String>
        get() = _startDateLiveData

    private val _endDateLiveData: MutableLiveData<String> = MutableLiveData()
    val endDateLiveData: LiveData<String>
        get() = _endDateLiveData

    private val _dateRangeLiveDate: MutableLiveData<Pair<String,String>> = MutableLiveData()
    val dateRangeLiveData: LiveData<Pair<String,String>>
        get() = _dateRangeLiveDate

    fun setDate(date: String){
        // 이미 기간이 선택 되었을 때 -> 초기화
        if (_startDateLiveData.value != "" && _endDateLiveData.value != ""){
            _startDateLiveData.value = ""
            _endDateLiveData.value = ""
        }
        // 시작 날짜만 선택 되었을 때 -> 끝 날짜 선택
        // 끝 날짜가 시작 날짜보다 이전일 때 -> 해당 날짜를 시작 날짜로
        else if (_startDateLiveData.value != "") {
            if (date < _startDateLiveData.value!!)
                _startDateLiveData.value = date
            else
                _endDateLiveData.value = date
            // _dateRangeLiveDate.value = Pair(_startDateLiveData.value!!, _endDateLiveData.value!!)
        }
        // 아무 것도 선택 되어 있지 않을 때 -> 시작 날짜 선택
        else {
            _startDateLiveData.value = date
        }
    }
}