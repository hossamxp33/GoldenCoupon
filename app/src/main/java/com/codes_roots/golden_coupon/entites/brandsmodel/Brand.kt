package com.codes_roots.golden_coupon.entites.brandsmodel


data class Brand(
    var created_at: Any?=null,
    var id: Int?=null,
    var image: String?=null,
    var items: List<Item>?=null,
    var name: String?=null,
    var updated_at: String?=null
)