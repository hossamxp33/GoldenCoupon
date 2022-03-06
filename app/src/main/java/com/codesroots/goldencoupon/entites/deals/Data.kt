package com.codesroots.goldencoupon.entites.deals

data class DealsItem(
    var created_at: String,
    var discount_code: String,
    var url: String,
    var id: Int,
    var image: String,
    var updated_at: String
)