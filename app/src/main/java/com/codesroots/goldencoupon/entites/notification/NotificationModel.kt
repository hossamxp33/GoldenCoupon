package com.codesroots.goldencoupon.entites.notification

data class NotificationModel(
    var `data`: List<NotificationData>,
    var pagination: Pagination,
    var success: Boolean
)