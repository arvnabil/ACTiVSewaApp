package com.activ.activsewa.ui.auth.daftar

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.activ.activsewa.ActivSewa
import com.activ.activsewa.R
import com.activ.activsewa.databinding.FragmentDaftarBinding
import com.activ.activsewa.model.request.RegisterRequest
import com.activ.activsewa.model.response.daftar.DaftarResponse
import com.activ.activsewa.ui.auth.AuthActivity
import com.activ.activsewa.utils.PreferenceHelper
import com.activ.activsewa.utils.PreferenceHelper.email
import com.activ.activsewa.utils.PreferenceHelper.name
import com.activ.activsewa.utils.PreferenceHelper.roles
import com.activ.activsewa.utils.PreferenceHelper.token
import com.activ.activsewa.utils.PreferenceHelper.user
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
class DaftarFragment : Fragment() , DaftarContract.View {
    private lateinit var data:RegisterRequest
    lateinit var presenter: DaftarPresenter
    var progressDialog: Dialog?=null
    private lateinit var binding: FragmentDaftarBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding= FragmentDaftarBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment

        presenter = DaftarPresenter(this)
//        initDummy()
        initListener()
        initView()
        binding.btnMasuk.setOnClickListener {
            val masuk = Intent(activity, AuthActivity::class.java)
            masuk.putExtra("page_request",0)
            startActivity(masuk)
        }

        binding.btnLupaPassword.setOnClickListener {
            val lupaPassword = Intent(activity, AuthActivity::class.java)
            lupaPassword.putExtra("page_request",1)
            startActivity(lupaPassword)
        }
        return binding.root
    }

    private fun initListener() {
        binding.btnDaftar.setOnClickListener {

        var fullname = binding.editNama.text.toString()
        var email = binding.editEmail.text.toString()
        var pass = binding.editPassword.text.toString()
        var telp = binding.editTelp.text.toString()

           data =  RegisterRequest(
               fullname,
               email,
               pass,
               telp,
           )

            if (fullname.isNullOrEmpty()) {
                binding.editNama.error = "Silahkan masukkan fullname"
                binding.editNama.requestFocus()
            } else if (email.isNullOrEmpty()) {
                binding.editEmail.error = "Silahkan masukkan email"
                binding.editEmail.requestFocus()
            } else if (pass.isNullOrEmpty()) {
                binding.editPassword.error = "Silahkan masukkan pass"
                binding.editPassword.requestFocus()
            } else if (telp.isNullOrEmpty()) {
                binding.editTelp.error = "Silahkan masukkan No HP"
                binding.editTelp.requestFocus()
            } else {
                presenter.submitRegister(data, it)
            }
        }
    }

    private fun initDummy() {
        binding.editNama.setText("Jennie Kim White")
        binding.editEmail.setText("jennie.kim@blackpink.com")
        binding.editPassword.setText("12345678")
        binding.editTelp.setText("12345678")
    }

    override fun onRegisterSuccess(daftarResponse: DaftarResponse, view: View) {
        // simpan data token
        val prefs = PreferenceHelper.defaultPreference(requireContext())
        val token = daftarResponse.access_token
        prefs.token = "Bearer $token"
        prefs.name = daftarResponse.user?.name
        prefs.email = daftarResponse.user?.email
        prefs.roles = daftarResponse.user?.roles

        Log.d("loginResponsseee: ", daftarResponse.toString())

        val gson = Gson()
        val json = gson.toJson(daftarResponse.user)
        prefs.user = json

        Navigation.findNavController(view)
        .navigate(R.id.action_success_daftar, null)
    }

    override fun onRegisterFailed(message: String) {
        Log.d("message Register Failed", message)
        activity?.let { it1 ->
            Snackbar.make(it1.findViewById(android.R.id.content), "$message", Snackbar.LENGTH_SHORT)
                .show()
        }
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

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }

}