package com.activ.activsewa.ui.auth.masuk
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.activ.activsewa.MainActivity
import com.activ.activsewa.R
import com.activ.activsewa.databinding.FragmentMasukBinding
import com.activ.activsewa.model.response.login.LoginResponse
import com.activ.activsewa.ui.adminsales.DashboardAdminSalesActivity
import com.activ.activsewa.ui.adminsales.dashboard_admin_sales.DashboardAdminSalesFragment
import com.activ.activsewa.ui.auth.AuthActivity
import com.activ.activsewa.ui.sales.DashboardSalesActivity
import com.activ.activsewa.utils.PreferenceHelper
import com.activ.activsewa.utils.PreferenceHelper.email
import com.activ.activsewa.utils.PreferenceHelper.name
import com.activ.activsewa.utils.PreferenceHelper.roles
import com.activ.activsewa.utils.PreferenceHelper.token
import com.activ.activsewa.utils.PreferenceHelper.user
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
class MasukFragment : Fragment() , MasukContract.View {
    lateinit var presenter: MasukPresenter
    var progressDialog : Dialog? = null
    private lateinit var binding: FragmentMasukBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentMasukBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = MasukPresenter(this)
        val prefs = PreferenceHelper.defaultPreference(requireContext())
        if(prefs.token?.isNotEmpty() == true){
            if(prefs.roles == "CUSTOMER"){
                val home = Intent(activity, MainActivity::class.java)
                startActivity(home)
                activity?.finish()
            }else if(prefs.roles == "SALES"){
                val sales = Intent(activity, DashboardSalesActivity::class.java)
                startActivity(sales)
                activity?.finish()
            }else{
                val home = Intent(activity, DashboardAdminSalesActivity::class.java)
                startActivity(home)
                activity?.finish()
            }
        }

//        initDummy()
        initView()
        binding.btnDaftar.setOnClickListener {
            val daftar = Intent(activity, AuthActivity::class.java)
            daftar.putExtra("page_request",2)
            startActivity(daftar)
        }

        binding.btnLupaPassword.setOnClickListener {
            val lupaPassword = Intent(activity, AuthActivity::class.java)
            lupaPassword.putExtra("page_request",1)
            startActivity(lupaPassword)
        }
        binding.btnMasuk.setOnClickListener {
            var email = binding.etEmail.text.toString()
            var password = binding.etPassword.text.toString()
            if (email.isNullOrEmpty()) {
                binding.etEmail.error = "Silahkan masukkan email Anda"
                binding.etEmail.requestFocus()
            } else if (password.isNullOrEmpty()) {
                binding.etPassword.error = "Silahkan masukkan password Anda"
                binding.etPassword.requestFocus()
            } else {
                presenter.subimtLogin(email, password)
            }
        }
    }

    override fun onLoginSuccess(loginResponse: LoginResponse) {

        // simpan data token
        val prefs = PreferenceHelper.defaultPreference(requireContext())
        val token = loginResponse.access_token
        prefs.token = "Bearer $token"
        prefs.name = loginResponse.user?.name
        prefs.email = loginResponse.user?.email
        prefs.roles = loginResponse.user?.roles

        Log.d("loginResponsseee: ", loginResponse.toString())

        val gson = Gson()
        val json = gson.toJson(loginResponse.user)
        prefs.user = json
        when (loginResponse.user?.roles) {
            "CUSTOMER" -> {
                val home = Intent(activity, MainActivity::class.java)
                startActivity(home)
            }
            "SALES" -> {
                val sales = Intent(activity, DashboardSalesActivity::class.java)
                startActivity(sales)
            }
            else -> {
                val home = Intent(activity, DashboardAdminSalesActivity::class.java)
                startActivity(home)
            }
        }
        activity?.finish()
        activity?.let { it1 ->
            Snackbar.make(it1.findViewById(android.R.id.content), "Masuk Berhasil!", Snackbar.LENGTH_SHORT)
                .show()
        }
    }

    override fun onLoginFailed(message: String) {
        Log.d("message Login Failed", message.toString())
        activity?.let { it1 ->
            Snackbar.make(it1.findViewById(android.R.id.content), "$message", Snackbar.LENGTH_SHORT)
                .show()
        }
    }

    private fun initDummy() {
        binding.etEmail.setText("nabil@activ.co.id")
        binding.etPassword.setText("ACTiV1234!")
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