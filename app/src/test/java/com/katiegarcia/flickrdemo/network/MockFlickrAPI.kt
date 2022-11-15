package com.katiegarcia.flickrdemo.network

import com.katiegarcia.flickrdemo.model.data.FlickrPhoto
import com.katiegarcia.flickrdemo.model.data.Person
import com.katiegarcia.flickrdemo.network.api.flickr.FlickrAPI
import com.katiegarcia.flickrdemo.network.api.flickr.FlickrPhotoResponse
import com.katiegarcia.flickrdemo.network.api.flickr.FlickrUserResponse
import com.katiegarcia.flickrdemo.network.api.flickr.PhotosMetaData


/*
Retrofit2 implementation for the flickr rest services.
Null safe calls that allow for customisation in the view model implementations.
 */

class MockFlickrAPI() : FlickrAPI{
    override suspend fun flickr_photo_search(
        per_page: Int,
        page: Int,
        tags: String,
        userID: String
    ): FlickrPhotoResponse {
        if(tags.length > 0){
            var photoMockList = mutableListOf(FlickrPhoto())
            return FlickrPhotoResponse(PhotosMetaData(1, photoMockList))
        } else {
            return FlickrPhotoResponse(null)
        }

    }

    override suspend fun flickr_user_search(user_id: String): FlickrUserResponse {
        if(user_id.length > 0){
            return FlickrUserResponse(Person())
        } else {
            return FlickrUserResponse(null)
        }

    }

}