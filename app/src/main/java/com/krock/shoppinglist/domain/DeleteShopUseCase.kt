package com.krock.shoppinglist.domain

class DeleteShopUseCase(private val shopListRepository:ShopListRepository) {
    fun deleteShopItem(shopItemId :Int){
        shopListRepository.deleteShopItem(shopItemId)
    }
}