package com.activ.activsewa.model.response.set_verification

data class Transaction(
    val alamat: String,
    val approved_by: Any,
    val catatan: String,
    val code_reference: String,
    val created_at: String,
    val foto_ktp: Any,
    val foto_npwp: Any,
    val id: Int,
    val nama_penyewa: String,
    val nik: String,
    val npwp: String,
    val payment_id: Int,
    val status_verification: String,
    val tanggal_sewa: Any,
    val telp: String,
    val updated_at: String,
    val user_id: Int,
    val verification_id: Int
)