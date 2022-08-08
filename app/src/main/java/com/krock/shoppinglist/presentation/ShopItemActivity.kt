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

    private var screenmode = EXTRA_MODE_UNKNOWN
    private var shopItemId = ShopItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
        checkIntent()
        if (savedInstanceState == null) {
            launchRightMode()
        }
    }

    private fun launchRightMode() {
        val fragment: ShopItemFragment = when (screenmode) {
            EXTRA_MODE_ADD -> ShopItemFragment.newInstanceAdd()
            EXTRA_MODE_EDIT -> ShopItemFragment.newInstanceEdit(shopItemId)
            else -> throw RuntimeException("Unknown screen mode $screenmode")
        }
        val aa =  supportFragmentManager.beginTransaction()
        supportFragmentManager.beginTransaction()
            .replace(R.id.shop_item_container, fragment)
            .commit()
    }

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