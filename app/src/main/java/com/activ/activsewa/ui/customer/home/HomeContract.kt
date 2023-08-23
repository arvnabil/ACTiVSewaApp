package com.activ.activsewa.ui.customer.home

import com.activ.activsewa.base.BasePresenter
import com.activ.activsewa.base.BaseView
import com.activ.activsewa.model.response.home.HomeResponse

interface HomeContract {

    interface View: BaseView {
        fun onHomeSuccess(homeResponse: HomeResponse)
        fun onHomeFailed(message:String)

    }

    interface Presenter : HomeContract, BasePresenter {
        fun getHome()
    }
}