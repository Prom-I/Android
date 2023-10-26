package com.promi.recyclerview.group


// Group item DTO
data class Group(
    var groupName: String, // 그룹 이름
    var groupMemberCount : Int // 그룹에 포함된 전체 인원 수
)