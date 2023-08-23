package com.activ.activsewa.ui.sales.transaction

import com.activ.activsewa.base.BasePresenter
import com.activ.activsewa.base.BaseView
import com.activ.activsewa.model.response.transaction.TransactionResponse

interface TransactionContract {

    interface View: BaseView {
        fun onTransactionSuccess(transactionResponse: TransactionResponse)
        fun onTransactionFailed(message:String)

    }

    interface Presenter : TransactionContract, BasePresenter {
        fun getTransaction()
    }
}