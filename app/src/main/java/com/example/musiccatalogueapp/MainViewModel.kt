package com.example.musiccatalogueapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musiccatalogueapp.data.ItunesApi
import com.example.musiccatalogueapp.data.Repository
import kotlinx.coroutines.launch

enum class ApiStatus {LOADING, DONE, ERROR}

class MainViewModel: ViewModel() {

    private val repository = Repository(ItunesApi)

    private val _loading = MutableLiveData<ApiStatus>(ApiStatus.DONE)
    val loading: LiveData<ApiStatus>
        get() = _loading

    val songs = repository.songs

    init {
        loadSongs("")
    }



    fun loadSongs(term: String){
        viewModelScope.launch {
            try {
                _loading.value = ApiStatus.LOADING
                repository.loadSongs(term)
                _loading.value = ApiStatus.DONE
            } catch (e: java.lang.Exception){
                Log.e("MainViewModel", "Could not load songs: $e")
                _loading.value = ApiStatus.ERROR
            }
        }
    }
}