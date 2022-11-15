package com.katiegarcia.flickrdemo.network.api.flickr

import androidx.lifecycle.LiveData
import com.github.leonardoxh.livedatacalladapter.Resource
import com.katiegarcia.flickrdemo.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*


/*
Retrofit2 implementation for the flickr rest services.
Null safe calls that allow for customisation in the view model implementations.
 */
interface FlickrAPI {

    companion object {
        const val API_KEY = BuildConfig.API_KEY
        const val URL = "https://api.flickr.com/services/rest/"
    }

    /*
    Additions worth considering:
        - safe_search query for allowing different image results
        - license for allowing users to search difference types
        - tag_mode to allow for all and any selection by user
     */
    @GET("?method=flickr.photos.search&api_key=$API_KEY&media=photos&extras=url_q%2Curl_c%2Cowner_name%2Ctags%2Cdescription%2Cdate_upload&format=json&nojsoncallback=1")
    suspend fun flickr_photo_search(
        @Query("per_page") per_page: Int, //Defaults to 100
        @Query("page") page: Int, //defaults to 1
        @Query("tags") tags: String, //Requires + separated for multiple tags
        @Query("user_id") userID: String,
    ) : FlickrPhotoResponse

    @GET("?method=flickr.people.getInfo&api_key=$API_KEY&format=json&nojsoncallback=1")
    suspend fun flickr_user_search(
        @Query("user_id") user_id: String //Format example : 196925622%40N04 
    ) : FlickrUserResponse
}