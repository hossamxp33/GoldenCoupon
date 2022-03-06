package com.codesroots.goldencoupon.entites.whatsapp

data class WhatsAppModel(
    var data: List<Data>?=null,
    var pagination: Pagination?=null,
    var success: Boolean?=null
)