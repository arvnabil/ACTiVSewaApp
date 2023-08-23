package com.activ.activsewa.model.response.customer

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CustomerRepsonse(
    @Expose
    @SerializedName("data")
    val `data`: List<Data>
)