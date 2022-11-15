package com.katiegarcia.flickrdemo.network.api.flickr

import com.google.gson.annotations.SerializedName
import com.katiegarcia.flickrdemo.model.data.FlickrPhoto


/*
Class for holding the REST response from /method?flickr.photos.search
Parcelized data class to allow for transmitting of object between fragments and activities for
use with the NavGraph
Null typing to be safe in event of any changes to API
 */
data class FlickrPhotoResponse(
    @SerializedName("photos"        ) var photos: PhotosMetaData?
)

data class PhotosMetaData(
    @SerializedName("page"          ) var page   : Int,
    @SerializedName("photo"         ) var photo  : List<FlickrPhoto>
)
