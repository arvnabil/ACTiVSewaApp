package com.activ.activsewa.model.response.daftar

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DaftarResponse(
    @Expose
    @SerializedName("access_token")
    val access_token: String,
    @Expose
    @SerializedName("token_type")
    val token_type: String,
    @Expose
    @SerializedName("user")
    val user: User,
    @Expose
    @SerializedName("detail_user")
    val detail_user: DetailUser
)