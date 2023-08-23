package com.activ.activsewa.ui.sales.verification_data

import com.activ.activsewa.base.BasePresenter
import com.activ.activsewa.base.BaseView
import com.activ.activsewa.model.response.setujui_verifikasi.SetujuiVerifikasiResponse
import com.activ.activsewa.model.response.tolak_verifikasi.TolakVerifikasiResponse

interface VerificationDataContract {

    interface View: BaseView {
        fun onVerificationApproveSuccess(setujuiVerifikasiResponse: SetujuiVerifikasiResponse)
        fun onVerificationApproveFailed(message:String)
        fun onVerificationRejectSuccess(tolakVerifikasiResponse: TolakVerifikasiResponse)
        fun onVerificationRejectFailed(message:String)
    }

    interface Presenter : VerificationDataContract, BasePresenter {
        fun submitTolak(id:String,cek_nama:String,cek_nik:String,cek_npwp:String,cek_telp:String,cek_alamat:String,cek_foto_ktp:String,cek_foto_npwp:String)
        fun submitSetujui(id:String,cek_nama:String,cek_nik:String,cek_npwp:String,cek_telp:String,cek_alamat:String,cek_foto_ktp:String,cek_foto_npwp:String)
    }
}