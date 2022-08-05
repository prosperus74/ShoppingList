package com.krock.shoppinglist.presentation

import androidx.lifecycle.ViewModel
import com.krock.shoppinglist.data.ShopListRepositoryImpl
import com.krock.shoppinglist.domain.AddShopUseCase
import com.krock.shoppinglist.domain.EditShopItemUseCase
import com.krock.shoppinglist.domain.GetShopItemUseCase
import com.krock.shoppinglist.domain.ShopItem
import java.lang.Exception

class ShopItemViewModel : ViewModel() {
    private val shopListRepository = ShopListRepositoryImpl()
    private val editShopItemUseCase = EditShopItemUseCase(shopListRepository)
    private val addShopUseCase = AddShopUseCase(shopListRepository)
    private val getShopItemUseCase = GetShopItemUseCase(shopListRepository)


    fun getShopItem(shopItemId: Int) {
        getShopItemUseCase.getShopItem(shopItemId)
    }

    fun addShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        if (validateInput(name, count)) {
            editShopItemUseCase.editShopItem(ShopItem(name, count, true))
        }
    }

    fun editShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        if (validateInput(name, count)) {
            addShopUseCase.addShopItem(ShopItem(name, count, true))
        }
    }

    private fun parseName(inputName: String?): String {
        return inputName?.trim() ?: ""
    }

    private fun parseCount(inputCount: String?): Int {
        return try {
            inputCount?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }

    private fun validateInput(name: String, count: Int): Boolean {
        var result = true
        if (name.isEmpty()) {
            result = false
            //TODO show error in field Name
        }
        if (count <= 0) {
            result = false
            //TODO shoe error in field count
        }
        return result
    }


}