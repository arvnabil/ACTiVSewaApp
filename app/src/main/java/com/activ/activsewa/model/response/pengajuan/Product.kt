package com.activ.activsewa.model.response.pengajuan

import android.os.Parcelable
import com.activ.activsewa.model.response.home.Brand
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    @Expose
    @SerializedName("brand")
    val brand: Brand?,
    @Expose
    @SerializedName("brand_id")
    val brand_id: Int?,
    @Expose
    @SerializedName("created_at")
    val created_at: String?,
    @Expose
    @SerializedName("deleted_at")
    val deleted_at: String?,
    @Expose
    @SerializedName("deskripsi")
    val deskripsi: String?,
    @Expose
    @SerializedName("durasi")
    val durasi: String?,
    @Expose
    @SerializedName("foto")
    val foto: String?,
    @Expose
    @SerializedName("foto_1")
    val foto_1: String?,
    @Expose
    @SerializedName("foto_2")
    val foto_2: String?,
    @Expose
    @SerializedName("status")
    val status: String?,
    @Expose
    @SerializedName("harga")
    val harga: String?,
    @Expose
    @SerializedName("id")
    val id: Int?,
    @Expose
    @SerializedName("nama_produk")
    val nama_produk: String?,
    @Expose
    @SerializedName("sku")
    val sku: String?,
    @Expose
    @SerializedName("stok")
    val stok: String?,
    @Expose
    @SerializedName("tag")
    val tag: String?,
    @Expose
    @SerializedName("updated_at")
    val updated_at: String?,

    ): Parcelable