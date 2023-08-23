package com.activ.activsewa.utils

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.activ.activsewa.R

object FragmentUtil {
    fun refreshFragment(context: Context){
        context.let {
            val fragmentManager = (context as? AppCompatActivity)?.supportFragmentManager
            fragmentManager?.let {
                val currentFragment = fragmentManager.findFragmentById(R.id.container)
                currentFragment?.let {
                    fragmentManager.beginTransaction().detach(it).attach(it).commit()
                }
            }
        }
    }

}