package com.activ.activsewa.ui.auth.masuk
import com.activ.activsewa.base.BasePresenter
import com.activ.activsewa.base.BaseView
import com.activ.activsewa.model.response.login.LoginResponse
interface MasukContract {
    interface View: BaseView {
        fun onLoginSuccess(loginResponse: LoginResponse)
        fun onLoginFailed(message:String)

    }
    interface Presenter : MasukContract, BasePresenter {
        fun subimtLogin(email:String, password:String)
    }
}