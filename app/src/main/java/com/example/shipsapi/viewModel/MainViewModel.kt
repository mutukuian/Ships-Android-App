package com.example.shipsapi.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shipsapi.repository.ApiRepository
import com.example.shipsapi.retrofit.shipsresponse.ShipsResponseListItem
import com.example.shipsapi.utils.DataStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(private val repository: ApiRepository)
    : ViewModel() {

        /*
        List of ships
         */

    private val _shipsList = MutableLiveData<DataStatus<List<ShipsResponseListItem>>>()
        val shipsList : LiveData<DataStatus<List<ShipsResponseListItem>>>
            get() = _shipsList

    fun getShipsList() = viewModelScope.launch {
        repository.getShipsList().collect{
            _shipsList.value =it
        }
    }
}