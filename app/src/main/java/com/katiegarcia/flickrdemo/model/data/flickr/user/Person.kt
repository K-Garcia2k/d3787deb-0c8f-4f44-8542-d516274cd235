package com.katiegarcia.flickrdemo.model.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/*
Class to hold user data
Parcelized data class to allow for transmitting of object between fragments and activities for
use with the NavGraph
Null typing to be safe in event of any changes to API
 */

@Parcelize
data class Person(

    @SerializedName("id"                             ) var id: String? = null,
    @SerializedName("nsid"                           ) var nsid: String?      = null,
    @SerializedName("iconserver"                     ) var iconserver: Int?         = 0,
    @SerializedName("iconfarm"                       ) var iconfarm: Int?         = 0,
    @SerializedName("username"                       ) var username: Username?    = Username(),
    @SerializedName("realname"                       ) var realname: Realname?    = Realname(),
    @SerializedName("description"                    ) var description: Description? = Description(),
    var userProfileURL: String? = ""
    ) :Parcelable {
        fun getURL(): String{
            if(iconfarm!! > 0){
                return "https://farm${iconfarm}.staticflickr.com/${iconserver}/buddyicons/${nsid}.jpg"
            } else {
                return "https://www.flickr.com/images/buddyicon.gif"
            }
        }
    }

/*
Used to hold the Real Name for Person
 */
@Parcelize
data class Realname (

  @SerializedName("_content" ) var Content : String? = null

) :Parcelable

/*
Used to hold the description for Person
 */
@Parcelize
data class Description (

  @SerializedName("_content" ) var Content : String? = null

) :Parcelable

/*
Used to hold the Username for Person
 */
@Parcelize
data class Username (

  @SerializedName("_content" ) var Content : String? = null

) :Parcelable