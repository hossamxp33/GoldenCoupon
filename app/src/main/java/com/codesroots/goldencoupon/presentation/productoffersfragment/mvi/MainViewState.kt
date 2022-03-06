package com.codesroots.goldencoupon.presentation.productoffersfragment.mvi

import com.codesroots.goldencoupon.entites.allbrands.AllBrandsModel
import com.codesroots.goldencoupon.entites.category.AllCategoryModel
import com.codesroots.goldencoupon.entites.products.Product
import com.codesroots.goldencoupon.entites.products.ProductsModel
import com.codesroots.goldencoupon.presentation.homefragment.mvi.UserError


data class MainViewState(
    var categoryData: AllCategoryModel? = null,
    var productsData: ProductsModel? = null,
    var allBrandsData:AllBrandsModel?=null,
    var filteredData: List<Product>? = null,
    val noProductsFound: Boolean? = null,
    val progress: Boolean? = null,
    val page : Int?=null,
    val country_id : Int? = null,
    var error: UserError? = null,
    var subcategoryVisibility: Boolean? = false,
    var category_position: Int = 0,


)