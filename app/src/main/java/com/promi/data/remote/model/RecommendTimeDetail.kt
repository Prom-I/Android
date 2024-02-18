package com.promi.data.remote.model

// 약속 추천 시간 상세 페이지 관련 아이템
// 9:00~10:00
// 가능한 친구
// 불가능한 친구
data class RecommendTimeDetail (
    var recommendTime : String,
    var ableFriendList : List<String>,
    var disableFriendList : List<String>
)