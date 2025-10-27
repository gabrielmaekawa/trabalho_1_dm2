package com.example.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import android.util.Log
import android.widget.Toast
import com.example.myapplication.adapter.CategoriesAdapter
import com.example.myapplication.databinding.FragmentCategoriesBinding
import com.example.myapplication.model.Category
import com.example.myapplication.network.RetrofitClient
import com.example.myapplication.utils.NetworkUtils
import kotlinx.coroutines.launch

class CategoriesFragment : Fragment() {
    private var _binding: FragmentCategoriesBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: CategoriesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.recyclerViewCategories.layoutManager = GridLayoutManager(context, 2)

        lifecycleScope.launch {
            try {
                val dados = RetrofitClient.apiService.getData().body() ?: emptyList()
                // Extrair categorias únicas
                val categoriasMap = mutableMapOf<String, Category>()
                dados.forEach {
                    if (!categoriasMap.containsKey(it.categoria)) {
                        categoriasMap[it.categoria] = Category(it.categoria,it.categoria, it.urlImagem)
                    }
                }
                val categorias = categoriasMap.values.toList()

                adapter = CategoriesAdapter { category ->
                    Toast.makeText(context, "Categoria: ${category.descricao}", Toast.LENGTH_SHORT).show()
                }
                adapter.submitList(categorias)
                binding.recyclerViewCategories.adapter = adapter
            } catch(e: Exception) {
                Toast.makeText(context, "Erro ao carregar categorias", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
