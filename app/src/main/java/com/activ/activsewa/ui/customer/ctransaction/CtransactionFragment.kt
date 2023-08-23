package com.activ.activsewa.ui.customer.ctransaction

import android.app.Dialog
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
import com.activ.activsewa.adapter.TransactionAdapter
import com.activ.activsewa.databinding.FragmentCtransactionBinding
import com.activ.activsewa.model.response.transaction.Data
import com.activ.activsewa.model.response.transaction.TransactionResponse
import com.google.android.material.snackbar.Snackbar

class CtransactionFragment : Fragment(),TransactionAdapter.ItemAdapterCallback,CTransactionContract.View {

    private var _binding: FragmentCtransactionBinding? = null
    private lateinit var transactionPresenter: CTransactionPresenter
    var progressDialog : Dialog? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCtransactionBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initView()
        transactionPresenter = CTransactionPresenter(this, requireContext())
        transactionPresenter.getCTransaction()

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

    override fun onCTransactionSuccess(transactionResponse: TransactionResponse) {
        if(transactionResponse.data.isNotEmpty()){
            var adapterTransaction = TransactionAdapter(transactionResponse.data,this)
            var layoutManagerTransaction: RecyclerView.LayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            binding.rvTransactionList.layoutManager = layoutManagerTransaction
            binding.rvTransactionList.adapter = adapterTransaction
        }else{
            binding.tvNoTransaction.visibility = View.VISIBLE
        }
    }

    override fun onCTransactionFailed(message: String) {
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
        val directions = CtransactionFragmentDirections.actionNavigationCtransactionToFragmentDetailCTransaction(data)
        view?.findNavController()?.navigate(directions)
    }
}