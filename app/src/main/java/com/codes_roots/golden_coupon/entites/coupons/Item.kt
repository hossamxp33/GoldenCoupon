package com.codes_roots.golden_coupon.entites.coupons

data class CouponItem(
    var brand_id: Int,
    var category_id: Int,
    var created_at: String,
    var description: String,
    var description_en: String,
    var discount_code: String,
    var discount_percent: String,
    var id: Int,
    var last_used: String,
    var offer: String,
    var updated_at: String,
    var url: String,
    var used_count: Int
)