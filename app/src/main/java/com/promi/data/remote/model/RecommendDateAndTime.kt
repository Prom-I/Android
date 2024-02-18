package com.promi.data.remote.model

data class RecommendDateAndTime (
    var recommendDate : String, // 8/27일(월)
    var recommendTimeList : List<RecommendTimeDetail> // 해당 날짜의 추천 시간 및 가능,불가능 친구
)