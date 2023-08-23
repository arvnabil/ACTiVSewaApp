package com.activ.activsewa.ui.customer.product

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.activ.activsewa.MainActivity
import com.activ.activsewa.R
import com.activ.activsewa.adapter.ProductAdapter
import com.activ.activsewa.databinding.FragmentProductBinding
import com.activ.activsewa.model.response.home.Data
import com.activ.activsewa.model.response.home.HomeResponse
import com.google.android.material.snackbar.Snackbar

class ProductFragment : Fragment()  , ProductAdapter.ItemAdapterCallback, ProductContract.View {

    private var _binding: FragmentProductBinding? = null
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
        _binding = FragmentProductBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initView()
        presenter = ProductPresenter(this,requireContext())
        presenter.getProduct()

        binding.includeToolbar.btnBack.setOnClickListener{
            val intent = Intent(context, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
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
        val directions = ProductFragmentDirections.actionNavigationProductToFragmentDetail(data)
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