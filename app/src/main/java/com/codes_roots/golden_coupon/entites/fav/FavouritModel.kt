package com.codes_roots.golden_coupon.entites.fav

data class FavouritModel(
    var data: List<FavoriteData>,
    var pagination: Pagination,
    var success: Boolean
)