package com.activ.activsewa.ui.adminsales.brand.contract

import com.activ.activsewa.base.BasePresenter
import com.activ.activsewa.base.BaseView
import com.activ.activsewa.model.response.tambahBrand.TambahBrandResponse

interface TambahBrandContract {

    interface View: BaseView {
        fun onTambahBrandSuccess(tambahBrandResponse: TambahBrandResponse)
        fun onTambahBrandFailed(message:String)

    }

    interface Presenter : TambahBrandContract, BasePresenter {
        fun addBrand(name:String)
    }
}