package com.oluwafemi.findadev.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.oluwafemi.findadev.R
import com.oluwafemi.findadev.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_main
        )

        replaceFragment(MainFragment())

        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            var fragment: Fragment? = null
            when (item.itemId) {
                R.id.all_devs -> fragment = MainFragment()
                R.id.reg_self -> fragment = RegisterFragment()
            }
            this@MainActivity.replaceFragment(fragment)
        }

    }
}

fun AppCompatActivity.replaceFragment(fragment: Fragment?): Boolean {
    if (fragment != null) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.host_fragment, fragment)
        transaction.commit()

        return true
    }
    return false
}
