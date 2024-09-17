package com.example.kisaanconnect.ui.message

import androidx.lifecycle.*
import com.example.kisaanconnect.data.universaladapter.RecyclerItem
import com.example.kisaanconnect.firebase.services.ProductServices
import com.example.kisaanconnect.utils.Event
import com.example.kisaanconnect.utils.MessageItemViewModel
import com.example.kisaanconnect.utils.toRecyclerItem
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MessageViewModel(service: ProductServices) : ViewModel() {

    private val _messages = service.getMessageList().asLiveData()
    val messages: LiveData<List<RecyclerItem>> = _messages.map { list ->
        list.filter { it.message.isNotEmpty() }
            .sortedByDescending { it.timeStamp }
            .map { message ->
                MessageItemViewModel(message).apply {
                    itemClickHandler = { messageClicked(message.id) }

                }.toRecyclerItem()
            }
    }

    private val _eventMessage = MutableLiveData<Event<String>>()
    val eventMessage: LiveData<Event<String>>
        get() = _eventMessage

    private fun messageClicked(messageId: String) {
        _eventMessage.value = Event(messageId)
    }
}

@ExperimentalCoroutinesApi
@Suppress("UNCHECKED_CAST")
class MessageViewModelFactory(private val service: ProductServices) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        (MessageViewModel(service) as T)
}
