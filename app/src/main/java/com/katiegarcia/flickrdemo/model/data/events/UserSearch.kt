package com.katiegarcia.flickrdemo.model.data.events

import com.katiegarcia.flickrdemo.network.api.flickr.PhotosMetaData

/*
UserSearch Data Class for use with EventBus for onClick Listeners
 */
data class UserSearch(val userID: String)
