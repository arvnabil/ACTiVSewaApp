package com.activ.activsewa.ui.customer.detail.contract

import com.activ.activsewa.base.BasePresenter
import com.activ.activsewa.base.BaseView
import com.activ.activsewa.model.response.payment.PaymentResponse

interface PembayaranContract {

    interface View: BaseView {
        fun onPaymentSuccess(paymentResponse: PaymentResponse)
        fun onPaymentFailed(message:String)
    }
    interface Presenter : PembayaranContract, BasePresenter {
        fun submitPayment(id:String,tanggal_sewa:String,metode:String)
    }
}