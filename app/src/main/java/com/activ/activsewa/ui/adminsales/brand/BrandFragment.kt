package com.activ.activsewa.ui.adminsales.brand

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
import com.activ.activsewa.adapter.BrandAdapter
import com.activ.activsewa.databinding.FragmentBrandBinding
import com.activ.activsewa.model.response.brand.BrandResponse
import com.activ.activsewa.model.response.brand.Data
import com.activ.activsewa.ui.adminsales.DashboardAdminSalesActivity
import com.activ.activsewa.ui.adminsales.brand.contract.BrandContract
import com.activ.activsewa.ui.adminsales.brand.presenter.BrandPresenter
import com.google.android.material.snackbar.Snackbar

class BrandFragment : Fragment(),BrandAdapter.ItemAdapterCallback, BrandContract.View {

    private var _binding: FragmentBrandBinding? = null
    private lateinit var presenter: BrandPresenter
    var progressDialog : Dialog? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBrandBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initView()
        presenter = BrandPresenter(this, requireContext())
        presenter.getBrand()

        binding.includeToolbar.btnBack.setOnClickListener{
            val intent = Intent(context, DashboardAdminSalesActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        binding.includeToolbar.toolbar.title = "Daftar Brand"
        binding.btnTambahBrand.setOnClickListener{
            val directions = BrandFragmentDirections.actionBrandFragmentToTambahBrandFragment()
            view?.findNavController()?.navigate(directions)
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

    override fun onBrandSuccess(brandResponse: BrandResponse) {
        if(brandResponse.data.isNotEmpty()){
            var adaptorBrand = BrandAdapter(brandResponse.data,this)
            var layoutManagerTransaction: RecyclerView.LayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            binding.rvBrandList.layoutManager = layoutManagerTransaction
            binding.rvBrandList.adapter = adaptorBrand
        }else{
            binding.tvNoBrand.visibility = View.VISIBLE
        }
    }

    override fun onBrandFailed(message: String) {
        Log.d("message Brand Failed", message)
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
        val nama = data.name
        activity?.let { it1 ->
            Snackbar.make(it1.findViewById(android.R.id.content), "Klik $nama!", Snackbar.LENGTH_SHORT)
                .show()
        }
    }
}