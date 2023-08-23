package com.activ.activsewa.ui.sales.detail

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
import com.activ.activsewa.databinding.FragmentDetailPenyewaBinding
import com.activ.activsewa.model.response.transaction.Data
import com.activ.activsewa.ui.customer.detail.DetailPenyewaFragmentArgs
import com.activ.activsewa.ui.sales.DashboardSalesActivity

class DetailPenyewaFragment : Fragment() {
    var bundle:Bundle ?= null
    private var _binding: FragmentDetailPenyewaBinding? = null
    private val args: DetailPenyewaFragmentArgs by navArgs()

    private val binding get() = _binding!!
    private lateinit var dataTransaction: Data

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailPenyewaBinding.inflate(inflater, container, false)
        binding.includeToolbar.btnBack.setOnClickListener{
            findNavController().popBackStack()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        dataTransaction = args.data!!
        initView(dataTransaction)
    }

    @SuppressLint("SetTextI18n")
    private fun initView(data: Data) {

        bundle = bundleOf("data" to data)
        Log.d("Data Sukses: ",data.toString())
        binding.editNama.setText(data.nama_penyewa)
        binding.etNik.setText(data.nik)
        binding.etNPWP.setText(data.npwp)
        binding.etTelp.setText(data.telp)
        binding.etAlamat.setText(data.alamat)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}