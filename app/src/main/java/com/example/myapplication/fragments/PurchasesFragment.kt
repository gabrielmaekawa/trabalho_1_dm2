package com.example.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.R
import com.example.myapplication.adapter.PurchaseListAdapter
import com.example.myapplication.databinding.FragmentPurchasesBinding
import com.example.myapplication.firebase.FirebaseAuthService
import com.example.myapplication.firebase.FirestoreService
import com.example.myapplication.model.Purchase
import kotlinx.coroutines.launch

class PurchasesFragment : Fragment() {
    private var _binding: FragmentPurchasesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPurchasesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadPurchases()
    }

    private fun loadPurchases() {
        if (!FirebaseAuthService.isUserLoggedIn()) {
            showLoginRequired()
            return
        }

        _binding?.let { binding ->
            binding.progressBarPurchases.visibility = View.VISIBLE
            binding.textViewLoginRequired.visibility = View.GONE
            binding.textViewEmptyPurchases.visibility = View.GONE
        }
        
        lifecycleScope.launch {
            try {
                val result = FirestoreService.getUserPurchases()
                if (result.isSuccess) {
                    val purchases = result.getOrNull() ?: emptyList()
                    _binding?.let { binding ->
                        if (purchases.isNotEmpty()) {
                            setupPurchasesList(purchases)
                            binding.listViewPurchases.visibility = View.VISIBLE
                        } else {
                            showEmptyPurchases()
                        }
                    }
                } else {
                    showError("Erro ao carregar compras: ${result.exceptionOrNull()?.message}")
                }
            } catch (e: Exception) {
                // Não mostrar erro se a coroutine foi cancelada (fragmento destruído)
                if (e.message?.contains("Job was cancelled") != true && 
                    e.message?.contains("cancelled") != true) {
                    showError("Erro de conexão: ${e.message}")
                }
            } finally {
                _binding?.let { binding ->
                    binding.progressBarPurchases.visibility = View.GONE
                }
            }
        }
    }

    private fun setupPurchasesList(purchases: List<Purchase>) {
        _binding?.let { binding ->
            val adapter = PurchaseListAdapter(requireContext(), purchases)
            binding.listViewPurchases.adapter = adapter
        }
    }

    private fun showLoginRequired() {
        _binding?.let { binding ->
            binding.listViewPurchases.visibility = View.GONE
            binding.textViewEmptyPurchases.visibility = View.GONE
            binding.textViewLoginRequired.visibility = View.VISIBLE
        }
    }

    private fun showEmptyPurchases() {
        _binding?.let { binding ->
            binding.listViewPurchases.visibility = View.GONE
            binding.textViewLoginRequired.visibility = View.GONE
            binding.textViewEmptyPurchases.visibility = View.VISIBLE
        }
    }

    private fun showError(message: String) {
        android.widget.Toast.makeText(context, message, android.widget.Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
