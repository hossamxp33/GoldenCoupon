package com.codes_roots.golden_coupon.presentation.productoffersfragment.mvi

import android.opengl.Visibility
import com.codes_roots.golden_coupon.entites.category.AllCategoryModel
import com.codes_roots.golden_coupon.entites.products.Product
import com.codes_roots.golden_coupon.entites.products.ProductsModel
import com.codes_roots.golden_coupon.presentation.homefragment.mvi.UserError


data class MainViewState(
    var categoryData: AllCategoryModel? = null,
    var productsData: ProductsModel? = null,
    var filteredData: ArrayList<Product>? = null,
    val noProductsFound: Boolean? = null,
    val progress: Boolean? = null,
    var error: UserError? = null,
    var subcategoryVisibility: Boolean? = false,
    var category_position: Int = 0,


)