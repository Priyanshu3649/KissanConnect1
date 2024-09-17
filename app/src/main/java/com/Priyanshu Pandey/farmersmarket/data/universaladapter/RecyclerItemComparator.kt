package com.example.kisaanconnect.data.universaladapter

interface RecyclerItemComparator {
    fun isSameItem(other: Any): Boolean
    fun isSameContent(other: Any): Boolean
}
