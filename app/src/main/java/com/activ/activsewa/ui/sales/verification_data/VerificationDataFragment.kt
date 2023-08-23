package com.activ.activsewa.ui.sales.verification_data

import android.R
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.activ.activsewa.databinding.FragmentVerificationDataBinding
import com.activ.activsewa.model.response.setujui_verifikasi.SetujuiVerifikasiResponse
import com.activ.activsewa.model.response.tolak_verifikasi.TolakVerifikasiResponse
import com.activ.activsewa.model.response.transaction.Data
import com.activ.activsewa.ui.sales.DashboardSalesActivity
import com.google.android.material.snackbar.Snackbar
class VerificationDataFragment : Fragment(), VerificationDataContract.View {
    var bundle:Bundle ?= null
    private var _binding: FragmentVerificationDataBinding? = null
    private val args: VerificationDataFragmentArgs by navArgs()
    private lateinit var verificationDataPresenter: VerificationDataPresenter
    var progressDialog : Dialog? = null
    private val binding get() = _binding!!
    private lateinit var dataTransaction: Data

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentVerificationDataBinding.inflate(inflater, container, false)
        binding.includeToolbar.btnBack.setOnClickListener{
            val intent = Intent(context, DashboardSalesActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)

        }
        verificationDataPresenter = VerificationDataPresenter(this, requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        dataTransaction = args.data!!
        binding.includeToolbar.toolbar.title = dataTransaction.code_reference
        if(dataTransaction.status_verification.toString() == "Belum Terverifikasi"){
            binding.btnTolak.visibility = View.VISIBLE
            binding.btnSetujui.visibility = View.VISIBLE
        }
        initView(dataTransaction)
        initVerification()

    }

    private fun initVerification() {
        var nama = ""
        var nik = ""
        var npwp = ""
        var telp = ""
        var alamat = ""
        binding.radioNama.setOnCheckedChangeListener{ radioGroup, i ->
            var rbNama = view?.findViewById<RadioButton>(i)
            if(rbNama!=null){
                nama = if (rbNama.text.equals("Sesuai")) "sesuai" else "tidak"
            }
        }
        binding.radioNIK.setOnCheckedChangeListener{ radioGroup, i ->
            var rbNik = view?.findViewById<RadioButton>(i)
            if(rbNik!=null){
                nik = if (rbNik.text.equals("Sesuai")) "sesuai" else "tidak"
            }
        }
        binding.radioNpwp.setOnCheckedChangeListener{ radioGroup, i ->
            var rbNpwp = view?.findViewById<RadioButton>(i)
            if(rbNpwp!=null){
                npwp = if (rbNpwp.text.equals("Sesuai")) "sesuai" else "tidak"
            }
        }
        binding.radioTelp.setOnCheckedChangeListener{ radioGroup, i ->
            var rbTelp = view?.findViewById<RadioButton>(i)
            if(rbTelp!=null){
                telp = if (rbTelp.text.equals("Sesuai")) "sesuai" else "tidak"
            }
        }
        binding.radioAlamat.setOnCheckedChangeListener{ radioGroup, i ->
            var rbAlamat = view?.findViewById<RadioButton>(i)
            if(rbAlamat!=null){
                alamat = if (rbAlamat.text.equals("Sesuai")) "sesuai" else "tidak"
            }
        }

        binding.btnSetujui.setOnClickListener {
            rbNotEmpty(nama,nik,npwp,telp,alamat)
            if(nama=="sesuai"&& nik=="sesuai"&& npwp=="sesuai"&&telp=="sesuai"&&alamat=="sesuai"){
                val cekFotoKtp = "sesuai"
                val cekFotoNpwp = "sesuai"
                verificationDataPresenter.submitSetujui(dataTransaction.id.toString(),nama,nik,npwp,telp,alamat, cekFotoKtp, cekFotoNpwp )
            }else{
                activity?.let { it1 ->
                    Snackbar.make(it1.findViewById(R.id.content), "Belum semua sesuai, tidak dapat disetujui!", Snackbar.LENGTH_SHORT)
                        .show()
                }
            }
        }
        binding.btnTolak.setOnClickListener {
            rbNotEmpty(nama,nik,npwp,telp,alamat)
            val cekFotoKtp = "sesuai"
            val cekFotoNpwp = "sesuai"
            verificationDataPresenter.submitTolak(dataTransaction.id.toString(),nama,nik,npwp,telp,alamat, cekFotoKtp, cekFotoNpwp )
        }
    }

    private fun rbNotEmpty(nama: String, nik: String, npwp: String, telp: String, alamat: String) {
        if (nama.isNotEmpty() && nik.isNotEmpty() && npwp.isNotEmpty() && telp.isNotEmpty() && alamat.isNotEmpty()){
            val data = "Nama: $nama! NIK: $nik NPWP: $npwp telp: $telp Alamat:$alamat"
            Log.d("Verifikasi Data: ",data)
        }else{
            activity?.let { it1 ->
                Snackbar.make(it1.findViewById(R.id.content), "Belum Terisi Semua!", Snackbar.LENGTH_SHORT)
                    .show()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initView(data: Data) {

        bundle = bundleOf("data" to data)
        Log.d("Data Sukses: ",data.toString())
        binding.editNama.setText(data.nama_penyewa)
        binding.etNik.setText(data.nik)
        binding.etNPWP.setText(data.npwp)
        binding.etTelp.setText(data.telp)
        binding.etAlamat.setText(data.alamat)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onVerificationApproveSuccess(setujuiVerifikasiResponse: SetujuiVerifikasiResponse) {
        val directions = VerificationDataFragmentDirections.actionVerificationDataFragmentToSuccessSetujuiVerificationFragment()
        view?.findNavController()?.navigate(directions)
    }

    override fun onVerificationApproveFailed(message: String) {
        Log.d("message Approve Failed", message)
        activity?.let { it1 ->
            Snackbar.make(it1.findViewById(R.id.content), "$message", Snackbar.LENGTH_SHORT)
                .show()
        }
    }

    override fun onVerificationRejectSuccess(tolakVerifikasiResponse: TolakVerifikasiResponse) {
        val directions = VerificationDataFragmentDirections.actionVerificationDataFragmentToSuccessTolakVerificationFragment()
        view?.findNavController()?.navigate(directions)
    }

    override fun onVerificationRejectFailed(message: String) {
        Log.d("message Reject Failed", message)
        activity?.let { it1 ->
            Snackbar.make(it1.findViewById(R.id.content), "$message", Snackbar.LENGTH_SHORT)
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