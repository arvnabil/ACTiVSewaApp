package com.activ.activsewa.ui.auth

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.activ.activsewa.R
import com.activ.activsewa.databinding.ActivityAuthBinding
import com.google.android.material.snackbar.Snackbar
import kotlin.system.exitProcess

class AuthActivity : AppCompatActivity() {
    private var time: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityAuthBinding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val pageRequest = intent.getIntExtra("page_request",0)
        val pageRequestLogout = intent.getIntExtra("page_request_logout",0)
        if (pageRequestLogout == 1) {
            this?.let { it1 ->
                Snackbar.make(it1.findViewById(android.R.id.content), "Berhasil Keluar", Snackbar.LENGTH_SHORT)
                    .show()
            }
        }

        if(pageRequest == 1){
            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.fragmentLupaPassword, true)
                .build()

            Navigation.findNavController(findViewById(R.id.authHostFragment))
                .navigate(R.id.action_lupa_password, null, navOptions)
        }else if(pageRequest == 2) {
            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.fragmentDaftar, true)
                .build()

            Navigation.findNavController(findViewById(R.id.authHostFragment))
                .navigate(R.id.action_daftar, null, navOptions)
        }
    }

    override fun onBackPressed() {
        val twoSecond = 2000
        val strExit: String = getString(R.string.exit)
        if (time.plus(twoSecond) > System.currentTimeMillis()) {
            super.onBackPressed()
            moveTaskToBack(true)
            finish()
            exitProcess(0)
        } else {
            Toast.makeText(this, strExit, Toast.LENGTH_SHORT)
                .show()
        }
        time = System.currentTimeMillis()
    }
}