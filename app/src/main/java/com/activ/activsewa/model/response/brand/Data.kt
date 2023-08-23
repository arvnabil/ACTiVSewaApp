package com.activ.activsewa.model.response.brand

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Data(
    @Expose
    @SerializedName("created_at")
    val created_at: String,
    @Expose
    @SerializedName("id")
    val id: Int,
    @Expose
    @SerializedName("logo")
    val logo: String?,
    @Expose
    @SerializedName("name")
    val name: String,
    @Expose
    @SerializedName("updated_at")
    val updated_at: String
): Parcelable