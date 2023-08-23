package com.activ.activsewa.ui.adminsales.transaction

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.activ.activsewa.R
import com.activ.activsewa.adapter.TransactionSalesAdapter
import com.activ.activsewa.databinding.FragmentTransactionAdminSalesBinding
import com.activ.activsewa.model.response.transaction.Data
import com.activ.activsewa.model.response.transaction.TransactionResponse
import com.google.android.material.snackbar.Snackbar

class TransactionFragment : Fragment(),TransactionSalesAdapter.ItemAdapterCallback,
    TransactionContract.View {

    private var _binding: FragmentTransactionAdminSalesBinding? = null
    private lateinit var transactionPresenter: TransactionPresenter
    var progressDialog : Dialog? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTransactionAdminSalesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initView()
        transactionPresenter = TransactionPresenter(this, requireContext())
        transactionPresenter.getTransaction()
        binding.btnCetak.setOnClickListener{
            val url = "https://skripsi.activ.co.id/api/transaction/laporan-transaksi-sales"
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

    override fun onTransactionSuccess(transactionResponse: TransactionResponse) {
        if(transactionResponse.data.isNotEmpty()){
            var adapterTransaction = TransactionSalesAdapter(transactionResponse.data,this)
            var layoutManagerTransaction: RecyclerView.LayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            binding.rvTransactionList.layoutManager = layoutManagerTransaction
            binding.rvTransactionList.adapter = adapterTransaction
        }else{
            binding.tvNoTransaction.visibility = View.VISIBLE
        }
    }

    override fun onTransactionFailed(message: String) {
        Log.d("message Home Failed", message)
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

    override fun onClickTransaction(v: View, data: Data) {
        val directions = TransactionFragmentDirections.actionNavigationTransactionToFragmentDetailTransactionAdminSales(data)
        view?.findNavController()?.navigate(directions)
    }
}