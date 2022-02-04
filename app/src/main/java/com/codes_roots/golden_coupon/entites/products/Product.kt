package com.codes_roots.golden_coupon.entites.products

data class Product(
    var blue: String?=null,
    var cat_id: Int?=null,
    var created: String?=null,
    var description: String?=null,
    var description_en: String?=null,
    var green: String?=null,
    var id: Int?=null,
    var img: String?=null,
    var modified: String?=null,
    var name: String?=null,
    var name_en: String?=null,
    var productsizes: List<Any>?=null,
    var red: String?=null,
    var subcat_id: Int?=null,
    var visible: String?=null
)