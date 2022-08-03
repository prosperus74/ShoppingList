package com.krock.shoppinglist.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.krock.shoppinglist.data.ShopListRepositoryImpl
import com.krock.shoppinglist.domain.*

class MainViewModel : ViewModel() {


    private val shopListRepository = ShopListRepositoryImpl()

    private val getShopListUseCase = GetShopListUseCase(shopListRepository)
    private val editShopItemUseCase = EditShopItemUseCase(shopListRepository)
    private val deleteShopUseCase = DeleteShopUseCase(shopListRepository)
    private val getShopItemUseCase = GetShopItemUseCase(shopListRepository)

    val shopList = MutableLiveData<List<ShopItem>>()

    fun getShopList() {
        shopList.value = getShopListUseCase.getShopList()
    }

    fun deleteShopItem(shopItem :ShopItem){
        deleteShopUseCase.deleteShopItem(shopItem)
        getShopList()
    }

    fun editShopItemU(shopItem :ShopItem){
        val shopItemTemp = shopItem.copy(enabled = !shopItem.enabled)
        editShopItemUseCase.editShopItem(shopItemTemp)
        getShopList()
    }


}