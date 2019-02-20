package com.test.shops.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Shop(
    val id: Int,
    val number: Int,
    val name: String,
    val address: String,
    val shopDetail: ShopDetail?
) : Parcelable {
    constructor(id: Int, number: Int, name: String, address: String) : this(
        id,
        number,
        name,
        address,
        null
    )
}





