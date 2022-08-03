package com.krock.shoppinglist.data

import com.krock.shoppinglist.domain.ShopItem
import com.krock.shoppinglist.domain.ShopListRepository

class ShopListRepositoryImpl : ShopListRepository {
    private val shopList: MutableList<ShopItem> = ArrayList()
    private var itemId = 0
    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == UNDEFINED){
            shopItem.id = itemId++
        }
        shopList.add(shopItem)
    }

    override fun deleteShopItem(shopItemId: Int) {
        val shopItem = getShopItem(shopItemId)
        shopList.remove(shopItem)
    }

    override fun editShopItem(shopItem: ShopItem) {
        deleteShopItem(shopItem.id)
        addShopItem(shopItem)
    }

    override fun getShopItem(shopItemId: Int): ShopItem {
        return shopList.find { it.id == shopItemId }
            ?: throw RuntimeException("Element Id =${shopItemId} not found")
    }

    override fun getShopList(): List<ShopItem> {
        return shopList.toList() // возвращаем копию списка для исключения воздействия
    }
    companion object{
        private const val UNDEFINED = -1
    }

}