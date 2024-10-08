package com.example.kisaanconnect.data.entities

import com.google.firebase.firestore.DocumentId

data class MessageShell(
    @DocumentId val messageId: String = "",
    val productId: String = "",
    val imgUrl: String = "",
    val participants: List<String> = emptyList()
)