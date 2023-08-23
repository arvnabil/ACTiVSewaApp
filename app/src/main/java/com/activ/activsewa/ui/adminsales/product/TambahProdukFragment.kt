package com.activ.activsewa.ui.adminsales.product

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.activ.activsewa.R
import com.activ.activsewa.databinding.FragmentTambahProdukBinding
import com.activ.activsewa.model.response.tambah_produk.TambahProdukResponse
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.snackbar.Snackbar

class TambahProdukFragment : Fragment() , TambahProdukContract.View {
    lateinit var presenter: TambahProdukPresenter
    var progressDialog: Dialog?=null
    var filePath: Uri?= null
    private lateinit var binding: FragmentTambahProdukBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding= FragmentTambahProdukBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        binding.includeToolbar.btnBack.setOnClickListener{
            findNavController().popBackStack()
        }
        binding.includeToolbar.toolbar.title = "Tambah Produk"
        presenter = TambahProdukPresenter(this, requireContext())
        initView()
        binding.btnUpload.setOnClickListener{
            ImagePicker.with(this)
                .galleryOnly()
                .start()
        }

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_OK -> {
                filePath = data?.data
                    binding.ivProduct.setImageURI(filePath)
                binding.btnUpload.text = filePath.toString()
                binding.btnTambahProduct.setOnClickListener {
                    val namaProduk = binding.etNama.text.toString()
                    val deskripsi = binding.etDeskripsi.text.toString()
                    val durasi = binding.etDurasi.text.toString()
                    val tag = binding.etTag.text.toString()
                    val stok = binding.etStok.text.toString()
                    val harga = binding.etHarga.text.toString()
                    val brand = "2"
                    if (namaProduk.isNullOrEmpty()) {
                        binding.etNama.error = "Silahkan masukkan Nama Poduk"
                        binding.etNama.requestFocus()
                    }else if (deskripsi.isNullOrEmpty()) {
                        binding.etDeskripsi.error = "Silahkan masukkan Deskripsi Produk"
                        binding.etDeskripsi.requestFocus()
                    }else if (durasi.isNullOrEmpty()) {
                        binding.etDurasi.error = "Silahkan masukkan Durasi Produk"
                        binding.etDurasi.requestFocus()
                    }else if (tag.isNullOrEmpty()) {
                        binding.etTag.error = "Silahkan masukkan Durasi Produk"
                        binding.etTag.requestFocus()
                    }else if (stok.isNullOrEmpty()) {
                        binding.etStok.error = "Silahkan masukkan Durasi Produk"
                        binding.etStok.requestFocus()
                    }else if (harga.isNullOrEmpty()) {
                        binding.etDurasi.error = "Silahkan masukkan Durasi Produk"
                        binding.etDurasi.requestFocus()
                    }else if (brand.isNullOrEmpty()) {
                        binding.etBrand.error = "Silahkan masukkan Durasi Produk"
                        binding.etBrand.requestFocus()
                    }else{
                        val Ibrand = Integer.parseInt(brand)
                            presenter.addProduk(namaProduk,deskripsi,durasi,tag,stok,harga,Ibrand,filePath!!)
                    }
                }

            }
            ImagePicker.RESULT_ERROR -> {
                Toast.makeText(context, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(context, "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initView() {
        progressDialog = Dialog(requireContext())
        val dialogLayout = layoutInflater.inflate(R.layout.dialog_loader, null)

        progressDialog?.let {
            it.setContentView(dialogLayout)
            it.setCancelable(false)
            it.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
    }

    override fun onTambahProdukSuccess(tambahProdukResponse: TambahProdukResponse) {
        findNavController().popBackStack()
        activity?.let { it1 ->
            Snackbar.make(it1.findViewById(android.R.id.content), "Berhasil Tambah Produk!", Snackbar.LENGTH_SHORT)
                .show()
        }
    }

    @SuppressLint("LongLogTag")
    override fun onTambahProdukFailed(message: String) {
        Log.d("message Tambah Produk Failed", message)
        activity?.let { it1 ->
            Snackbar.make(it1.findViewById(android.R.id.content), "$message", Snackbar.LENGTH_SHORT)
                .show()
        }
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }

}