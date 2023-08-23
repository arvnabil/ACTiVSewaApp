package com.activ.activsewa.ui.adminsales.customers

import com.activ.activsewa.base.BasePresenter
import com.activ.activsewa.base.BaseView
import com.activ.activsewa.model.response.customer.CustomerRepsonse
import com.activ.activsewa.ui.sales.customers.CustomerContract

interface CustomerContract {

    interface View: BaseView {
        fun onCustomerSuccess(customerRepsonse: CustomerRepsonse)
        fun onCustomerFailed(message:String)

    }

    interface Presenter : CustomerContract, BasePresenter {
        fun getCustomer()
    }
}