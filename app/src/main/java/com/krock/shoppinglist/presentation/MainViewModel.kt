package com.krock.shoppinglist.presentation

import android.app.Application
import androidx.lifecycle.*
import com.krock.shoppinglist.data.ShopListRepositoryImpl
import com.krock.shoppinglist.domain.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val shopListRepository = ShopListRepositoryImpl(application)

    private val getShopListUseCase = GetShopListUseCase(shopListRepository)
    private val editShopItemUseCase = EditShopItemUseCase(shopListRepository)
    private val deleteShopUseCase = DeleteShopUseCase(shopListRepository)
    private val getShopItemUseCase = GetShopItemUseCase(shopListRepository)

    val shopList = getShopListUseCase.getShopList() //MutableLiveData<List<ShopItem>>()

    fun deleteShopItem(shopItem: ShopItem) {
        viewModelScope.launch {
            deleteShopUseCase.deleteShopItem(shopItem)
        }
    }

    fun changeEnableState(shopItem: ShopItem) {
        viewModelScope.launch {
            val shopItemTemp = shopItem.copy(enabled = !shopItem.enabled)
            editShopItemUseCase.editShopItem(shopItemTemp)
        }
    }

}