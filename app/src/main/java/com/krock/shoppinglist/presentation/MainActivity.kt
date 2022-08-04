package com.krock.shoppinglist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.krock.shoppinglist.R

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: ShopListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        setRecyclerView()
        viewModel.shopList.observe(this) {
            adapter.submitList(it)
            Log.d(TAG, it.toString())
        }
    }

    private fun setRecyclerView() {
        val rvChopList = this.findViewById<RecyclerView>(R.id.rv_shop_list)
        adapter = ShopListAdapter()
        rvChopList.adapter = adapter
        rvChopList.recycledViewPool.setMaxRecycledViews(
            ShopListAdapter.ENABLED,
            ShopListAdapter.PULL_SIZE
        )
        rvChopList.recycledViewPool.setMaxRecycledViews(
            ShopListAdapter.DISABLED,
            ShopListAdapter.PULL_SIZE
        )
        setupLongClickListener()
        setupClickListener()
        setupSwipedListener(rvChopList)
    }

    private fun setupSwipedListener(rvChopList: RecyclerView) {
        val simpleCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                viewModel.deleteShopItem(adapter.currentList.get(viewHolder.adapterPosition))
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(rvChopList)
    }

    private fun setupClickListener() {
        adapter.onShopItemClick = {
            Log.d(TAG, it.toString())
        }
    }

    private fun setupLongClickListener() {
        adapter.onShopItemLongClick = {
            viewModel.changeEnableState(it)
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }

}