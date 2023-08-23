package com.activ.activsewa.ui.auth.daftar

import android.net.Uri
import com.activ.activsewa.base.BasePresenter
import com.activ.activsewa.base.BaseView
import com.activ.activsewa.model.request.RegisterRequest
import com.activ.activsewa.model.response.daftar.DaftarResponse

interface DaftarContract {

    interface View: BaseView {
        fun onRegisterSuccess(daftarResponse: DaftarResponse, view:android.view.View)
        fun onRegisterFailed(message:String)

    }

    interface Presenter : DaftarContract, BasePresenter {
        fun submitRegister(registerRequest: RegisterRequest, view:android.view.View)
    }
}