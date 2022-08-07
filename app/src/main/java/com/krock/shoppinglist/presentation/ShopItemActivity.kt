package com.krock.shoppinglist.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.krock.shoppinglist.R
import com.krock.shoppinglist.domain.ShopItem

class ShopItemActivity : AppCompatActivity() {
//    private lateinit var viewModel: ShopItemViewModel
//
//    private lateinit var til_name: TextInputLayout
//    private lateinit var et_name: TextInputEditText
//    private lateinit var til_count: TextInputLayout
//    private lateinit var et_count: TextInputEditText
//    private lateinit var save_button: Button
    private var screenmode = EXTRA_MODE_UNKNOWN
    private var shopItemId = ShopItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)

//        viewModel = ViewModelProvider(this).get(ShopItemViewModel::class.java)
        checkIntent()
//        initViews()
        val fragment :ShopItemFragment = when (screenmode) {
            EXTRA_MODE_ADD -> ShopItemFragment.newInstanceAdd()
            EXTRA_MODE_EDIT -> ShopItemFragment.newInstanceEdit(shopItemId)
            else -> throw RuntimeException("Unknown screen mode $screenmode")
        }
        supportFragmentManager.beginTransaction()
            .add(R.id.shop_item_container,fragment)
            .commit()

//        listeners()
    }
//
//
//    private fun listeners() {
//
//
//        viewModel.shouldCloseScreen.observe(this) {
//            finish()
//        }
//        viewModel.errorInputName.observe(this) {
//            if (it) {
//                til_name.error = "Message error input"
//            } else {
//                til_name.error = null
//            }
//        }
//
//        viewModel.errorInputCount.observe(this) {
//            if (it) {
//                til_count.error = "Count error input"
//            } else {
//                til_count.error = null
//            }
//        }
//
//        et_name.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//            }
//
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                viewModel.resetErrorInputName()
//            }
//
//            override fun afterTextChanged(p0: Editable?) {
//            }
//
//        })
//
//        et_count.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//            }
//
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                viewModel.resetErrorInputCount()
//            }
//
//            override fun afterTextChanged(p0: Editable?) {
//            }
//        })
//
//    }
//
//    private fun startAddMode() {
//        save_button.setOnClickListener {
//            viewModel.addShopItem(et_name.text?.toString(), et_count.text?.toString())
//        }
//    }
//
//    private fun startEditMode() {
//        Log.d(TAG, shopItemId.toString())
//        viewModel.getShopItem(shopItemId)
//        viewModel.shopItemEdit.observe(this) {
//            Log.d(TAG, it.toString())
//            et_name.setText(it.name)
//            et_count.setText(it.count.toString())
//        }
//        save_button.setOnClickListener {
//            viewModel.editShopItem(et_name.text?.toString(), et_count.text?.toString())
//        }
//    }
//
//
//    private fun initViews() {
//        til_name = findViewById(R.id.til_name)
//        et_name = findViewById(R.id.et_name)
//        til_count = findViewById(R.id.til_count)
//        et_count = findViewById(R.id.et_count)
//        save_button = findViewById(R.id.save_button)
//    }
//
    private fun checkIntent() {
        if (!intent.hasExtra(EXTRA_MODE)) {
            throw RuntimeException("Param screen mode is absent")
        }
        val mode = intent.getStringExtra(EXTRA_MODE)
        if (mode != EXTRA_MODE_ADD && mode != EXTRA_MODE_EDIT) {
            throw RuntimeException("Unknown screen mode $mode")
        } else {
            screenmode = mode
        }
        if (screenmode == EXTRA_MODE_EDIT) {
            if (!intent.hasExtra(EXTRA_ID)) {
                throw RuntimeException("Param shop item id is absent")
            }
            shopItemId = intent.getIntExtra(EXTRA_ID, ShopItem.UNDEFINED_ID)
        }
    }


    companion object {

        fun newIntentAdd(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_MODE, EXTRA_MODE_ADD)
            return intent
        }

        fun newIntentEdit(context: Context, id: Int): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_MODE, EXTRA_MODE_EDIT)
            intent.putExtra(EXTRA_ID, id)
            return intent
        }

        private const val EXTRA_MODE = "extra_mode"
        private const val EXTRA_MODE_ADD = "add"
        private const val EXTRA_MODE_EDIT = "edit"
        private const val EXTRA_MODE_UNKNOWN = ""
        private const val EXTRA_ID = "extra_id"
        private const val TAG = "ShopItemActivity"
    }

}