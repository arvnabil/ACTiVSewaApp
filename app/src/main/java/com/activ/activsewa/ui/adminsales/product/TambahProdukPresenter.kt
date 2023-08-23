package com.activ.activsewa.ui.adminsales.product

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.View
import androidx.core.net.toFile
import com.activ.activsewa.network.HttpClient
import com.activ.activsewa.utils.PreferenceHelper
import com.activ.activsewa.utils.PreferenceHelper.token
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.http.Field
import java.io.File

class TambahProdukPresenter (private val view: TambahProdukContract.View, private val context: Context) : TambahProdukContract.Presenter {

    private val mCompositeDisposable : CompositeDisposable?
    init {
        this.mCompositeDisposable = CompositeDisposable()
    }

    override fun addProduk(
        nama_produk: String,
        deskripsi: String,
        durasi: String,
        tag: String,
        stok: String,
        harga: String,
        brand_id: Int,
        filePath: Uri
    ) {
        view.showLoading()
        val pref = PreferenceHelper.defaultPreference(context)
        val prefToken = pref.token
        val token = "$prefToken"
        var profileImageFile = File(filePath.path)
        val profileImageRequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), profileImageFile)
        val profileImageParms = MultipartBody.Part.createFormData("file", profileImageFile.name, profileImageRequestBody)
        val multipartBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("nama_produk", nama_produk)
            .addFormDataPart("deskripsi", deskripsi)
            .addFormDataPart("durasi", durasi)
            .addFormDataPart("tag", tag)
            .addFormDataPart("stok", stok)
            .addFormDataPart("harga", harga)
            .addFormDataPart("brand_id", brand_id.toString())
            .addFormDataPart("foto", profileImageFile.name, filePath.toFile().asRequestBody())
            .build()
        val disposable = HttpClient.getInstance().getApi()!!.tambahProduk(token,multipartBody)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view.dismissLoading()

                    if (it.meta?.status.equals("success",true)){
                        it.data?.let { it1 -> view.onTambahProdukSuccess(it1) }
                    } else {
                        it.meta?.status?.let { it1 -> view.onTambahProdukFailed(it1) }
                    }
                },
                {
                    Log.d("Status Meta", it.localizedMessage)
                    view.dismissLoading()
                    view.onTambahProdukFailed(it.message.toString())
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