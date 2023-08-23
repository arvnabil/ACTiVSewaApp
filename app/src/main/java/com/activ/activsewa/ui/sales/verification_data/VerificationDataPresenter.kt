package com.activ.activsewa.ui.sales.verification_data

import android.content.Context
import com.activ.activsewa.network.HttpClient
import com.activ.activsewa.utils.PreferenceHelper
import com.activ.activsewa.utils.PreferenceHelper.token
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class VerificationDataPresenter (private val view:VerificationDataContract.View, private val context: Context) : VerificationDataContract.Presenter {

    private val mCompositeDisposable : CompositeDisposable?
    init {
        this.mCompositeDisposable = CompositeDisposable()
    }

    override fun submitTolak(
        id: String,
        cek_nama: String,
        cek_nik: String,
        cek_npwp: String,
        cek_telp: String,
        cek_alamat: String,
        cek_foto_ktp: String,
        cek_foto_npwp: String
    ) {
        view.showLoading()
        val pref = PreferenceHelper.defaultPreference(context)
        val prefToken = pref.token
        val token = "$prefToken"
        val disposable = HttpClient.getInstance().getApi()!!.tolakVerifikasi(token,id,cek_nama, cek_nik, cek_npwp, cek_telp, cek_alamat, cek_foto_ktp, cek_foto_npwp)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view.dismissLoading()

                    if (it.meta?.status.equals("success",true)){
                        it.data?.let { it1 -> view.onVerificationRejectSuccess(it1) }
                    } else {
                        it.meta?.message?.let { it1 -> view.onVerificationRejectFailed(it1) }
                    }
                },
                {
                    view.dismissLoading()
                    view.onVerificationRejectFailed(it.message.toString())
                }
            )
        mCompositeDisposable!!.add(disposable)
    }

    override fun submitSetujui(
        id: String,
        cek_nama: String,
        cek_nik: String,
        cek_npwp: String,
        cek_telp: String,
        cek_alamat: String,
        cek_foto_ktp: String,
        cek_foto_npwp: String
    ) {
        view.showLoading()
        val pref = PreferenceHelper.defaultPreference(context)
        val prefToken = pref.token
        val token = "$prefToken"
        val disposable = HttpClient.getInstance().getApi()!!.setujuiVerifikasi(token,id,cek_nama, cek_nik, cek_npwp, cek_telp, cek_alamat, cek_foto_ktp, cek_foto_npwp)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view.dismissLoading()

                    if (it.meta?.status.equals("success",true)){
                        it.data?.let { it1 -> view.onVerificationApproveSuccess(it1) }
                    } else {
                        it.meta?.message?.let { it1 -> view.onVerificationApproveFailed(it1) }
                    }
                },
                {
                    view.dismissLoading()
                    view.onVerificationApproveFailed(it.message.toString())
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