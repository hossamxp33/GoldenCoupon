package com.codesroots.goldencoupon.entites.brandsmodel



data class Brand(
    var created_at: Any?=null,
    var favourite_items: List<FavouriteItem>,
    var id: Int?=null,
    var image: String?=null,
    var items: List<Item>?=null,
    var name: String?=null,
    var name_en: String,
    var updated_at: String?=null
)