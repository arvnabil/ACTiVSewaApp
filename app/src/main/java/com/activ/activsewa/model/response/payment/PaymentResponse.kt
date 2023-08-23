package com.activ.activsewa.model.response.payment

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PaymentResponse(
    @Expose
    @SerializedName("alamat")
    val alamat: String?,
    @Expose
    @SerializedName("approved_by")
    val approved_by: String?,
    @Expose
    @SerializedName("catatan")
    val catatan: String?,
    @Expose
    @SerializedName("code_reference")
    val code_reference: String?,
    @Expose
    @SerializedName("created_at")
    val created_at: String?,
    @Expose
    @SerializedName("foto_ktp")
    val foto_ktp: String?,
    @Expose
    @SerializedName("foto_npwp")
    val foto_npwp: String?,
    @Expose
    @SerializedName("id")
    val id: Int?,
    @Expose
    @SerializedName("nama_penyewa")
    val nama_penyewa: String?,
    @Expose
    @SerializedName("nik")
    val nik: String?,
    @Expose
    @SerializedName("npwp")
    val npwp: String?,
    @Expose
    @SerializedName("payment")
    val payment: Payment,
    @Expose
    @SerializedName("payment_id")
    val payment_id: String?,
    @Expose
    @SerializedName("status")
    val status: String?,
    @Expose
    @SerializedName("status_verification")
    val status_verification: String?,
    @Expose
    @SerializedName("tanggal_sewa")
    val tanggal_sewa: String?,
    @Expose
    @SerializedName("telp")
    val telp: String?,
    @Expose
    @SerializedName("updated_at")
    val updated_at: String?,
    @Expose
    @SerializedName("user")
    val user: User?,
    @Expose
    @SerializedName("user_id")
    val user_id: String?,
    @Expose
    @SerializedName("verification")
    val verification: Verification?,
    @Expose
    @SerializedName("verification_id")
    val verification_id: String?
)