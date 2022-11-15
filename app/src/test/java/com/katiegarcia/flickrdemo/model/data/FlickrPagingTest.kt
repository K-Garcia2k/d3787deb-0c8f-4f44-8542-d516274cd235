package com.katiegarcia.flickrdemo.model.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import com.google.common.truth.Truth.assertThat
import com.katiegarcia.flickrdemo.network.MockFlickrAPI
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertThrows
import org.junit.Test

@ExperimentalPagingApi
@OptIn(ExperimentalCoroutinesApi::class)
class FlickrPagingTest{
    private val photoFactory = FlickrPhoto()

    private val mockAPI = MockFlickrAPI()
   /* private val mockApi = MockRedditApi().apply {
        mockPosts.forEach { post -> addPost(post) }
    }

    @Test
    fun loadReturnsPageWhenOnSuccessfulLoadOfItemKeyedData() = runTest {
        val pagingSource = ItemKeyedSubredditPagingSource(mockApi, DEFAULT_SUBREDDIT)
    }*/

    @Test
    fun `Test data mapping for paging source from valid tags input`() = runTest {
        // Add mock results for the API to return.
        var mockPhotos = mockAPI.flickr_photo_search(1, 1,"Test", "")
        var mockGalleryPhotos = mockPhotos.photos!!.photo
        val remoteMediator = PagingSource.LoadResult.Page(
            data = mockGalleryPhotos,
            prevKey = if (1 == 1) null else 1 - 1,
            nextKey = if (mockGalleryPhotos.isEmpty()) null else 1 + 1
        )
        assertThat(remoteMediator.data).isNotEmpty()
    }

    @Test(expected = NullPointerException::class)
    fun `Test data mapping for paging source from empty tags input`() = runTest {
        // Add mock results for the API to return.
        var mockPhotos = mockAPI.flickr_photo_search(1, 1,"", "")
        var mockGalleryPhotos = mockPhotos.photos!!.photo
        val remoteMediator = PagingSource.LoadResult.Page(
            data = mockGalleryPhotos,
            prevKey = if (1 == 1) null else 1 - 1,
            nextKey = if (mockGalleryPhotos.isEmpty()) null else 1 + 1
        )
    }

}