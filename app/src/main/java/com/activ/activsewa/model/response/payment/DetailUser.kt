package com.activ.activsewa.model.response.payment

data class DetailUser(
    val alamat: String,
    val created_at: String,
    val foto_ktp: Any,
    val foto_npwp: Any,
    val id: Int,
    val jenkel: String,
    val nik: String,
    val npwp: String,
    val perusahaan: String,
    val profesi: String,
    val telp: String,
    val updated_at: String,
    val user_id: String
)