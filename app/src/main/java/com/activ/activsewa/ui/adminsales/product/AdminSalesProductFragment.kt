package com.activ.activsewa.ui.adminsales.product

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.activ.activsewa.R
import com.activ.activsewa.adapter.ProductAdapter
import com.activ.activsewa.databinding.FragmentProductAdminSalesBinding
import com.activ.activsewa.model.response.home.Data
import com.activ.activsewa.model.response.home.HomeResponse
import com.activ.activsewa.ui.adminsales.transaction.TransactionFragmentDirections
import com.google.android.material.snackbar.Snackbar

class AdminSalesProductFragment : Fragment()  , ProductAdapter.ItemAdapterCallback, ProductContract.View {

    private var _binding: FragmentProductAdminSalesBinding? = null
    private lateinit var presenter: ProductPresenter
    var progressDialog : Dialog? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductAdminSalesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initView()
        presenter = ProductPresenter(this,requireContext())
        presenter.getProduct()

        binding.includeToolbar.btnBack.setOnClickListener{
            findNavController().popBackStack()
        }
        binding.includeToolbar.toolbar.title = "Daftar Produk"
        binding.btnCetak.setOnClickListener{
            val url = "https://skripsi.activ.co.id/api/product/laporan-produk"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }
        binding.btnTambahProduct.setOnClickListener{
            val directions = AdminSalesProductFragmentDirections.actionAdminSalesProductFragmentToTambahProdukFragment()
            view?.findNavController()?.navigate(directions)
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View, data: Data) {
        val directions = AdminSalesProductFragmentDirections.actionAdminSalesProductFragmentToDetailProductFragment2(data)
        view?.findNavController()?.navigate(directions)
    }

    override fun onProductSuccess(homeResponse: HomeResponse) {
        var adapter = ProductAdapter(homeResponse.data,this)
        var layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvProductList.layoutManager = layoutManager
        binding.rvProductList.adapter = adapter
    }

    override fun onProductFailed(message: String) {
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