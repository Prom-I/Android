package com.promi.viewmodel.promise

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.promi.data.remote.model.RecommendDateAndTime
import com.promi.data.remote.model.RecommendTimeDetail

class RecommendTimeDetailViewModel : ViewModel() {

    // 추천 날짜 버튼 목록들
    private val _recommendDateAndTime = MutableLiveData<List<RecommendDateAndTime>>()
    val recommendDateAndTime: LiveData<List<RecommendDateAndTime>> = _recommendDateAndTime

    // 추천 시간 리스트
    private val _recommendTimeDetail = MutableLiveData<List<RecommendTimeDetail>>()
    val recommendTimeDetail: LiveData<List<RecommendTimeDetail>> = _recommendTimeDetail // 관측 가능

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
        val recommendDateDummyData1 = listOf(
            RecommendTimeDetail("9:00 ~ 10:00",ableFriend1,disableFriend2),
            RecommendTimeDetail("10:00 ~ 11:00",ableFriend2,disableFriend1),
            RecommendTimeDetail("11:00 ~ 12:00",ableFriend1, emptyList())
        )
        val recommendDateDummyData2 = listOf(
            RecommendTimeDetail("12:00 ~ 13:00",ableFriend2,disableFriend1),
            RecommendTimeDetail("14:00 ~ 15:00",ableFriend2, emptyList()),
        )

        val recommendDateAndTimeDummyData = listOf(
            RecommendDateAndTime("8/27(월)",recommendDateDummyData1),
            RecommendDateAndTime("8/28(화)",recommendDateDummyData2),
            RecommendDateAndTime("8/29(목)",recommendDateDummyData1),
            RecommendDateAndTime("8/30(금)",recommendDateDummyData2)

        )

        _recommendTimeDetail.value = recommendDateDummyData1
        _recommendDateAndTime.value = recommendDateAndTimeDummyData

    }
}