package com.katiegarcia.flickrdemo.model.data.util

import com.katiegarcia.flickrdemo.model.data.Person
import com.katiegarcia.flickrdemo.module.ApplicationModule
import com.katiegarcia.flickrdemo.network.api.flickr.FlickrAPI
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject
import javax.inject.Singleton

/*
Simple helper class to get the User object from an API call
 */
class UserUtil () {

    companion object {
        suspend fun getUser(userID: String): Person {
            val returnPerson = ApplicationModule.getFlickrAPI(ApplicationModule.getRetroFit()).flickr_user_search(userID).person
            return returnPerson!!
        }
    }

}