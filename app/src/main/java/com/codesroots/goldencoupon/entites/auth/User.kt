package com.codesroots.goldencoupon.entites.auth

data class User(
    var groupid: Any? = null,
    var token: String? = null,
    var userid: Int? = null,
    var id: Int? = null,
    var username: String? = null,
    var password: String? = null,
    var mobile :String?=null,
    val email:String?=null,
    var email_required: String? = null,
    var active: Int? = null,
    var google_token:String?=null
)