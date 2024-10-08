package com.example.kisaanconnect.ui.registration

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputEditText
import com.example.kisaanconnect.R

class CustomEditText @JvmOverloads
    constructor(private val myContext: Context, attrs: AttributeSet) :
    TextInputEditText(myContext, attrs)
{
    init {

    }

    override fun getBackground(): Drawable? {
        return ContextCompat.getDrawable(myContext, R.drawable.custom_edit_text_background)
    }
}