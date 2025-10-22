package com.example.shoppingapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.fragments.CategoriesFragment
import com.example.myapplication.fragments.EmptyFragment
import com.example.myapplication.fragments.ItemsFragment
import com.example.myapplication.fragments.LoginFragment
import com.example.myapplication.fragments.PurchasesFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadFragment(ItemsFragment()) // Tela 1 inicial

        binding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.menu_itens -> loadFragment(ItemsFragment()) // Tela 1
                R.id.menu_compras -> loadFragment(PurchasesFragment()) // Tela 2 (extra)
                R.id.menu_categorias -> loadFragment(CategoriesFragment()) // Tela 3
                R.id.menu_info -> loadFragment(EmptyFragment()) // Tela 4
                R.id.menu_login -> loadFragment(LoginFragment()) // Tela 5
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment): Boolean {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
        return true
    }
}

