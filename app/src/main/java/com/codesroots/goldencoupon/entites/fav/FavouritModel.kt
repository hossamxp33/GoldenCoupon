package com.codesroots.goldencoupon.entites.fav

import java.util.ArrayList

data class FavouritModel(
    var data: ArrayList<FavoriteData>,
    var pagination: Pagination,
    var success: Boolean
)