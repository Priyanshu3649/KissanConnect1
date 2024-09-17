package com.example.kisaanconnect.data.repository

import android.net.Uri
import androidx.lifecycle.LiveData
import com.example.kisaanconnect.data.entities.*

interface IRepository {
    fun searchProducts(searchString: String): LiveData<List<Product>>
    suspend fun getUser(sellerId: String): User
    suspend fun getRecentProducts(): List<Product>
    suspend fun getCloseByProduct(): List<CloseByProduct>
    suspend fun getCategory() : List<String>
    suspend fun uploadPicture(file: Uri?): String
    fun getCurrentUser(): User
    fun insertProduct(product: Product)
    fun getMessages(messageId: String): LiveData<List<ChatMessage>>
    suspend fun getMessageRecipients(messageId: String): List<String>
    fun sendMessage(chatMessage: ChatMessage)
    suspend fun getPostedAds(): List<AdItem>
    suspend fun getInterestedAds(): List<AdItem>
    suspend fun getAd(itemId: String): Product
    suspend fun getMessageList(): List<Message>?
}
