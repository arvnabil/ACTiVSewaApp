package com.activ.activsewa.ui.adminsales.transaction

import com.activ.activsewa.base.BasePresenter
import com.activ.activsewa.base.BaseView
import com.activ.activsewa.model.response.transaction.TransactionResponse
import com.activ.activsewa.ui.sales.transaction.TransactionContract

interface TransactionContract {

    interface View: BaseView {
        fun onTransactionSuccess(transactionResponse: TransactionResponse)
        fun onTransactionFailed(message:String)

    }

    interface Presenter : TransactionContract, BasePresenter {
        fun getTransaction()
    }
}