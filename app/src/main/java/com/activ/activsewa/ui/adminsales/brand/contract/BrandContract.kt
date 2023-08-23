package com.activ.activsewa.ui.adminsales.brand.contract

import com.activ.activsewa.base.BasePresenter
import com.activ.activsewa.base.BaseView
import com.activ.activsewa.model.response.brand.BrandResponse

interface BrandContract {

    interface View: BaseView {
        fun onBrandSuccess(brandResponse: BrandResponse)
        fun onBrandFailed(message:String)

    }

    interface Presenter : BrandContract, BasePresenter {
        fun getBrand()
    }
}