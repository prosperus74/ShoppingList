package com.krock.shoppinglist.domain

class DeleteShopUseCase(private val shopListRepository:ShopListRepository) {
    fun deleteShopItem(shopItem :ShopItem){
        shopListRepository.deleteShopItem(shopItem)
    }
}