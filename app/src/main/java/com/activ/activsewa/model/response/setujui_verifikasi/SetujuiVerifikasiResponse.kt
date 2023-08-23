package com.activ.activsewa.model.response.setujui_verifikasi

data class SetujuiVerifikasiResponse(
    val alamat: String,
    val approved_by: Int,
    val catatan: Any,
    val code_reference: String,
    val created_at: String,
    val foto_ktp: Any,
    val foto_npwp: Any,
    val id: Int,
    val nama_penyewa: String,
    val nik: String,
    val npwp: String,
    val payment_id: String,
    val status: Any,
    val status_verification: String,
    val tanggal_sewa: String,
    val telp: String,
    val updated_at: String,
    val user_id: String,
    val verification: Verification,
    val verification_id: String
)