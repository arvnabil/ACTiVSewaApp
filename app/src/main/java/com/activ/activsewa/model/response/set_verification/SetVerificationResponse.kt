package com.activ.activsewa.model.response.set_verification

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SetVerificationResponse(
    @Expose
    @SerializedName("payment")
    val payment: Payment,
    @Expose
    @SerializedName("transaction")
    val transaction: Transaction,
    @Expose
    @SerializedName("transaction_detail")
    val transaction_detail: List<TransactionDetail>,
    @Expose
    @SerializedName("verification")
    val verification: Verification
)