package com.krock.shoppinglist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.krock.shoppinglist.R

class MainActivity : AppCompatActivity(), ShopItemFragment.OnEditingFinishedListener {
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: ShopListAdapter
    private lateinit var buttonAdd: FloatingActionButton
    private var shopItemContainer: FragmentContainerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        shopItemContainer = findViewById(R.id.shop_item_container)
        setRecyclerView()
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.shopList.observe(this) {
            adapter.submitList(it)
            Log.d(TAG, it.toString())
        }
        buttonAdd = findViewById(R.id.button_add_shop_item)
        buttonAdd.setOnClickListener {
            if (isOnePanelMode()) {
                startActivity(ShopItemActivity.newIntentAdd(this))
            } else {
                launchFragment(ShopItemFragment.newInstanceAdd())
            }
        }
    }

    private fun isOnePanelMode(): Boolean {
        return shopItemContainer == null
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

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.shop_item_container, fragment)
            .addToBackStack(null)
            .commit()
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
            if (isOnePanelMode()) {
                startActivity(ShopItemActivity.newIntentEdit(this, it.id))
            } else {
                launchFragment(ShopItemFragment.newInstanceEdit(it.id))
            }
            Log.d(TAG, it.toString())
        }
    }

    private fun setupLongClickListener() {
        adapter.onShopItemLongClick = {
            viewModel.changeEnableState(it)
        }
    }

    override fun onEditingFinished() {
        Toast.makeText(this, "Saved Success", Toast.LENGTH_SHORT).show()
        supportFragmentManager.popBackStack()
    }


    companion object {
        private const val TAG = "MainActivity"
    }

}