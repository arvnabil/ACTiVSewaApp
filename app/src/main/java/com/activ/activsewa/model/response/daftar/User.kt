package com.activ.activsewa.model.response.daftar

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class User(
    @Expose
    @SerializedName("active")
    val active: Int,
    @Expose
    @SerializedName("avatar")
    val avatar: Any,
    @Expose
    @SerializedName("created_at")
    val created_at: String,
    @Expose
    @SerializedName("deleted_at")
    val deleted_at: Any,
    @Expose
    @SerializedName("email")
    val email: String,
    @Expose
    @SerializedName("email_verified_at")
    val email_verified_at: Any,
    @Expose
    @SerializedName("gender")
    val gender: Any,
    @Expose
    @SerializedName("id")
    val id: Int,
    @Expose
    @SerializedName("name")
    val name: String,
    @Expose
    @SerializedName("roles")
    val roles: String,
    @Expose
    @SerializedName("two_factor_confirmed_at")
    val two_factor_confirmed_at: Any,
    @Expose
    @SerializedName("updated_at")
    val updated_at: String,
    @Expose
    @SerializedName("username")
    val username: String,
)