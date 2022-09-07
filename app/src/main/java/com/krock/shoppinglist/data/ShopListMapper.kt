package com.krock.shoppinglist.data

import com.krock.shoppinglist.domain.ShopItem

class ShopListMapper {
    fun shopEntityToDbModel(shopItem: ShopItem): ShopItemDbModel {
        return ShopItemDbModel(
            id = shopItem.id,
            name = shopItem.name,
            count = shopItem.count,
            enabled = shopItem.enabled
        )
    }

    fun dbModelToShopItem(shopItemDbModel: ShopItemDbModel):ShopItem {
        return ShopItem(
            id  = shopItemDbModel.id,
            name = shopItemDbModel.name,
            count = shopItemDbModel.count,
            enabled = shopItemDbModel.enabled
        )
    }

    fun listDBModelToShopItem(listDbModel: List<ShopItemDbModel>):List<ShopItem>{
        return listDbModel.map{dbModelToShopItem(it)}
    }

}