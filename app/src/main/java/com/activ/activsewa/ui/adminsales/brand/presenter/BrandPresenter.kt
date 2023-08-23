package com.activ.activsewa.ui.adminsales.brand.presenter

import android.content.Context
import com.activ.activsewa.network.HttpClient
import com.activ.activsewa.ui.adminsales.brand.contract.BrandContract
import com.activ.activsewa.utils.PreferenceHelper
import com.activ.activsewa.utils.PreferenceHelper.token
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class BrandPresenter (private val view: BrandContract.View, private val context: Context) : BrandContract.Presenter {

    private val mCompositeDisposable : CompositeDisposable?
    init {
        this.mCompositeDisposable = CompositeDisposable()
    }


    override fun getBrand() {
        view.showLoading()
        val pref = PreferenceHelper.defaultPreference(context)
        val prefToken = pref.token
        val token = "$prefToken"
        val disposable = HttpClient.getInstance().getApi()!!.brand(token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view.dismissLoading()

                    if (it.meta?.status.equals("success",true)){
                        it.data?.let { it1 -> view.onBrandSuccess(it1) }
                    } else {
                        it.meta?.message?.let { it1 -> view.onBrandFailed(it1) }
                    }
                },
                {
                    view.dismissLoading()
                    view.onBrandFailed(it.message.toString())
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