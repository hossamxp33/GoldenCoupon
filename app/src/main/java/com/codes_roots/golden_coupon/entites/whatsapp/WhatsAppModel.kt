package com.codes_roots.golden_coupon.entites.whatsapp

data class WhatsAppModel(
    var `data`: List<Data>,
    var pagination: Pagination,
    var success: Boolean
)