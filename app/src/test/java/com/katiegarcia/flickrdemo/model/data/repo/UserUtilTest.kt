package com.katiegarcia.flickrdemo.model.data.repo

import com.google.common.truth.Truth.assertThat
import com.katiegarcia.flickrdemo.model.data.util.UserUtil
import com.katiegarcia.flickrdemo.network.MockFlickrAPI
import kotlinx.coroutines.test.runTest
import org.junit.Test

class UserUtilTest{

    var mockFlickrAPI = MockFlickrAPI()

    @Test(expected = NullPointerException::class)
    fun `UserID being blank returns null exception`() = runTest {
        UserUtil.getUser("")
    }

    @Test
    fun `UserID being having a valid ID returns person`() = runTest {
        assertThat(UserUtil.getUser("196925622@N04")).isNotNull()
    }


}