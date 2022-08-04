package com.krock.shoppinglist.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.krock.shoppinglist.R
import com.krock.shoppinglist.domain.ShopItem
import java.lang.Exception

class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ShopListViewHolder>() {
    var count = 0
    var shopList: List<ShopItem> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopListViewHolder {
        val layoutId = when(viewType) {
            ENABLED ->   R.layout.item_shop_enabled
            DISABLED ->     R.layout.item_shop_disabled
            else -> throw RuntimeException("unknown viewType $viewType")
        }
        val view: View = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        Log.d(TAG, "viewType ${++count}")
        return ShopListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopListViewHolder, position: Int) {
        val shopItem = shopList.get(position)
        holder.tvName.text = shopItem.name
        holder.tvCount.text = shopItem.count.toString()
        Log.d(TAG, " Binding ${shopItem.enabled}")
    }


    override fun getItemCount(): Int {
        return shopList.size
    }

    class ShopListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_name)
        val tvCount: TextView = itemView.findViewById(R.id.tv_count)
    }

    companion object {
        private const val TAG = "ShopListAdapter"
        const val ENABLED = 100
        const val DISABLED = 101
        const val PULL_SIZE = 10
    }

    override fun getItemViewType(position: Int): Int {
        return if (shopList.get(position).enabled) {
            ENABLED
        } else {
            DISABLED
        }
    }

    override fun onViewRecycled(holder: ShopListViewHolder) {
        super.onViewRecycled(holder)
        holder.tvName.text = ""
        holder.tvCount.text = ""
    }

}