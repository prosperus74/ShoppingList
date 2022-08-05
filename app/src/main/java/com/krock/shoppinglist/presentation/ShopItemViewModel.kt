package com.krock.shoppinglist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.krock.shoppinglist.data.ShopListRepositoryImpl
import com.krock.shoppinglist.domain.AddShopUseCase
import com.krock.shoppinglist.domain.EditShopItemUseCase
import com.krock.shoppinglist.domain.GetShopItemUseCase
import com.krock.shoppinglist.domain.ShopItem
import java.lang.Exception

class ShopItemViewModel : ViewModel() {
    private val shopListRepository = ShopListRepositoryImpl
    private val editShopItemUseCase = EditShopItemUseCase(shopListRepository)
    private val addShopUseCase = AddShopUseCase(shopListRepository)
    private val getShopItemUseCase = GetShopItemUseCase(shopListRepository)

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    private val _shopItemEdit = MutableLiveData<ShopItem>()
    val shopItemEdit: LiveData<ShopItem>
        get() = _shopItemEdit

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen


    fun getShopItem(shopItemId: Int) {
        _shopItemEdit.value = getShopItemUseCase.getShopItem(shopItemId)
    }

    fun addShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        if (validateInput(name, count)) {
            addShopUseCase.addShopItem(ShopItem(name, count, true))
            finishWork()
        }
    }

    fun editShopItem(inputName: String?, inputCount: String?) {
        val fieldName = parseName(inputName)
        val fieldCount = parseCount(inputCount)
        if (validateInput(fieldName, fieldCount)) {
            _shopItemEdit.value?.let {
                val editShopItem: ShopItem = it.copy(name = fieldName, count = fieldCount)
                editShopItemUseCase.editShopItem(editShopItem)
            }
            finishWork()
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
            _errorInputName.value = true
        }
        if (count <= 0) {
            result = false
            _errorInputCount.value = true
        }
        return result
    }

    fun resetErrorInputName() {
        _errorInputName.value = false
    }

    fun resetErrorInputCount() {
        _errorInputCount.value = false
    }

    private fun finishWork() {
        _shouldCloseScreen.value = Unit
    }

}