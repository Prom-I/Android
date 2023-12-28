package com.promi.data.remote.model


// Group item DTO
data class GroupMember(
    //var memberImage : String // 사용자 이미지
    var memberName: String, // 그룹에 소속된 사용자의 이름
    var memberId : Int, // 사용자의 아이디 => 나중에 사용자를 삭제할 수 있도록하기 위해..
)