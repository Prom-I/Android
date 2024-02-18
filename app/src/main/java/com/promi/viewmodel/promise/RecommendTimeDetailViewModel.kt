package com.promi.viewmodel.promise

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.promi.data.remote.model.RecommendTimeDetail

class RecommendTimeDetailViewModel : ViewModel() {

    // 추천 시간 리스트
    private val _recommendTimeDetail = MutableLiveData<List<RecommendTimeDetail>>()
    val recommendDate: LiveData<List<RecommendTimeDetail>> = _recommendTimeDetail // 관측 가능

    private val ableFriend1 = listOf(
        "가능함","가 능","가능가능","이자민","이자민",
        "다섯글자","다섯글자이상")
    private val ableFriend2 = listOf(
        "가능가능","이자민","이자민",
        "다섯글자","다섯글자이상")

    private val disableFriend1 = listOf(
        "불가능","불가능","불가느응","이자민","이자민",
        "다섯글자","다섯글자이상")

    private val disableFriend2 = listOf(
        "불가능","불가능","불가느응","이자민","이자민")

    init {
        // 더미 데이터
        val recommendDateDummyData = listOf(
            RecommendTimeDetail("9:00 ~ 10:00",ableFriend1,disableFriend2),
            RecommendTimeDetail("10:00 ~ 11:00",ableFriend2,disableFriend1),
            RecommendTimeDetail("11:00 ~ 12:00",ableFriend1, emptyList())
        )
        _recommendTimeDetail.value = recommendDateDummyData

    }
}