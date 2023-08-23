package com.activ.activsewa.model.response.transaction

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class TransactionDetail(
    @Expose
    @SerializedName("created_at")
    val created_at: String,
    @Expose
    @SerializedName("id")
    val id: Int,
    @Expose
    @SerializedName("product")
    val product: Product,
    @Expose
    @SerializedName("product_id")
    val product_id: Int,
    @Expose
    @SerializedName("transaction_id")
    val transaction_id: Int,
    @Expose
    @SerializedName("updated_at")
    val updated_at: String
): Parcelable