package com.krock.shoppinglist.data

import com.krock.shoppinglist.domain.ShopItem
import com.krock.shoppinglist.domain.ShopListRepository

class ShopListRepositoryImpl : ShopListRepository {
    private val shopList: MutableList<ShopItem> = ArrayList()
    private var itemId = 0

    init {
        for (i in 0 until 10){
            val shopItem = ShopItem("Name${i}",1,true)
            addShopItem(shopItem)
        }

    }


    override fun addShopItem( shopItem: ShopItem) {
        if (shopItem.id == UNDEFINED){
            shopItem.id = itemId++
        }
        shopList.add(shopItem)
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
    }

    override fun editShopItem(shopItem: ShopItem) {
        deleteShopItem(shopItem)
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