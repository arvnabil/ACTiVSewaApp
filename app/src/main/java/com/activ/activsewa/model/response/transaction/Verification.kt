package com.activ.activsewa.model.response.transaction

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Verification(
    @Expose
    @SerializedName("cek_alamat")
    val cek_alamat: String,
    @Expose
    @SerializedName("cek_foto_ktp")
    val cek_foto_ktp: String,
    @Expose
    @SerializedName("cek_foto_npwp")
    val cek_foto_npwp: String,
    @Expose
    @SerializedName("cek_nama")
    val cek_nama: String,
    @Expose
    @SerializedName("cek_nik")
    val cek_nik: String,
    @Expose
    @SerializedName("cek_npwp")
    val cek_npwp: String,
    @Expose
    @SerializedName("cek_telp")
    val cek_telp: String,
    @Expose
    @SerializedName("created_at")
    val created_at: String,
    @Expose
    @SerializedName("id")
    val id: Int,
    @Expose
    @SerializedName("updated_at")
    val updated_at: String
) : Parcelable