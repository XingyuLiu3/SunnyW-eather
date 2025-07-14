package com.example.sunnyweather.ui.place

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sunnyweather.logic.Repository
import com.example.sunnyweather.logic.model.Place

class PlaceViewModel : ViewModel() {
    private val searchLiveData = MutableLiveData<String>()
    val placeList = ArrayList<Place>()

    val placeLiveData: LiveData<Result<List<Place>>> = MediatorLiveData<Result<List<Place>>>().apply {
        addSource(searchLiveData) { query ->
            value = Repository.searchPlaces(query).value
        }
    }

     fun searchPlaces(query: String) {
        searchLiveData.value = query
    }
}




