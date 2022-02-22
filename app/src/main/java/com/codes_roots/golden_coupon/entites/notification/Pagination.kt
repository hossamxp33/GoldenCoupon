package com.codes_roots.golden_coupon.entites.notification

data class Pagination(
    var count: Int?=null,
    var current_page: Int?=null,
    var has_next_page: Boolean?=null,
    var has_prev_page: Boolean?=null,
    var limit: Any?=null,
    var page_count: Int?=null
)