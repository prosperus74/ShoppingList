package com.krock.shoppinglist.presentation

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("errorInputName")
fun bindErrorInputName(textInputLayout: TextInputLayout, isError:Boolean){
    val message  = if (isError) {
          textInputLayout.error = "Message error input"
    } else {
         null
    }
}

@BindingAdapter("errorInputCount")
fun bindErrorInputCount(textInputLayout: TextInputLayout, isError:Boolean){
    val message  = if (isError) {
        textInputLayout.error = "Message error input count"
    } else {
        null
    }
}