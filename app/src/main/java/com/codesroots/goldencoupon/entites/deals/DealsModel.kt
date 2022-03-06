package com.codesroots.goldencoupon.entites.deals

data class DealsModel(
    var data: List<DealsItem>,
    var pagination: Pagination,
    var success: Boolean
)