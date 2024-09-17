package com.example.kisaanconnect.utils

import androidx.annotation.LayoutRes
import com.example.kisaanconnect.BR
import com.example.kisaanconnect.R
import com.example.kisaanconnect.data.entities.AdItem
import com.example.kisaanconnect.data.universaladapter.RecyclerItem

sealed class AddItemViewModel(val item: AdItem, @LayoutRes val layoutId: Int) {
    lateinit var removeItemHandler: (itemId: String) -> Unit
    lateinit var itemClickHandler: (itemId: String) -> Unit

    fun removeItem() {
        removeItemHandler(item.itemId)
    }

    fun itemClicked() {
        itemClickHandler(item.itemId)
    }
}

class PostedAdsViewModel(thisItem: AdItem) :
    AddItemViewModel(thisItem, R.layout.ads_product_item)

class InterestedAdsViewModel(thisItem: AdItem) :
    AddItemViewModel(thisItem, R.layout.interested_product_item)

fun AddItemViewModel.toRecyclerItem() = RecyclerItem(
    data = this,
    layoutId = layoutId,
    variableId = BR.viewModel
)