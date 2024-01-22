package com.promi

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.promi.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // 상단 탭의 값을 변경하는 코드 => 나중에 쓸 일 있으면 주석 풀 것
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.navigation_calendar, R.id.navigation_promise, R.id.navigation_my_information
//            )
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)


        navView.setupWithNavController(navController)
    }

    fun setToolbar(isVisible: Boolean = false, name: String = "") {
        if (isVisible) {
            binding.btnToolbarBack.setOnClickListener { onBackPressed() }
            binding.tvToolbarName.text = name
            binding.toolbar.visibility = View.VISIBLE
        }
        else
            binding.toolbar.visibility = View.GONE
    }
}