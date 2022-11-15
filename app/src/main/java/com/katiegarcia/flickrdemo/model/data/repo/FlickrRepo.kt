package com.katiegarcia.flickrdemo.model.data.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.katiegarcia.flickrdemo.model.data.FlickrPaging
import com.katiegarcia.flickrdemo.network.api.flickr.FlickrAPI
import javax.inject.Inject
import javax.inject.Singleton

/*
Gallery Repo built upon a liveData viewModel implementing the Androidx PagingFactory
Allowing for efficient automatic paging of API scrolls and management of how many
items are available on the page. Removing the need for a lot of boiler plate code.
 */
@Singleton
class FlickrRepo @Inject constructor(private val flickrAPI: FlickrAPI) {
    fun getResults(query: String, userID: String?) = Pager(
        config = PagingConfig(
            pageSize = 40, //Allows for over double to be seen on screen
            maxSize = 200, //Stores over 4x the amount that can fit on screen to save resources but not have users need to reload data.
            enablePlaceholders = false
        ),
        pagingSourceFactory = { FlickrPaging(query, userID!!, flickrAPI) }
    ).liveData
}