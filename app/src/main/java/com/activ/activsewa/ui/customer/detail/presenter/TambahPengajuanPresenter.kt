package com.activ.activsewa.ui.customer.detail.presenter

import android.content.Context
import android.view.View
import com.activ.activsewa.network.HttpClient
import com.activ.activsewa.ui.customer.detail.contract.TambahPengajuanContract
import com.activ.activsewa.utils.PreferenceHelper
import com.activ.activsewa.utils.PreferenceHelper.token
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class TambahPengajuanPresenter (private val view: TambahPengajuanContract.View, private val context: Context) : TambahPengajuanContract.Presenter {

    private val mCompositeDisposable : CompositeDisposable?
    init {
        this.mCompositeDisposable = CompositeDisposable()
    }

    override fun getTambahPengajuan(product_id:String, viewParms: View) {
        view.showLoading()
        val pref = PreferenceHelper.defaultPreference(context)
        val prefToken = pref.token
        val token = "$prefToken"
        val disposable = HttpClient.getInstance().getApi()!!.filingAdd(token, product_id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view.dismissLoading()

                    if (it.meta?.status.equals("success",true)){
                        it.data?.let { it1 -> view.onTambahPengajuanSuccess(it1) }
                    } else {
                        it.meta?.message?.let { it1 -> view.onTambahPengajuanFailed(it1) }
                    }
                },
                {
                    view.dismissLoading()
                    view.onTambahPengajuanFailed(it.message.toString())
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