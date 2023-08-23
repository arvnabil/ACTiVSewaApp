package com.activ.activsewa.model.response.set_verification

data class Payment(
    val created_at: String,
    val id: Int,
    val metode: Any,
    val payload: Any,
    val payment_status: String,
    val total_harga: Int,
    val transaction_reference: String,
    val updated_at: String,
    val url: Any
)