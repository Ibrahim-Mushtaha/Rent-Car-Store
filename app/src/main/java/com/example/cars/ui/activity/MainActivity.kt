package com.example.cars.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.cars.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        setSupportActionBar(toolbar)

        val navController = findNavController(R.id.fragment)
        appBarConfiguration= AppBarConfiguration(setOf(
            R.id.onBoarding_Fragment,
            R.id.home_Fragment
        ))

        findViewById<Toolbar>(R.id.toolbar)
                .setupWithNavController(navController,appBarConfiguration)

    }
}
