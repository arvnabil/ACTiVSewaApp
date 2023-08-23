package com.activ.activsewa.model.response.pengajuan

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Data(
    @Expose
    @SerializedName("created_at")
    val created_at: String?,
    @Expose
    @SerializedName("id")
    val id: Int?,
    @Expose
    @SerializedName("product")
    val product: Product?,
    @Expose
    @SerializedName("product_id")
    val product_id: String?,
    @Expose
    @SerializedName("total")
    val total: String?,
    @Expose
    @SerializedName("updated_at")
    val updated_at: String?,
    @Expose
    @SerializedName("user")
    val user: User?,
    @Expose
    @SerializedName("user_id")
    val user_id: String?
): Parcelable