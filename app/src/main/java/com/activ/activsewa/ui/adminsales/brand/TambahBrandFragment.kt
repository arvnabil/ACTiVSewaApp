package com.activ.activsewa.ui.adminsales.brand

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.activ.activsewa.R
import com.activ.activsewa.databinding.FragmentTambahBrandBinding
import com.activ.activsewa.model.response.tambahBrand.TambahBrandResponse
import com.activ.activsewa.ui.adminsales.DashboardAdminSalesActivity
import com.activ.activsewa.ui.adminsales.brand.contract.TambahBrandContract
import com.activ.activsewa.ui.adminsales.brand.presenter.TambahBrandPresenter
import com.google.android.material.snackbar.Snackbar

class TambahBrandFragment : Fragment() , TambahBrandContract.View {
    lateinit var presenter: TambahBrandPresenter
    var progressDialog: Dialog?=null
    private lateinit var binding: FragmentTambahBrandBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding= FragmentTambahBrandBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        binding.includeToolbar.btnBack.setOnClickListener{
            val intent = Intent(context, DashboardAdminSalesActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        binding.includeToolbar.toolbar.title = "Tambah Brand"
        presenter = TambahBrandPresenter(this, requireContext())
        initView()

        binding.btnTambahBrand.setOnClickListener {
            val name = binding.etNama.text.toString()
            if (name.isNullOrEmpty()) {
                binding.etNama.error = "Silahkan masukkan Nama Brand"
                binding.etNama.requestFocus()
            }else{
                presenter.addBrand(name)
            }

        }
        return binding.root
    }

    private fun initView() {
        progressDialog = Dialog(requireContext())
        val dialogLayout = layoutInflater.inflate(R.layout.dialog_loader, null)

        progressDialog?.let {
            it.setContentView(dialogLayout)
            it.setCancelable(false)
            it.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
    }

    override fun onTambahBrandSuccess(tambahBrandResponse: TambahBrandResponse) {
        findNavController().popBackStack()
        activity?.let { it1 ->
            Snackbar.make(it1.findViewById(android.R.id.content), "Berhasil Tambah Brand!", Snackbar.LENGTH_SHORT)
                .show()
        }
    }

    override fun onTambahBrandFailed(message: String) {
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

}