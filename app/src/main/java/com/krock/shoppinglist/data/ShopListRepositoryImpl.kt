package com.krock.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.krock.shoppinglist.domain.ShopItem
import com.krock.shoppinglist.domain.ShopListRepository

class ShopListRepositoryImpl : ShopListRepository {
    private val shopList : MutableList<ShopItem> = ArrayList()
    private val liveShopList: MutableLiveData<List<ShopItem>> = MutableLiveData();
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
        shopList. add(shopItem)
        update()
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
        update()
    }

    override fun editShopItem(shopItem: ShopItem) {
        val oldElement = getShopItem(shopItem.id)
        shopList.remove(oldElement)
        addShopItem(shopItem)
    }

    override fun getShopItem(shopItemId: Int): ShopItem {
        return shopList.find { it.id == shopItemId }
            ?: throw RuntimeException("Element Id =${shopItemId} not found")
    }

    override fun getShopList(): LiveData<List<ShopItem>> {
        return liveShopList
    }

    private fun update() {
        liveShopList.value = shopList.toList()
    }


    companion object{
        private const val UNDEFINED = -1
    }

}