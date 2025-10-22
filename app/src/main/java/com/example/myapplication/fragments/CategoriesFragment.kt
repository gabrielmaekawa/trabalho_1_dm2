package com.example.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import android.util.Log
import com.example.myapplication.adapter.CategoriesAdapter
import com.example.myapplication.databinding.FragmentCategoriesBinding
import com.example.myapplication.network.RetrofitClient
import com.example.myapplication.utils.LocalDataProvider
import com.example.myapplication.utils.NetworkUtils
import kotlinx.coroutines.launch

class CategoriesFragment : Fragment() {
    private var _binding: FragmentCategoriesBinding? = null
    private val binding get() = _binding!!
    private lateinit var categoriesAdapter: CategoriesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        loadCategories()
    }

    private fun setupRecyclerView() {
        categoriesAdapter = CategoriesAdapter()
        
        binding.recyclerViewCategories.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = categoriesAdapter
        }
    }

    private fun loadCategories() {
        // Verificar conectividade antes de fazer a requisição
        if (!NetworkUtils.isNetworkAvailable(requireContext())) {
            showError("Sem conexão com a internet")
            return
        }
        
        Log.d("CategoriesFragment", "Iniciando carregamento de categorias")
        
        _binding?.let { binding ->
            binding.progressBarCategories.visibility = View.VISIBLE
            binding.textViewEmptyCategories.visibility = View.GONE
        }
        
        lifecycleScope.launch {
            try {
                Log.d("CategoriesFragment", "Fazendo requisição para API")
                val response = RetrofitClient.apiService.getData()
                Log.d("CategoriesFragment", "Resposta recebida: ${response.code()}")
                
                if (response.isSuccessful) {
                    val data = response.body()
                    Log.d("CategoriesFragment", "Dados recebidos: $data")
                    
                    data?.categorias?.let { categories ->
                        Log.d("CategoriesFragment", "Categorias encontradas: ${categories.size}")
                        _binding?.let { binding ->
                            val categories = com.example.myapplication.utils.LocalDataProvider.getLocalCategories()

                            if (categories.isNotEmpty()) {
                                binding.recyclerViewCategories.visibility = View.VISIBLE
                                binding.textViewEmptyCategories.visibility = View.GONE
                                categoriesAdapter.submitList(categories)
                            } else {
                                showEmptyState()
                            }

                            binding.progressBarCategories.visibility = View.GONE
                        }
                    } ?: run {
                        Log.d("CategoriesFragment", "Categorias nulas")
                        _binding?.let { showEmptyState() }
                    }
                } else {
                    Log.e("CategoriesFragment", "Erro na resposta: ${response.code()} - ${response.message()}")
                    showError("Erro ao carregar categorias: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("CategoriesFragment", "Exceção: ${e.message}", e)
                val errorMessage = NetworkUtils.getErrorMessage(e)
                
                // Se houver erro de rede, tentar usar dados locais
                if (errorMessage.contains("Servidor não encontrado") || 
                    errorMessage.contains("Conexão recusada") ||
                    errorMessage.contains("Timeout")) {
                    
                    Log.d("CategoriesFragment", "Usando dados locais como fallback")
                    val localCategories = LocalDataProvider.getLocalCategories()
                    _binding?.let { binding ->
                        categoriesAdapter.submitList(localCategories)
                        binding.recyclerViewCategories.visibility = View.VISIBLE
                        Log.d("CategoriesFragment", "Categorias locais carregadas: ${localCategories.size}")
                    }
                } else if (errorMessage != "Operação cancelada") {
                    showError(errorMessage)
                }
            } finally {
                _binding?.let { binding ->
                    binding.progressBarCategories.visibility = View.GONE
                }
            }
        }
    }

    private fun showError(message: String) {
        // You can implement a toast or dialog here
        android.widget.Toast.makeText(context, message, android.widget.Toast.LENGTH_LONG).show()
    }

    private fun showEmptyState() {
        _binding?.let { binding ->
            binding.recyclerViewCategories.visibility = View.GONE
            binding.textViewEmptyCategories.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
