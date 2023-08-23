package com.activ.activsewa.ui.adminsales.customers

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.activ.activsewa.R
import com.activ.activsewa.adapter.CustomerAdapter
import com.activ.activsewa.databinding.FragmentCustomerAdminSalesBinding
import com.activ.activsewa.model.response.customer.CustomerRepsonse
import com.activ.activsewa.model.response.customer.Data
import com.activ.activsewa.ui.adminsales.DashboardAdminSalesActivity
import com.activ.activsewa.ui.sales.DashboardSalesActivity
import com.google.android.material.snackbar.Snackbar

class CustomerFragment : Fragment(),CustomerAdapter.ItemAdapterCallback, CustomerContract.View {

    private var _binding: FragmentCustomerAdminSalesBinding? = null
    private lateinit var presenter: CustomerPresenter
    var progressDialog : Dialog? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCustomerAdminSalesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initView()
        presenter = CustomerPresenter(this, requireContext())
        presenter.getCustomer()

        binding.includeToolbar.btnBack.setOnClickListener{
            val intent = Intent(context, DashboardAdminSalesActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        binding.includeToolbar.toolbar.title = "Daftar Data Penyewa"
        binding.btnCetak.setOnClickListener{
            val url = "https://skripsi.activ.co.id/api/transaction/laporan-data-penyewa"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }
        return root
    }

    private fun initView(){
        progressDialog = Dialog(requireContext())
        val dialogLayout = layoutInflater.inflate(R.layout.dialog_loader, null)

        progressDialog?.let {
            it.setContentView(dialogLayout)
            it.setCancelable(false)
            it.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCustomerSuccess(customerRepsonse: CustomerRepsonse) {
        if(customerRepsonse.data.isNotEmpty()){
            var adapterTransaction = CustomerAdapter(customerRepsonse.data,this)
            var layoutManagerTransaction: RecyclerView.LayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            binding.rvPenyewaList.layoutManager = layoutManagerTransaction
            binding.rvPenyewaList.adapter = adapterTransaction
        }else{
            binding.tvNoPenyewa.visibility = View.VISIBLE
        }
    }

    override fun onCustomerFailed(message: String) {
        Log.d("message Customer Failed", message)
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

    override fun onClick(v: View, data: Data) {
        activity?.let { it1 ->
            Snackbar.make(it1.findViewById(android.R.id.content), "Detail belum tersedia!", Snackbar.LENGTH_SHORT)
                .show()
        }
    }
}