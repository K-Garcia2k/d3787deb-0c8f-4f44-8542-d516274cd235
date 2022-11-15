package com.katiegarcia.flickrdemo.view.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.katiegarcia.flickrdemo.module.ApplicationModule
import com.katiegarcia.flickrdemo.model.data.repo.FlickrRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/*
View Model for PhotoGallery to handle query changes dynamically for integration with
livedata and the pagingSourceFactory
 */
@HiltViewModel
class PhotoGalleryViewModel @Inject constructor(private val repo: FlickrRepo) : ViewModel() {

    companion object {
        private const val query_default = "Red Panda"
    }

    private val query = MutableLiveData(query_default)
    private var userID = ""

    fun setQuery(queryIn: String, clearUserID: Boolean) {
        if (clearUserID) userID = ""
        query.value = queryIn
    }


    fun setUserID(userIDIn: String) {
        userID = userIDIn
        setQuery("", false)
    }

    val photos = query.switchMap { queryCurrent ->
        repo.getResults(queryCurrent, userID).cachedIn(viewModelScope)
    }




}