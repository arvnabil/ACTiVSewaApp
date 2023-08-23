package com.activ.activsewa.model.response.set_verification

data class TransactionDetail(
    val created_at: String,
    val id: Int,
    val product_id: String,
    val transaction_id: Int,
    val updated_at: String
)