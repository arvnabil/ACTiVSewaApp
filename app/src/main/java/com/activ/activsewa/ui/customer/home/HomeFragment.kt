package com.activ.activsewa.ui.customer.home
import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.activ.activsewa.R
import com.activ.activsewa.adapter.HomeAdapter
import com.activ.activsewa.adapter.TransactionAdapter
import com.activ.activsewa.databinding.FragmentHomeBinding
import com.activ.activsewa.model.response.home.Data
import com.activ.activsewa.model.response.transaction.Data as DataTransaction
import com.activ.activsewa.model.response.home.HomeResponse
import com.activ.activsewa.model.response.transaction.TransactionResponse
import com.activ.activsewa.ui.customer.ctransaction.CTransactionContract
import com.activ.activsewa.ui.customer.ctransaction.CTransactionPresenter
import com.activ.activsewa.utils.PreferenceHelper
import com.activ.activsewa.utils.PreferenceHelper.name
import com.activ.activsewa.utils.PreferenceHelper.roles
import com.google.android.material.snackbar.Snackbar
class HomeFragment : Fragment() ,HomeAdapter.ItemAdapterCallback, TransactionAdapter.ItemAdapterCallback, HomeContract.View, CTransactionContract.View {
    private var _binding: FragmentHomeBinding? = null
    private lateinit var presenter:HomePresenter
    private lateinit var transactionPresenter: CTransactionPresenter
    var progressDialog : Dialog? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initView()
        presenter = HomePresenter(this, requireContext())
        presenter.getHome()
        transactionPresenter = CTransactionPresenter(this, requireContext())
        transactionPresenter.getCTransaction()
        binding.btnLihatSemuaProduk.setOnClickListener{
            findNavController()
                .navigate(HomeFragmentDirections.actionNavigationHomeToNavigationProduct())
        }
        binding.btnLihatTransaction.setOnClickListener{
            findNavController()
                .navigate(HomeFragmentDirections.actionNavigationHomeToNavigationCtransaction())
        }
        return root
    }
    @SuppressLint("SetTextI18n")
    private fun initView() {
        progressDialog = Dialog(requireContext())
        val dialogLayout = layoutInflater.inflate(R.layout.dialog_loader, null)
        progressDialog?.let {
            it.setContentView(dialogLayout)
            it.setCancelable(false)
            it.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
        val prefs = PreferenceHelper.defaultPreference(requireContext())
        val name = prefs.name
        val roles = prefs.roles
        binding.txtHalo.text  = "Halo $name!"
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onClick(v: View, data: Data) {
        val directions = HomeFragmentDirections.actionNavigationHomeToFragmentDetail(data)
        view?.findNavController()?.navigate(directions)
    }
    override fun onHomeSuccess(homeResponse: HomeResponse) {
        Log.d("home Response : ", homeResponse.toString())
        if(homeResponse.data.isNotEmpty()){
            var adapter = HomeAdapter(homeResponse.data, this)
            var layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            binding.rvPerangkatList.layoutManager = layoutManager
            binding.rvPerangkatList.adapter = adapter
        }else{
            binding.tvNoPerangkat.visibility = View.VISIBLE
        }
    }
    override fun onHomeFailed(message: String) {
        Log.d("message Home Failed", message)
        activity?.let { it1 ->
            Snackbar.make(it1.findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT)
                .show()
        }
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
            Snackbar.make(it1.findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT)
                .show()
        }
    }
    override fun showLoading() {
        progressDialog?.show()
    }
    override fun dismissLoading() {
        progressDialog?.dismiss()
    }
    override fun onClickTransaction(v: View, data: DataTransaction) {
        val directions = HomeFragmentDirections.actionNavigationHomeToFragmentDetailCTransaction(data)
        view?.findNavController()?.navigate(directions)
    }
}