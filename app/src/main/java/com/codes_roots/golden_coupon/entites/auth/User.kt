package com.codes_roots.golden_coupon.entites.auth

data class User(
    var groupid: Any?=null,
    var token: String?=null,
    var userid: Int?=null,
    var username: String?=null,
    var password:String?=null
)