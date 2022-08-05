package com.krock.shoppinglist.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.SortedList
import com.krock.shoppinglist.domain.ShopItem
import com.krock.shoppinglist.domain.ShopListRepository
import kotlin.random.Random

object ShopListRepositoryImpl : ShopListRepository {
    private val shopList = sortedSetOf<ShopItem>({ p0, p1 ->
        p0.id.compareTo(p1.id)
    })

    private val liveShopList: MutableLiveData<List<ShopItem>> = MutableLiveData();
    private var itemId = 0

    init {
        for (i in 0 until 2000) {
            val shopItem = ShopItem("Name${i}", 1, Random.nextBoolean())
            addShopItem(shopItem)
        }

    }


    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.UNDEFINED_ID) {
            shopItem.id = itemId++
        }
        shopList.add(shopItem)
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
        Log.d(TAG, shopList.toString())
        liveShopList.value = shopList.toList()
    }

    private const val TAG = "ShopListRepositoryImpl"

}