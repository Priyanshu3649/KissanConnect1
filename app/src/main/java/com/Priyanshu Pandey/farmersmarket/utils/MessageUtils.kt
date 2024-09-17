package com.example.kisaanconnect.utils

import androidx.annotation.LayoutRes
import com.example.kisaanconnect.R
import com.example.kisaanconnect.BR
import com.example.kisaanconnect.data.entities.ChatMessage
import com.example.kisaanconnect.data.entities.Message
import com.example.kisaanconnect.data.universaladapter.RecyclerItem

const val DATE_SEPARATOR = "this-is-date-separator"

sealed class ChatRecyclerViewModel(val chatMessage: ChatMessage, @LayoutRes val layoutId: Int)

class  SentRecyclerViewModel(val chat: ChatMessage)
    : ChatRecyclerViewModel(chat, R.layout.chat_message)

class  ReceiveRecyclerViewModel(val chat: ChatMessage)
    : ChatRecyclerViewModel(chat, R.layout.chat_message_user)

class DateGroupRecyclerViewModel(val chat: ChatMessage)
    : ChatRecyclerViewModel(chat, R.layout.message_time_separator)

fun ChatRecyclerViewModel.toRecyclerItem() = RecyclerItem(
    data = chatMessage,
    layoutId = layoutId,
    variableId = BR.viewmodel
)

class MessageItemViewModel(val message: Message) {
    lateinit var itemClickHandler: (messageId: String) -> Unit

    fun onItemClick() {
        itemClickHandler(message.id)
    }
}

fun MessageItemViewModel.toRecyclerItem() = RecyclerItem(
    data = this,
    layoutId = R.layout.message_item,
    variableId = BR.viewModel
)