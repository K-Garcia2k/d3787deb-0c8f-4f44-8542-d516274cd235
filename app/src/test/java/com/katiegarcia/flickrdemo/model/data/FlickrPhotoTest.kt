package com.katiegarcia.flickrdemo.model.data

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Test

class FlickrPhotoTest{

    @Test
    fun `get user while owner is not null returns person object`() = runTest {
        val flickrPhoto = FlickrPhoto(owner = "196925622@N04")
        val person = flickrPhoto.getUser()
        assertThat(person).isNotNull()
    }

    @Test (expected = NullPointerException::class)
    fun `get user while owner is null returns person object`() = runTest {
        val flickrPhoto = FlickrPhoto()
        val person = flickrPhoto.getUser()
    }

}