package com.activ.activsewa.ui.customer.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.activ.activsewa.databinding.FragmentProfileBinding
import com.activ.activsewa.model.response.login.User
import com.activ.activsewa.ui.auth.AuthActivity
import com.activ.activsewa.utils.PreferenceHelper
import com.activ.activsewa.utils.PreferenceHelper.clearValues
import com.activ.activsewa.utils.PreferenceHelper.email
import com.activ.activsewa.utils.PreferenceHelper.name
import com.activ.activsewa.utils.PreferenceHelper.roles
import com.activ.activsewa.utils.PreferenceHelper.token
import com.activ.activsewa.utils.PreferenceHelper.user
import com.activ.activsewa.utils.PreferenceHelper.userId
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import java.io.File
import kotlin.system.exitProcess

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val prefs = PreferenceHelper.defaultPreference(requireContext())
        val name = prefs.name
        val email = prefs.email

        binding.tvName.text = name
        binding.tvEmail.text = email

        var user = prefs.user
        var userResponse = Gson().fromJson(user, User::class.java)
        if (!userResponse.avatar.isNullOrEmpty()){
            Glide.with(requireActivity())
                .load(userResponse.avatar)
                .apply(RequestOptions.circleCropTransform())
                .into(binding.ivPicture)
        }

        binding.btnKeluar.setOnClickListener{
            clearSharedPreferences(requireContext())

            val masuk = Intent(activity, AuthActivity::class.java)
            masuk.putExtra("page_request_logout",1)
            startActivity(masuk)
            activity?.finish()
        }
        return root
    }

    private fun clearSharedPreferences(ctx: Context) {
        val dir: File = File(ctx.filesDir.parent.toString() + "/shared_prefs/")
        val children = dir.list()
        for (i in children.indices) {
            // clear each preference file
            ctx.getSharedPreferences(children[i].replace(".xml", ""), Context.MODE_PRIVATE).edit()
                .clear().apply()
            //delete the file
            File(dir, children[i]).delete()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}