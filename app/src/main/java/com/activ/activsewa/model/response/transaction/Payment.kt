package com.activ.activsewa.model.response.transaction

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Payment(
    @Expose
    @SerializedName("created_at")
    val created_at: String?,
    @Expose
    @SerializedName("id")
    val id: Int?,
    @Expose
    @SerializedName("metode")
    val metode: String?,
    @Expose
    @SerializedName("payload")
    val payload: String?,
    @Expose
    @SerializedName("payment_status")
    val payment_status: String?,
    @Expose
    @SerializedName("total_harga")
    val total_harga: Int?,
    @Expose
    @SerializedName("transaction_reference")
    val transaction_reference: String?,
    @Expose
    @SerializedName("updated_at")
    val updated_at: String?,
    @Expose
    @SerializedName("url")
    val url: String?
):Parcelable