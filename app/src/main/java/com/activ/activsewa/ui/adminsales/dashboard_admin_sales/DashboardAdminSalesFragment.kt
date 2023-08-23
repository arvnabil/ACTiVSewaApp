package com.activ.activsewa.ui.adminsales.dashboard_admin_sales
import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.activ.activsewa.R
import com.activ.activsewa.databinding.FragmentDashboardAdminSalesBinding
import com.activ.activsewa.utils.PreferenceHelper
import com.activ.activsewa.utils.PreferenceHelper.name
import com.activ.activsewa.utils.PreferenceHelper.roles

class DashboardAdminSalesFragment : Fragment() {
    private var _binding: FragmentDashboardAdminSalesBinding? = null
    var progressDialog: Dialog? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardAdminSalesBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initView()
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
        binding.txtHalo.text = "Halo $roles $name!"
        binding.btnTransaction.setOnClickListener{
            val directions = DashboardAdminSalesFragmentDirections.actionNavigationDashbordAdminSalesToNavigationTransaction2()
            view?.findNavController()?.navigate(directions)
        }
        binding.btnPenyewa.setOnClickListener{
            val directions = DashboardAdminSalesFragmentDirections.actionNavigationDashbordAdminSalesToCustomerFragmentAdminSales2()
            view?.findNavController()?.navigate(directions)
        }
        binding.btnBrand.setOnClickListener{
            val directions = DashboardAdminSalesFragmentDirections.actionNavigationDashbordAdminSalesToBrandFragment()
            view?.findNavController()?.navigate(directions)
        }
        binding.btnProduct.setOnClickListener{
            val directions = DashboardAdminSalesFragmentDirections.actionNavigationDashbordAdminSalesToProductFragment()
            view?.findNavController()?.navigate(directions)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}