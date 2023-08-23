package com.activ.activsewa.model.response.payment

data class Verification(
    val cek_alamat: String,
    val cek_foto_ktp: String,
    val cek_foto_npwp: String,
    val cek_nama: String,
    val cek_nik: String,
    val cek_npwp: String,
    val cek_telp: String,
    val created_at: String,
    val id: Int,
    val updated_at: String
)