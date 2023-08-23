package com.activ.activsewa.model.response.tambahBrand

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TambahBrandResponse(
    @Expose
    @SerializedName("data")
    val `data`: Data
)