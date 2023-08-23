package com.activ.activsewa.ui.adminsales.product

import com.activ.activsewa.base.BasePresenter
import com.activ.activsewa.base.BaseView
import com.activ.activsewa.model.response.home.HomeResponse

interface ProductContract {

    interface View: BaseView {
        fun onProductSuccess(homeResponse: HomeResponse)
        fun onProductFailed(message:String)
    }

    interface Presenter : ProductContract, BasePresenter {
        fun getProduct()
    }
}