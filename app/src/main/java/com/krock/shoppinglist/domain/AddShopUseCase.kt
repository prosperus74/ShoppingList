package com.krock.shoppinglist.domain

class AddShopUseCase(private val shopListRepository: ShopListRepository)  {
    fun addShopItem(shopItem :ShopItem){
        shopListRepository.addShopItem(shopItem)
    }
}