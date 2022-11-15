package com.katiegarcia.flickrdemo.model.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.katiegarcia.flickrdemo.model.data.util.UserUtil
import kotlinx.android.parcel.Parcelize

/*
Class to hold core photo data
Parcelized data class to allow for transmitting of object between fragments and activities for
use with the NavGraph
Null typing to be safe in event of any changes to API
 */
@Parcelize
data class FlickrPhoto(

    @SerializedName("id"        ) var id        : String? = null,
    @SerializedName("owner"     ) var owner     : String? = null,
    @SerializedName("secret"    ) var secret    : String? = null,
    @SerializedName("server"    ) var server    : String? = null,
    @SerializedName("farm"      ) var farm      : Int?    = null,
    @SerializedName("title"     ) var title     : String? = null,
    @SerializedName("ispublic"  ) var ispublic  : Int?    = null,
    @SerializedName("isfriend"  ) var isfriend  : Int?    = null,
    @SerializedName("isfamily"  ) var isfamily  : Int?    = null,
    @SerializedName("ownername" ) var ownername : String? = null,
    @SerializedName("url_q"     ) var urlQ      : String? = null,
    @SerializedName("height_q"  ) var heightQ   : String? = null,
    @SerializedName("width_q"   ) var widthQ    : String? = null,
    @SerializedName("url_c"     ) var urlC      : String? = null,
    @SerializedName("height_c"  ) var heightC   : String? = null,
    @SerializedName("width_c"   ) var widthC    : String? = null,
    @SerializedName("tags"      ) var tags      : String? = null,
    @SerializedName("dateupload") var dateupload: Long? = null,
    @SerializedName("description")var description: FlickrPhotoDescription? = null,

    //Handling User
    var user: Person? = null

) :Parcelable{

    suspend fun getUser(): Person{
        user = UserUtil.getUser(owner!!)
        return user as Person
    }
}

/*
Class for holding the Description for Photo
 */
@Parcelize
data class FlickrPhotoDescription(
    @SerializedName("_content" ) var Content : String? = null
):Parcelable