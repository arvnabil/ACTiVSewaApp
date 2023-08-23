package com.activ.activsewa.ui.customer.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.Browser
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.activ.activsewa.MainActivity
import com.activ.activsewa.R
import com.activ.activsewa.adapter.TransactionDetailProductAdapter
import com.activ.activsewa.databinding.FragmentDetailTransactionBinding
import com.activ.activsewa.model.response.transaction.Data
import com.activ.activsewa.model.response.transaction.TransactionDetail
import com.activ.activsewa.ui.customer.ctransaction.CtransactionFragmentDirections
import com.activ.activsewa.ui.sales.DashboardSalesActivity
import com.activ.activsewa.utils.Helpers.formatPrices
import com.activ.activsewa.utils.PreferenceHelper
import com.activ.activsewa.utils.PreferenceHelper.name
import com.activ.activsewa.utils.PreferenceHelper.roles
import com.activ.activsewa.utils.PreferenceHelper.token
import com.google.android.material.snackbar.Snackbar


class DetailTransactionFragment : Fragment(), TransactionDetailProductAdapter.ItemAdapterCallback {
    var bundle:Bundle ?= null
    private var _binding: FragmentDetailTransactionBinding? = null
    private val args: DetailTransactionFragmentArgs by navArgs()

    private val binding get() = _binding!!
    private lateinit var dataTransaction: Data

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailTransactionBinding.inflate(inflater, container, false)
        binding.includeToolbar.btnBack.setOnClickListener{
                val intent = Intent(context, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)

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

        if(data.payment?.url != null){
            binding.btnLihatDataPembayaran.visibility = View.GONE
        }

        var adapterProductTransaction = TransactionDetailProductAdapter(data.transaction_detail,this)
        var layoutManagerTransaction: RecyclerView.LayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvProductList.layoutManager = layoutManagerTransaction
        binding.rvProductList.adapter = adapterProductTransaction

        Log.d("Data Sukses: ",data.toString())
        binding.tvStatusVerifikasi.text = data.status_verification
        binding.tvStatusPembayaran.text = data.payment?.payment_status
        binding.includeToolbar.toolbar.title = data.code_reference

        if(data.tanggal_sewa != null){
            binding.tvTglSewa.text = data.tanggal_sewa
        }else{
            binding.tvTglSewa.text = "Belum ada"
        }

        binding.tvUrlPembayaran.setOnClickListener{
            if(data.payment?.url != null){
                val url = data.payment?.url
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(url)
                startActivity(i)
            }else{
                activity?.let { it1 ->
                    Snackbar.make(it1.findViewById(android.R.id.content), "Link belum ada!", Snackbar.LENGTH_SHORT)
                        .show()
                }
            }

        }

        binding.btnHubungiSales.setOnClickListener {
            val url = "https://api.whatsapp.com/send?phone=6285162994602"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)

        }

        val totalSewa = formatPrices(data.payment?.total_harga.toString())
        binding.tvTotalSewa.text = "Total Sewa: $totalSewa"
        binding.btnLihatDataPenyewa.setOnClickListener {
            val directions = DetailTransactionFragmentDirections.actionFragmentDetailCTransactionToDetailPenyewaFragment(data)
            view?.findNavController()?.navigate(directions)
        }
        if(data.payment?.payment_status == "UNPAID" && data.payment.url == null){
            binding.btnLihatDataPembayaran.text = "Ubah Data"
            binding.btnCetak.text = "Lanjut Pembayaran"
            if(data.status_verification.equals("Disetujui")){
                binding.btnHubungiSales.visibility = View.VISIBLE
                binding.btnCetak.setOnClickListener{
                    val directions = DetailTransactionFragmentDirections.actionFragmentDetailCTransactionToDetailPembayaranFragment(data)
                    view?.findNavController()?.navigate(directions)
                }
                binding.btnLihatDataPembayaran.setOnClickListener {
                    val directions = DetailTransactionFragmentDirections.actionFragmentDetailCTransactionToDetailPembayaranFragment(data)
                    view?.findNavController()?.navigate(directions)
                }
            }else{
                binding.btnHubungiSales.visibility = View.GONE
                binding.btnLihatDataPembayaran.visibility = View.GONE
                binding.btnCetak.setOnClickListener{
                    activity?.let { it1 ->
                        Snackbar.make(it1.findViewById(android.R.id.content), "Status belum terverifikasi", Snackbar.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }else{
            binding.btnCetak.setOnClickListener {
                if(data.code_reference != null){
                    val url = "https://skripsi.activ.co.id/api/transaction/invoice?code_reference=" + data.code_reference
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(url)
                    startActivity(i)
                }else{
                    activity?.let { it1 ->
                        Snackbar.make(it1.findViewById(android.R.id.content), "Link belum ada!", Snackbar.LENGTH_SHORT)
                            .show()
                    }
                }
            }
            binding.btnLihatDataPembayaran.setOnClickListener {
                val directions = DetailTransactionFragmentDirections.actionFragmentDetailCTransactionToDetailPembayaranFragment(data)
                view?.findNavController()?.navigate(directions)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClickProductTransaction(v: View, data: TransactionDetail) {
        Snackbar.make(v, "Produk di klik", Snackbar.LENGTH_SHORT)
            .show()
    }
}