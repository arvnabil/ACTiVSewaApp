package com.activ.activsewa.ui.customer.detail.presenter

import android.content.Context
import com.activ.activsewa.network.HttpClient
import com.activ.activsewa.ui.customer.detail.contract.PembayaranContract
import com.activ.activsewa.utils.PreferenceHelper
import com.activ.activsewa.utils.PreferenceHelper.token
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class PembayaranPresenter (private val view: PembayaranContract.View, private val context: Context) : PembayaranContract.Presenter {

    private val mCompositeDisposable : CompositeDisposable?
    init {
        this.mCompositeDisposable = CompositeDisposable()
    }

    override fun submitPayment(
        id:String,
        tanggal_sewa: String,
        metode: String
    ) {
        view.showLoading()
        val pref = PreferenceHelper.defaultPreference(context)
        val prefToken = pref.token
        val token = "$prefToken"
        val disposable = HttpClient.getInstance().getApi()!!.payment(token,id,tanggal_sewa,metode)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view.dismissLoading()

                    if (it.meta?.status.equals("success",true)){
                        it.data?.let { it1 -> view.onPaymentSuccess(it1) }
                    } else {
                        it.meta?.message?.let { it1 -> view.onPaymentFailed(it1) }
                    }
                },
                {
                    view.dismissLoading()
                    view.onPaymentFailed(it.message.toString())
                }
            )
        mCompositeDisposable!!.add(disposable)
    }

    override fun subscribe() {

    }

    override fun unSubscribe() {
        mCompositeDisposable!!.clear()
    }

}