package com.activ.activsewa.ui.customer.filing

import com.activ.activsewa.base.BasePresenter
import com.activ.activsewa.base.BaseView
import com.activ.activsewa.model.response.pengajuan.PengajuanResponse
import com.activ.activsewa.model.response.set_verification.SetVerificationResponse
import retrofit2.http.Field

interface FilingContract {

    interface View: BaseView {
        fun onFilingSuccess(pengajuanResponse: PengajuanResponse)
        fun onFilingFailed(message:String)
        fun onHapusProdukPengajuanSuccess(pengajuanResponse: PengajuanResponse)
        fun onHapusProdukPengajuanFailed(message:String)
        fun onSetFilingVerificationSuccess(setVerificationResponse: SetVerificationResponse)
        fun onSetFilingVerificationFailed(message:String)

    }

    interface Presenter : FilingContract, BasePresenter {
        fun getFilingContract()
        fun getHapusProdukPengajuan(product_id:String, view: android.view.View)

        fun subimtFilingVerification(nama_penyewa:String,
                                     nik:String,
                                     npwp:String,
                                     telp:String,
                                     alamat:String)
    }
}