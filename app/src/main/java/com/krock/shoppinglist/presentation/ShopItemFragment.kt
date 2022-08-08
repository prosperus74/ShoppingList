package com.krock.shoppinglist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.krock.shoppinglist.R
import com.krock.shoppinglist.domain.ShopItem

class ShopItemFragment : Fragment() {

    private lateinit var  onEditingFinishedListener :OnEditingFinishedListener

    private lateinit var viewModel: ShopItemViewModel

    private lateinit var til_name: TextInputLayout
    private lateinit var et_name: TextInputEditText
    private lateinit var til_count: TextInputLayout
    private lateinit var et_count: TextInputEditText
    private lateinit var save_button: Button
    private var screenmode = EXTRA_MODE_UNKNOWN
    private var shopItemId = ShopItem.UNDEFINED_ID


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnEditingFinishedListener) {
            onEditingFinishedListener = context
        } else {
            throw RuntimeException(" Activity must implement onEditingFinishedListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkParam()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shop_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(ShopItemViewModel::class.java)
        // перенесли в onCreate checkParam()
        initViews(view)
        when (screenmode) {
            MODE_ADD -> startAddMode()
            MODE_EDIT -> startEditMode()
        }
        listeners()
    }


    private fun listeners() {
        viewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
            onEditingFinishedListener?.onEditingFinished()
        }
        viewModel.errorInputName.observe(viewLifecycleOwner) {
            if (it) {
                til_name.error = "Message error input"
            } else {
                til_name.error = null
            }
        }

        viewModel.errorInputCount.observe(viewLifecycleOwner) {
            if (it) {
                til_count.error = "Count error input"
            } else {
                til_count.error = null
            }
        }

        et_name.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

        et_count.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputCount()
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

    }

    private fun startAddMode() {
        save_button.setOnClickListener {
            viewModel.addShopItem(et_name.text?.toString(), et_count.text?.toString())
        }
    }

    private fun startEditMode() {
        Log.d(TAG, shopItemId.toString())
        viewModel.getShopItem(shopItemId)
        viewModel.shopItemEdit.observe(viewLifecycleOwner) {
            Log.d(TAG, it.toString())
            et_name.setText(it.name)
            et_count.setText(it.count.toString())
        }
        save_button.setOnClickListener {
            viewModel.editShopItem(et_name.text?.toString(), et_count.text?.toString())
        }
    }


    private fun initViews(view: View) {
        til_name = view.findViewById(R.id.til_name)
        et_name = view.findViewById(R.id.et_name)
        til_count = view.findViewById(R.id.til_count)
        et_count = view.findViewById(R.id.et_count)
        save_button = view.findViewById(R.id.save_button)
    }

    private fun checkParam() {
        val args = requireArguments()
        if (!args.containsKey(MODE)) {
            throw RuntimeException("Param screen mode is absent")
        }
        val mode = args.getString(MODE)
        if (mode != MODE_ADD && mode != MODE_EDIT) {
            throw RuntimeException("Unknown screen mode $mode")
        }
        screenmode = mode

        if (screenmode == MODE_EDIT) {
            if (!args.containsKey(ID)) {
                throw RuntimeException("Param shop item id is absent")
            }
            shopItemId = args.getInt(ID)
        }
    }

    interface OnEditingFinishedListener {
        fun onEditingFinished()
    }


    companion object {
        fun newInstanceAdd(): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(MODE, MODE_ADD)
                }
            }
        }

        fun newInstanceEdit(itemId: Int): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(MODE, MODE_EDIT)
                    putInt(ID, itemId)
                }
            }
        }

        private const val MODE = "extra_mode"
        private const val MODE_ADD = "add"
        private const val MODE_EDIT = "edit"
        private const val EXTRA_MODE_UNKNOWN = ""
        private const val ID = "extra_id"
        private const val TAG = "ShopItemActivity"
    }

}