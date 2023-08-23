package com.activ.activsewa.ui.customer.filing

import android.content.Context
import android.view.View
import com.activ.activsewa.network.HttpClient
import com.activ.activsewa.utils.PreferenceHelper
import com.activ.activsewa.utils.PreferenceHelper.token
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class FilingPresenter (private val view:FilingContract.View, private val context: Context) : FilingContract.Presenter {

    private val mCompositeDisposable : CompositeDisposable?
    init {
        this.mCompositeDisposable = CompositeDisposable()
    }

    override fun getFilingContract() {
        view.showLoading()
        val pref = PreferenceHelper.defaultPreference(context)
        val prefToken = pref.token
        val token = "$prefToken"
        val disposable = HttpClient.getInstance().getApi()!!.filing(token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view.dismissLoading()

                    if (it.meta?.status.equals("success",true)){
                        it.data?.let { it1 -> view.onFilingSuccess(it1) }
                    } else {
                        it.meta?.message?.let { it1 -> view.onFilingFailed(it1) }
                    }
                },
                {
                    view.dismissLoading()
                    view.onFilingFailed(it.message.toString())
                }
            )
        mCompositeDisposable!!.add(disposable)
    }

    override fun getHapusProdukPengajuan(product_id: String, viewParms: View) {
        view.showLoading()
        val pref = PreferenceHelper.defaultPreference(context)
        val prefToken = pref.token
        val token = "$prefToken"
        val disposable = HttpClient.getInstance().getApi()!!.filingDelete(token, product_id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view.dismissLoading()

                    if (it.meta?.status.equals("success",true)){
                        it.data?.let { it1 -> view.onHapusProdukPengajuanSuccess(it1) }
                    } else {
                        it.meta?.message?.let { it1 -> view.onHapusProdukPengajuanFailed(it1) }
                    }
                },
                {
                    view.dismissLoading()
                    view.onHapusProdukPengajuanFailed(it.message.toString())
                }
            )
        mCompositeDisposable!!.add(disposable)
    }

    override fun subimtFilingVerification(
        nama_penyewa: String,
        nik: String,
        npwp: String,
        telp: String,
        alamat: String,
    ) {
        view.showLoading()
        val pref = PreferenceHelper.defaultPreference(context)
        val prefToken = pref.token
        val token = "$prefToken"
        val disposable = HttpClient.getInstance().getApi()!!.filingSetVerification(token, nama_penyewa,nik,npwp, telp, alamat)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view.dismissLoading()

                    if (it.meta?.status.equals("success",true)){
                        it.data?.let { it1 -> view.onSetFilingVerificationSuccess(it1) }
                    } else {
                        it.meta?.message?.let { it1 -> view.onSetFilingVerificationFailed(it1) }
                    }
                },
                {
                    view.dismissLoading()
                    view.onSetFilingVerificationFailed(it.message.toString())
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