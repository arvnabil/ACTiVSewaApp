package com.activ.activsewa.ui.customer.detail.contract

import com.activ.activsewa.base.BasePresenter
import com.activ.activsewa.base.BaseView
import com.activ.activsewa.model.response.tambah_pengajuan.TambahPengajuanResponse

interface TambahPengajuanContract {

    interface View: BaseView {
        fun onTambahPengajuanSuccess(tambahPengajuanResponse: TambahPengajuanResponse)
        fun onTambahPengajuanFailed(message:String)

    }

    interface Presenter : TambahPengajuanContract, BasePresenter {
        fun getTambahPengajuan(product_id:String, view: android.view.View)
    }
}