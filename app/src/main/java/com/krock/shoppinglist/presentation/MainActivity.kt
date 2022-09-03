package com.krock.shoppinglist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
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
import com.krock.shoppinglist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), ShopItemFragment.OnEditingFinishedListener {
    private lateinit var viewModel: MainViewModel
    private lateinit var shopListAdapter: ShopListAdapter
    private lateinit var buttonAdd: FloatingActionButton
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setRecyclerView()
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.shopList.observe(this) {
            shopListAdapter.submitList(it)
            Log.d(TAG, it.toString())
        }

        binding.buttonAddShopItem.setOnClickListener {
            if (isOnePanelMode()) {
                startActivity(ShopItemActivity.newIntentAdd(this))
            } else {
                launchFragment(ShopItemFragment.newInstanceAdd())
            }
        }
    }

    private fun isOnePanelMode(): Boolean {
        return binding.shopItemContainer == null
    }

    private fun setRecyclerView() {

        with(binding.rvShopList) {
            shopListAdapter = ShopListAdapter()
            adapter = shopListAdapter
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.ENABLED,
                ShopListAdapter.PULL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.DISABLED,
                ShopListAdapter.PULL_SIZE
            )
        }
        setupLongClickListener()
        setupClickListener()
        setupSwipedListener(binding.rvShopList)
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
                viewModel.deleteShopItem(shopListAdapter.currentList.get(viewHolder.adapterPosition))
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(rvChopList)
    }

    private fun setupClickListener() {
        shopListAdapter.onShopItemClick = {
            if (isOnePanelMode()) {
                startActivity(ShopItemActivity.newIntentEdit(this, it.id))
            } else {
                launchFragment(ShopItemFragment.newInstanceEdit(it.id))
            }
            Log.d(TAG, it.toString())
        }
    }

    private fun setupLongClickListener() {
        shopListAdapter.onShopItemLongClick = {
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