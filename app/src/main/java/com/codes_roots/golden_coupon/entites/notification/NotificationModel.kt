package com.codes_roots.golden_coupon.entites.notification

data class NotificationModel(
    var `data`: List<NotificationData>,
    var pagination: Pagination,
    var success: Boolean
)