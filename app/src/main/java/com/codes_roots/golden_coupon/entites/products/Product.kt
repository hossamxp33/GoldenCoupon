package com.codes_roots.golden_coupon.entites.products



data class Product(
    var brand: BrandX?=null,
    var brand_id: Int?=null,
    var cat_id: Int?=null,
    var cpoun: String?=null,
    var created: String?=null,
    var description: String?=null,
    var description_en: String?=null,
    var end_date: String?=null,
    var id: Int?=null,
    var img: String?=null,
    var modified: String?=null,
    var name: String?=null,
    var name_en: String?=null,
    var percentage: Int?=null,
    var productsizes: List<Productsize>?=null,
    var subcat_id: Int?=null,
    var url: String?=null,
    var url_en: String?=null
)