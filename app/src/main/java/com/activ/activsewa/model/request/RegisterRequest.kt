package com.activ.activsewa.model.request

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
class RegisterRequest (
    @Expose
    @SerializedName("name")
    var name : String,
    @Expose
    @SerializedName("email")
    var email : String,
    @Expose
    @SerializedName("password")
    var password : String,
    @Expose
    @SerializedName("telp")
    var telp : String
) : Parcelable