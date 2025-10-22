package com.example.myapplication.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.adapter.ItemsAdapter
import com.example.myapplication.databinding.FragmentItemsBinding
import com.example.myapplication.firebase.FirebaseAuthService
import com.example.myapplication.firebase.FirestoreService
import com.example.myapplication.model.Item
import com.example.myapplication.model.Purchase
import com.example.myapplication.network.RetrofitClient
import com.example.myapplication.utils.NetworkUtils
import kotlinx.coroutines.launch

class ItemsFragment : Fragment() {
    private var _binding: FragmentItemsBinding? = null
    private val binding get() = _binding!!
    private lateinit var itemsAdapter: ItemsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        loadItems()
    }

    private fun setupRecyclerView() {
        itemsAdapter = ItemsAdapter { item ->
            handleItemClick(item)
        }

        binding.recyclerViewItems.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = itemsAdapter
        }
    }

    private fun loadItems() {
        // Verificar conectividade antes de fazer a requisição
        if (!NetworkUtils.isNetworkAvailable(requireContext())) {
            showError("Sem conexão com a internet")
            return
        }

        _binding?.let { binding ->
            binding.progressBarItems.visibility = View.VISIBLE
            binding.textViewEmptyItems.visibility = View.GONE
        }

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.apiService.getData()
                if (response.isSuccessful) {
                    // The body is now the list of items directly
                    val items = response.body()
                    _binding?.let { binding ->
                        if (!items.isNullOrEmpty()) {
                            itemsAdapter.submitList(items)
                            binding.recyclerViewItems.visibility = View.VISIBLE
                        } else {
                            showEmptyState()
                        }
                    }
                } else {
                    showError("Erro ao carregar itens: ${response.message()}")
                }
            } catch (e: Exception) {
                val errorMessage = NetworkUtils.getErrorMessage(e)
                if (errorMessage != "Operação cancelada") {
                    showError(errorMessage)
                }
            } finally {
                _binding?.let { binding ->
                    binding.progressBarItems.visibility = View.GONE
                }
            }
        }
    }

    private fun handleItemClick(item: Item) {
        if (!FirebaseAuthService.isUserLoggedIn()) {
            showLoginRequiredDialog()
        } else {
            showPurchaseConfirmationDialog(item)
        }
    }

    private fun showLoginRequiredDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Login Necessário")
            .setMessage("Você precisa fazer login para comprar este item.")
            .setPositiveButton("OK") { _, _ -> }
            .show()
    }

    private fun showPurchaseConfirmationDialog(item: Item) {
        AlertDialog.Builder(requireContext())
            .setTitle("Confirmar Compra")
            // Corrigido para usar item.preco
            .setMessage("Deseja comprar ${item.nome} por R$ ${String.format("%.2f", item.preco)}?")
            .setPositiveButton("Comprar") { _, _ ->
                processPurchase(item)
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun processPurchase(item: Item) {
        val userId = FirebaseAuthService.getUserId() ?: return

        val purchase = Purchase(
            itemId = item.id, // Corresponde ao tipo Int
            itemName = item.nome,
            itemValue = item.preco,      // Corrigido para usar item.preco
            itemPhoto = item.url_imagem, // Corrigido para usar item.urlImagem
            userId = userId
        )

        lifecycleScope.launch {
            try {
                val result = FirestoreService.savePurchase(purchase)
                if (result.isSuccess) {
                    showSuccessDialog("Compra realizada com sucesso!")
                } else {
                    showError("Erro ao processar compra: ${result.exceptionOrNull()?.message}")
                }
            } catch (e: Exception) {
                // Não mostrar erro se a coroutine foi cancelada (fragmento destruído)
                if (e.message?.contains("Job was cancelled") != true &&
                    e.message?.contains("cancelled") != true) {
                    showError("Erro ao processar compra: ${e.message}")
                }
            }
        }
    }

    private fun showSuccessDialog(message: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Sucesso")
            .setMessage(message)
            .setPositiveButton("OK") { _, _ -> }
            .show()
    }

    private fun showError(message: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Erro")
            .setMessage(message)
            .setPositiveButton("OK") { _, _ -> }
            .show()
    }

    private fun showEmptyState() {
        _binding?.let { binding ->
            binding.recyclerViewItems.visibility = View.GONE
            binding.textViewEmptyItems.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}