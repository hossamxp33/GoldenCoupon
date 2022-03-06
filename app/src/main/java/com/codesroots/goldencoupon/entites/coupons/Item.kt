package com.codesroots.goldencoupon.entites.coupons

import com.codesroots.goldencoupon.entites.brandsmodel.Item

data class CouponItem(
    var brand_id: Int?=null,
    var category_id: Int?=null,
    var created_at: String?=null,
    var description: String?=null,
    var description_en: String?=null,
    var discount_code: String?=null,
    var discount_percent: String?=null,
    var id: Int?=null,
    var used_coupons: List<Item>?=null,
    var last_used: String?=null,
    var offer: String?=null,
    var updated_at: String?=null,
    var url: String?=null,
    var url_en: String?=null,
    var used_count: Int?=null,
)