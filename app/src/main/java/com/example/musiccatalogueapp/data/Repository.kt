package com.example.musiccatalogueapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.musiccatalogueapp.data.model.Song

class Repository(private val api: ItunesApi) {

    private var _songs = MutableLiveData<MutableList<Song>>()
    val songs: LiveData<MutableList<Song>>
        get() = _songs

    suspend fun loadSongs(term: String){
        _songs.value = api.retrofitService.getSongs(term, "music").results
    }

}