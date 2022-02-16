package com.codes_roots.golden_coupon.entites.deals

data class DealsModel(
    var data: List<DealsItem>,
    var pagination: Pagination,
    var success: Boolean
)