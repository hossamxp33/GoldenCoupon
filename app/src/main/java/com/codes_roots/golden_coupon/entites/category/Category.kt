package com.codes_roots.golden_coupon.entites.category

data class Category(
    var created_at: Any?=null,
    var id: Int?=null,
    var name: String?=null,
    var name_en: String?=null,
    var subcats: List<Subcat>?=null,
    var updated_at: String?=null
)