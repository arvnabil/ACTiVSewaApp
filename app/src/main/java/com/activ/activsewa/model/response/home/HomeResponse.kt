package com.activ.activsewa.model.response.home

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class HomeResponse(
    @Expose
    @SerializedName("current_page")
    val current_page: Int,
    @Expose
    @SerializedName("data")
    val `data`: List<Data>,
    @Expose
    @SerializedName("first_page_url")
    val first_page_url: String,
    @Expose
    @SerializedName("from")
    val from: Int,
    @Expose
    @SerializedName("last_page")
    val last_page: Int,
    @Expose
    @SerializedName("last_page_url")
    val last_page_url: String,
    @Expose
    @SerializedName("links")
    val links: List<Link>,
    @Expose
    @SerializedName("next_page_url")
    val next_page_url: Any,
    @Expose
    @SerializedName("path")
    val path: String,
    @Expose
    @SerializedName("per_page")
    val per_page: Int,
    @Expose
    @SerializedName("prev_page_url")
    val prev_page_url: Any,
    @Expose
    @SerializedName("to")
    val to: Int,
    @Expose
    @SerializedName("total")
    val total: Int

)