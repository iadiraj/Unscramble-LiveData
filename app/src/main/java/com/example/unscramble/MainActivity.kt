package com.example.unscramble

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val homeFragment = HomeFragment()
        val fragmentManager = supportFragmentManager.beginTransaction()
        fragmentManager.apply {
            add(R.id.fragment_container, homeFragment)
            setReorderingAllowed(true)
            commit()
        }
    }
}