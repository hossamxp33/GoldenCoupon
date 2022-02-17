package com.codes_roots.golden_coupon.entites.staticpages

data class StaticPagesModel(
    var data: List<StaticPagesItem>,
    var pagination: Pagination,
    var success: Boolean
)