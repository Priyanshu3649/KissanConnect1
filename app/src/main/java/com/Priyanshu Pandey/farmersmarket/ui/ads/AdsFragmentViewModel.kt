package com.example.kisaanconnect.ui.ads

import androidx.lifecycle.*
import com.google.firebase.Timestamp
import com.example.kisaanconnect.data.entities.AdItem
import com.example.kisaanconnect.data.entities.Product
import com.example.kisaanconnect.data.universaladapter.RecyclerItem
import com.example.kisaanconnect.firebase.services.ProductServices
import com.example.kisaanconnect.utils.Event
import com.example.kisaanconnect.utils.InterestedAdsViewModel
import com.example.kisaanconnect.utils.PostedAdsViewModel
import com.example.kisaanconnect.utils.toRecyclerItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import java.util.*

const val NEW_PRODUCT = "New Product"

@ExperimentalCoroutinesApi
class AdsFragmentViewModel(private val service: ProductServices) : ViewModel() {

    private val _eventAdsDetails = MutableLiveData<Event<Product>>()
    val eventAdsDetails: LiveData<Event<Product>>
        get() = _eventAdsDetails

    private val _eventEditAds = MutableLiveData<Event<String>>()
    val eventEditAds: LiveData<Event<String>>
        get() = _eventEditAds

    private val _postedAdsList = service.myItems().asLiveData()
    val postedAdsList: LiveData<List<RecyclerItem>> = _postedAdsList.map { list ->
        list.map { item ->
            val timestamp = if (item.location != null) {
                Timestamp(Date(item.location.time))
            } else Timestamp.now()

            PostedAdsViewModel(
                AdItem(
                    itemId = item.id,
                    name = item.name,
                    price = item.priceStr,
                    quantity = item.qtyAvailable,
                    imageUrl = item.imgUrls.getOrElse(0) { "" },
                    timestamp = timestamp
                )
            ).apply {
                itemClickHandler = { postedItemClicked(itemId = this.item.itemId) }
            }.toRecyclerItem()
        }
    }

    val interestedAdsList: LiveData<List<RecyclerItem>> = service.interestedItems().asLiveData().map { list ->
        list.map { item ->
            InterestedAdsViewModel(item).apply {
                itemClickHandler = { interestedAddClicked(itemId = this.item.itemId) }
            }.toRecyclerItem()
        }
    }

    private fun interestedAddClicked(itemId: String) {
        viewModelScope.launch {
            service.getProduct(itemId)?.let { _eventAdsDetails.postValue(Event(it)) }
        }
    }

    private fun postedItemClicked(itemId: String) {
        _eventEditAds.value = Event(itemId)
    }

    fun postNewAd() {
        _eventEditAds.value = Event(NEW_PRODUCT)
    }

    fun getProduct(productId: String) : Product? {
        if(productId == NEW_PRODUCT) {
            return null
        }
        return _postedAdsList.value?.find {  it.id == productId  }
    }
 }

@Suppress("UNCHECKED_CAST")
@ExperimentalCoroutinesApi
class AdsFragmentViewModelFactory(private val services: ProductServices) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        (AdsFragmentViewModel(services) as T)
}