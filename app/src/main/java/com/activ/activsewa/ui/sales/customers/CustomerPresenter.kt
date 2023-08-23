package com.activ.activsewa.ui.sales.customers

import android.content.Context
import com.activ.activsewa.network.HttpClient
import com.activ.activsewa.utils.PreferenceHelper
import com.activ.activsewa.utils.PreferenceHelper.token
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CustomerPresenter (private val view: CustomerContract.View, private val context: Context) : CustomerContract.Presenter {

    private val mCompositeDisposable : CompositeDisposable?
    init {
        this.mCompositeDisposable = CompositeDisposable()
    }

    override fun getCustomer() {
        view.showLoading()
        val pref = PreferenceHelper.defaultPreference(context)
        val prefToken = pref.token
        val token = "$prefToken"
        val disposable = HttpClient.getInstance().getApi()!!.allCustomer(token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view.dismissLoading()

                    if (it.meta?.status.equals("success",true)){
                        it.data?.let { it1 -> view.onCustomerSuccess(it1) }
                    } else {
                        it.meta?.message?.let { it1 -> view.onCustomerFailed(it1) }
                    }
                },
                {
                    view.dismissLoading()
                    view.onCustomerFailed(it.message.toString())
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