package com.codes_roots.golden_coupon.entites.fav

import java.util.ArrayList

data class FavouritModel(
    var data: ArrayList<FavoriteData>,
    var pagination: Pagination,
    var success: Boolean
)