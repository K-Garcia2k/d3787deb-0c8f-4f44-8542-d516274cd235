package com.katiegarcia.flickrdemo.network

import com.google.common.truth.Truth.assertThat
import com.katiegarcia.flickrdemo.module.ApplicationModule
import com.katiegarcia.flickrdemo.network.api.flickr.FlickrAPI
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


class FlickrAPITest () {

    private lateinit var flickrApi: FlickrAPI

    @Before
    fun setup(){
        flickrApi = ApplicationModule.getFlickrAPI(ApplicationModule.getRetroFit())
    }

    @Test
    fun`tags present when multiple tags are added`() = runTest {
        val flickrResponse = flickrApi.flickr_photo_search(1, 1, "Puppy+Cute", "")
        assertThat(flickrResponse.photos!!.photo[0].tags).contains("puppy")
    }

    @Test
    fun`wrong tag not present when multiple tag is searched`() = runTest {
        val flickrResponse = flickrApi.flickr_photo_search(1, 1, "Puppy", "")
        assertThat(flickrResponse.photos!!.photo[0].tags).doesNotContain("LoremIpsumTestString")
    }

    @Test
    fun`response tags are empty when no tags are added`() = runTest {
        val flickrResponse = flickrApi.flickr_photo_search(1, 1, "", "")
        assertThat(flickrResponse.photos!!.photo[0].tags).isEmpty()
    }



}