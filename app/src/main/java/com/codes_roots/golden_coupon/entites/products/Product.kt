package com.codes_roots.golden_coupon.entites.products


data class Product(
var brand: BrandX,
var brand_id: Int,
var cat_id: Int,
var cpoun: String,
var created: String,
var description: String,
var description_en: String,
var end_date: String,
var id: Int,
var img: String,
var modified: String,
var name: String,
var name_en: String,
var percentage: Int,
var productsizes: List<Productsize>,
var subcat_id: Int,
var url: String
)