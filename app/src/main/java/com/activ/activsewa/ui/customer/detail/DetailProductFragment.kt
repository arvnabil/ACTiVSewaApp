package com.activ.activsewa.ui.customer.detail

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.activ.activsewa.BuildConfig
import com.activ.activsewa.MainActivity
import com.activ.activsewa.R
import com.activ.activsewa.databinding.FragmentDetailProductBinding
import com.activ.activsewa.model.response.home.Data
import com.activ.activsewa.model.response.tambah_pengajuan.TambahPengajuanResponse
import com.activ.activsewa.ui.customer.ctransaction.CtransactionFragmentDirections
import com.activ.activsewa.ui.customer.detail.contract.TambahPengajuanContract
import com.activ.activsewa.ui.customer.detail.presenter.TambahPengajuanPresenter
import com.activ.activsewa.utils.Helpers.formatPrice
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar

class DetailProductFragment : Fragment(), TambahPengajuanContract.View{
    var bundle:Bundle ?= null
    private var _binding: FragmentDetailProductBinding? = null
    private val args: DetailProductFragmentArgs by navArgs()
    private lateinit var dataProduk: Data

    var progressDialog: Dialog?= null
    lateinit var presenter: TambahPengajuanPresenter
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailProductBinding.inflate(inflater, container, false)
        presenter = TambahPengajuanPresenter(this,requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        binding.btnBack.setOnClickListener{
            val intent = Intent(context, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        dataProduk = args.data!!
        initView(dataProduk)
    }
    @SuppressLint("SetTextI18n")
    private fun initView(data: Data?) {

        bundle = bundleOf("data" to data)

        var url = BuildConfig.IMAGE_URL
        var foto = data?.foto
        var path: String = url + foto
        Glide.with(requireContext())
            .load(path)
            .into(binding.ivProduct)

        var stok = data?.stok

        Log.d("Data Sukses: ",data.toString())
        binding.tvStatus.text = data?.status
        binding.tvBrand.text = data?.brand?.name
        binding.tvStok.text = "$stok Unit"
        val durasi = data?.durasi
        if(data?.sku == null){
            binding.tvSku.text = "SKU: Tidak ada"
        }else{
            val sku = data?.sku
            binding.tvSku.text = "SKU: $sku"
        }
        binding.tvProductName.text = data?.nama_produk
        binding.tvTag.text = data?.tag
        binding.tvDescription.text = data?.deskripsi
        binding.tvDurasi.text = "Durasi $durasi Hari"
        binding.tvPrice.formatPrice(data?.harga.toString())

        binding.btnTambahSewa.setOnClickListener {
            presenter.getTambahPengajuan(
                data?.id.toString(),
                it
            )
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onTambahPengajuanSuccess(tambahPengajuanResponse: TambahPengajuanResponse) {
        val directions = DetailProductFragmentDirections.actionFragmentDetailToNavigationFiling()
        view?.findNavController()?.navigate(directions)
        activity?.let { it1 ->
            Snackbar.make(it1.findViewById(android.R.id.content), "Tambah Sewa Berhasil", Snackbar.LENGTH_SHORT)
                .show()
        }
    }

    override fun onTambahPengajuanFailed(message: String) {
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