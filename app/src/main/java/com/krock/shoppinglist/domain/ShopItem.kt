package com.krock.shoppinglist.domain

data class ShopItem(
    val name: String,
    val count: Int,
    var enabled: Boolean,
    var id: Int = -1
)
