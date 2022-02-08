package com.codes_roots.golden_coupon.entites.fav

import com.codes_roots.golden_coupon.entites.brandsmodel.Brand


data class FavoriteData(
    var brand: Brand,
    var created_at: String?=null,
    var id: Int?=null,
    var item_id: Int?=null,
    var updated_at: String?=null,
    var user_id: Int?=null
)