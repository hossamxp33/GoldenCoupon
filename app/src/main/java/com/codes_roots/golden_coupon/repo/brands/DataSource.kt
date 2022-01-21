package com.codes_roots.golden_coupon.repo.brands


import com.codes_roots.golden_coupon.entites.brandsmodel.BrandsModel


interface DataSource {

    suspend fun getBrandsResponse(): BrandsModel



}