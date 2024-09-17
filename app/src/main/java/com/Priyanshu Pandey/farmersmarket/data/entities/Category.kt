package com.example.kisaanconnect.data.entities

import com.google.firebase.firestore.DocumentId

data class Category(@DocumentId val id: String = "", val type: String = ""){
    override fun toString(): String {
        return type
    }
}