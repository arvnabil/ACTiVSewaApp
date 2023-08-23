package com.activ.activsewa.ui.adminsales.product

import android.content.Context
import android.util.Log
import com.activ.activsewa.network.HttpClient
import com.activ.activsewa.utils.PreferenceHelper
import com.activ.activsewa.utils.PreferenceHelper.token
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlin.math.log

class ProductPresenter (private val view:ProductContract.View, private val context: Context) : ProductContract.Presenter {

    private val mCompositeDisposable : CompositeDisposable?
    init {
        this.mCompositeDisposable = CompositeDisposable()
    }

    override fun getProduct() {
        view.showLoading()
        val pref = PreferenceHelper.defaultPreference(context)
        val prefToken = pref.token
        val token = "$prefToken"
        val disposable = HttpClient.getInstance().getApi()!!.home(token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view.dismissLoading()

                    if (it.meta?.status.equals("success",true)){
                        it.data?.let { it1 -> view.onProductSuccess(it1) }
                    } else {
                        it.meta?.message?.let { it1 -> view.onProductFailed(it1) }
                    }
                },
                {
                    view.dismissLoading()
                    view.onProductFailed(it.message.toString())
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