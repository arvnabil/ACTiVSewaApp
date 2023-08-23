package com.activ.activsewa.ui.customer.ctransaction

import com.activ.activsewa.base.BasePresenter
import com.activ.activsewa.base.BaseView
import com.activ.activsewa.model.response.transaction.TransactionResponse

interface CTransactionContract {

    interface View: BaseView {
        fun onCTransactionSuccess(transactionResponse: TransactionResponse)
        fun onCTransactionFailed(message:String)

    }

    interface Presenter : CTransactionContract, BasePresenter {
        fun getCTransaction()
    }
}