package com.codesroots.goldencoupon.entites.staticpages

data class StaticPagesModel(
    var data: List<StaticPagesItem>,
    var pagination: Pagination,
    var success: Boolean
)