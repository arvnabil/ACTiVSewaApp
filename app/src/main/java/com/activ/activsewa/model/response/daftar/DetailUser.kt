package com.activ.activsewa.model.response.daftar

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DetailUser(
    @Expose
    @SerializedName("created_at")
    val created_at: String,
    @Expose
    @SerializedName("id")
    val id: Int,
    @Expose
    @SerializedName("telp")
    val telp: String,
    @Expose
    @SerializedName("updated_at")
    val updated_at: String,
    @Expose
    @SerializedName("user_id")
    val user_id: Int,
)