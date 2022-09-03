package com.krock.shoppinglist.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.krock.shoppinglist.R
import com.krock.shoppinglist.databinding.ItemShopDisabledBinding
import com.krock.shoppinglist.databinding.ItemShopEnabledBinding
import com.krock.shoppinglist.domain.ShopItem
import java.lang.Exception

class ShopListAdapter : ListAdapter<ShopItem,ShopListAdapter.ShopListViewHolder> (ShopItemDiffCallback()) {

    var onShopItemLongClick : ((ShopItem) -> Unit)? =null
    var onShopItemClick : ((ShopItem) -> Unit)? =null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopListViewHolder {
        val layoutId = when(viewType) {
            ENABLED ->   R.layout.item_shop_enabled
            DISABLED ->     R.layout.item_shop_disabled
            else -> throw RuntimeException("unknown viewType $viewType")
        }
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            layoutId,
            parent,
            false
        )
       return ShopListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShopListViewHolder, position: Int) {
        val shopItem = getItem(position)
        val binding = holder.binding

        Log.d(TAG, " Binding ${shopItem.enabled}")

        binding.root.setOnLongClickListener{
            onShopItemLongClick?.invoke(shopItem)
            true
        }

        binding.root.setOnClickListener{
            onShopItemClick?.invoke(shopItem)
        }

        when(binding) {
            is ItemShopEnabledBinding -> {
                binding.shopItemm = shopItem
            }
            is ItemShopDisabledBinding -> {
                binding.shopItem = shopItem
            }

        }
    }

    class ShopListViewHolder(val binding :ViewDataBinding
    ) : RecyclerView.ViewHolder(binding.root)

    companion object {
        private const val TAG = "ShopListAdapter"
        const val ENABLED = 100
        const val DISABLED = 101
        const val PULL_SIZE = 10
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).enabled) {
            ENABLED
        } else {
            DISABLED
        }
    }

    override fun onViewRecycled(holder: ShopListViewHolder) {
        super.onViewRecycled(holder)
       // holder.tvName.text = ""
       // holder.tvCount.text = ""
    }

}