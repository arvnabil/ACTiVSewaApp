package com.activ.activsewa.ui.customer.filing

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
import com.activ.activsewa.adapter.FilingAdapter
import com.activ.activsewa.databinding.FragmentFilingBinding
import com.activ.activsewa.model.response.pengajuan.Data
import com.activ.activsewa.model.response.pengajuan.PengajuanResponse
import com.activ.activsewa.model.response.set_verification.SetVerificationResponse
import com.activ.activsewa.utils.Helpers
import com.google.android.material.snackbar.Snackbar

class FilingFragment : Fragment(), FilingAdapter.ItemAdapterCallback, FilingContract.View {
    private var _binding: FragmentFilingBinding? = null
    private lateinit var filingPresenter: FilingPresenter
    var progressDialog : Dialog? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilingBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initView()
        filingPresenter = FilingPresenter(this, requireContext())
        filingPresenter.getFilingContract()

        binding.btnVerifikasi.setOnClickListener{
            val nama = binding.etNama.text.toString()
            val nik = binding.etNik.text.toString()
            val npwp = binding.etNPWP.text.toString()
            val telp = binding.etTelp.text.toString()
            val alamat = binding.etAlamat.text.toString()

            if (nama.isNullOrEmpty()) {
                binding.etNama.error = "Silahkan masukkan nama Anda"
                binding.etNama.requestFocus()
            } else if (nik.isNullOrEmpty()) {
                binding.etNik.error = "Silahkan masukkan NIK Anda"
                binding.etNik.requestFocus()
            } else if (npwp.isNullOrEmpty()) {
                binding.etNPWP.error = "Silahkan masukkan NPWP Anda"
                binding.etNPWP.requestFocus()
            } else if (telp.isNullOrEmpty()) {
                binding.etTelp.error = "Silahkan masukkan NPWP Anda"
                binding.etTelp.requestFocus()
            } else if (alamat.isNullOrEmpty()) {
                binding.etAlamat.error = "Silahkan masukkan NPWP Anda"
                binding.etAlamat.requestFocus()
            }else {
                filingPresenter.subimtFilingVerification(
                    binding.etNama.text.toString(),
                    binding.etNik.text.toString(),
                    binding.etNPWP.text.toString(),
                    binding.etTelp.text.toString(),
                    binding.etAlamat.text.toString())
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
    override fun onClickProductFiling(v: View, data: Data) {
        Snackbar.make(v, "Produk di klik", Snackbar.LENGTH_SHORT)
            .show()
    }
    override fun onClickProductTrash(v: View, data: Data) {
        filingPresenter.getHapusProdukPengajuan(
            data?.id.toString(),
            v
        )
    }
    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun onFilingSuccess(pengajuanResponse: PengajuanResponse) {
        if(pengajuanResponse.data.isNotEmpty()){
            val adapterTransaction = FilingAdapter(pengajuanResponse.data,this)
            val layoutManagerTransaction: RecyclerView.LayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            binding.rvProductList.layoutManager = layoutManagerTransaction
            binding.rvProductList.adapter = adapterTransaction
            val totalSewa = Helpers.formatPrices(pengajuanResponse.grand_total.toString())
            binding.tvTotalSewa.text = "Total Sewa: $totalSewa"

            binding.tvTotalSewa.visibility = View.VISIBLE
            binding.txtNama.visibility = View.VISIBLE
            binding.txtNik.visibility = View.VISIBLE
            binding.txtNPWP.visibility = View.VISIBLE
            binding.txtTelp.visibility = View.VISIBLE
            binding.txtAlamat.visibility = View.VISIBLE
            binding.txtVerifikasiData.visibility = View.VISIBLE

            binding.etNama.visibility = View.VISIBLE
            binding.etNik.visibility = View.VISIBLE
            binding.etNPWP.visibility = View.VISIBLE
            binding.etTelp.visibility = View.VISIBLE
            binding.etAlamat.visibility = View.VISIBLE

            binding.btnVerifikasi.visibility = View.VISIBLE
        }else{
            binding.tvNoFiling.visibility = View.VISIBLE
        }
    }

    override fun onFilingFailed(message: String) {
        Log.d("message Home Failed", message)
        activity?.let { it1 ->
            Snackbar.make(it1.findViewById(android.R.id.content), "$message", Snackbar.LENGTH_SHORT)
                .show()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onHapusProdukPengajuanSuccess(pengajuanResponse: PengajuanResponse) {
        val id = findNavController().currentDestination?.id
        findNavController().popBackStack(id!!,true)
        findNavController().navigate(id)
        activity?.let { it1 ->
            Snackbar.make(it1.findViewById(android.R.id.content), "Produk berhasil dihapus!", Snackbar.LENGTH_SHORT)
                .show()
        }
    }

    override fun onHapusProdukPengajuanFailed(message: String) {
        Log.d("message Hapus Failed", message)
        activity?.let { it1 ->
            Snackbar.make(it1.findViewById(android.R.id.content), "$message", Snackbar.LENGTH_SHORT)
                .show()
        }
    }

    override fun onSetFilingVerificationSuccess(setVerificationResponse: SetVerificationResponse) {
        val directions = FilingFragmentDirections.actionNavigationFilingToFragmentSuccessPengajuan()
        view?.findNavController()?.navigate(directions)
    }

    override fun onSetFilingVerificationFailed(message: String) {
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