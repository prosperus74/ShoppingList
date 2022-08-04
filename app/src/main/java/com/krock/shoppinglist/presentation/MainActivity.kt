package com.krock.shoppinglist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.krock.shoppinglist.R
import com.krock.shoppinglist.domain.ShopItem
import com.krock.shoppinglist.presentation.ShopListAdapter.Companion.ENABLED

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter :ShopListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        setRecyclerView()
        viewModel.shopList.observe(this) {
            adapter.shopList = it
            Log.d("MainActivity", it.toString())
        }
    }


    private fun setRecyclerView() {
        val rvChopList = this.findViewById<RecyclerView>(R.id.rv_shop_list)
        adapter = ShopListAdapter()
        rvChopList.adapter = adapter
        rvChopList.recycledViewPool.setMaxRecycledViews(ShopListAdapter.ENABLED,ShopListAdapter.PULL_SIZE)
        rvChopList.recycledViewPool.setMaxRecycledViews(ShopListAdapter.DISABLED,ShopListAdapter.PULL_SIZE)
    }



}