package com.katiegarcia.flickrdemo.model.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.katiegarcia.flickrdemo.network.api.flickr.FlickrAPI
import retrofit2.HttpException
import java.io.IOException


/*
Class for defining the PagingSource, handles the query, user ID and config to pass back a source
that can be handled with LiveData or other observables
 */
class FlickrPaging(
    private val query: String,
    private val userID: String,
    private val flickrAPI: FlickrAPI
) : PagingSource<Int, FlickrPhoto>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FlickrPhoto> {
        val pos = params.key ?: 1 // Starting page default
        return try {
            //Use a replace here as the final part of the business logic to allow for other
            //classes and objects to not need to worry about the logic.
            val response = flickrAPI.flickr_photo_search(params.loadSize, pos, query.replace(" ", "+"), userID)
            val galleryPhotos = response.photos!!.photo

            LoadResult.Page(
                data = galleryPhotos,
                prevKey = if (pos == 1) null else pos - 1,
                nextKey = if (galleryPhotos.isEmpty()) null else pos + 1
            )
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, FlickrPhoto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}