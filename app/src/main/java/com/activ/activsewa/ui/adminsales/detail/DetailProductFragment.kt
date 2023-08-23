package com.activ.activsewa.ui.adminsales.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.activ.activsewa.BuildConfig
import com.activ.activsewa.MainActivity
import com.activ.activsewa.databinding.FragmentDetailProductBinding
import com.activ.activsewa.model.response.home.Data
import com.activ.activsewa.utils.Helpers.formatPrice
import com.bumptech.glide.Glide

class DetailProductFragment : Fragment(){
    var bundle:Bundle ?= null
    private var _binding: FragmentDetailProductBinding? = null
    private val args: DetailProductFragmentArgs by navArgs()
    private lateinit var dataProduk: Data

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        binding.btnBack.setOnClickListener{
            findNavController().popBackStack()
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

        binding.btnTambahSewa.visibility = View.GONE
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}