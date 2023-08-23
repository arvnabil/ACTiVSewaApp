package com.activ.activsewa.model.response.tambah_pengajuan

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TambahPengajuanResponse(
    @Expose
    @SerializedName("created_at")
    val created_at: String,
    @Expose
    @SerializedName("id")
    val id: Int,
    @Expose
    @SerializedName("product_id")
    val product_id: String,
    @Expose
    @SerializedName("total")
    val total: String,
    @Expose
    @SerializedName("updated_at")
    val updated_at: String,
    @Expose
    @SerializedName("user_id")
    val user_id: Int
)