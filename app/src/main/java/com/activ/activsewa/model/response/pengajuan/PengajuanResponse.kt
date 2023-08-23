package com.activ.activsewa.model.response.pengajuan

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PengajuanResponse(
    @Expose
    @SerializedName("data")
    val `data`: List<Data>,
    @Expose
    @SerializedName("grand_total")
    val grand_total: Int?
)