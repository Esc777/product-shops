package com.test.shops.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.threeten.bp.Instant

@Parcelize
data class ShopDetail(
    val id: Int,
    val annualProfit: Long,
    val latitude: Double,
    val longitude: Double,
    val openingDate: Instant,
    val status: ShopDetail.Status
): Parcelable {
    enum class Status(val statusName: String) {
        Open("открыт"),
        OpeningSoon("скоро открытие"),
        ClosedForRepairs("закрыт на ремонт"),
        Closed("закрыт")
    }
}