package com.activ.activsewa.ui.adminsales.product

import android.net.Uri
import com.activ.activsewa.base.BasePresenter
import com.activ.activsewa.base.BaseView
import com.activ.activsewa.model.response.tambah_produk.TambahProdukResponse

interface TambahProdukContract {

    interface View: BaseView {
        fun onTambahProdukSuccess(tambahProdukResponse: TambahProdukResponse)
        fun onTambahProdukFailed(message:String)

    }

    interface Presenter : TambahProdukContract, BasePresenter {
        fun addProduk(nama_produk:String,deskripsi:String,durasi:String,tag:String,stok:String,harga:String,brand_id:Int, filePath: Uri)
    }
}