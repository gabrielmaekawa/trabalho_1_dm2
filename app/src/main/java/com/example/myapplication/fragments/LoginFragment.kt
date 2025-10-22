package com.example.myapplication.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val auth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        checkLoginStatus()
    }

    private fun setupUI() {
        binding.buttonLogin.setOnClickListener {
            val email = binding.editTextEmail.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()
            
            if (validateInput(email, password)) {
                loginUser(email, password)
            }
        }

        binding.buttonRegister.setOnClickListener {
            val email = binding.editTextEmail.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()
            
            if (validateInput(email, password)) {
                registerUser(email, password)
            }
        }

        binding.buttonLogout.setOnClickListener {
            logoutUser()
        }
    }

    private fun validateInput(email: String, password: String): Boolean {
        if (TextUtils.isEmpty(email)) {
            binding.textInputLayoutEmail.error = "Email é obrigatório"
            return false
        }
        
        if (TextUtils.isEmpty(password)) {
            binding.textInputLayoutPassword.error = "Senha é obrigatória"
            return false
        }
        
        if (password.length < 6) {
            binding.textInputLayoutPassword.error = "Senha deve ter pelo menos 6 caracteres"
            return false
        }

        binding.textInputLayoutEmail.error = null
        binding.textInputLayoutPassword.error = null
        return true
    }

    private fun loginUser(email: String, password: String) {
        showLoading(true)
        
        lifecycleScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password).await()
                showStatus("Login realizado com sucesso!")
                updateUIForLoggedInUser()
            } catch (e: Exception) {
                val errorMessage = when {
                    e.message?.contains("API key not valid") == true -> 
                        "API key inválida. Verifique a configuração do Firebase."
                    e.message?.contains("API_KEY_NOT_VALID") == true -> 
                        "API key inválida. Verifique a configuração do Firebase."
                    e.message?.contains("INVALID_EMAIL") == true -> 
                        "Email inválido."
                    e.message?.contains("USER_NOT_FOUND") == true -> 
                        "Usuário não encontrado. Crie uma conta primeiro."
                    e.message?.contains("WRONG_PASSWORD") == true -> 
                        "Senha incorreta."
                    e.message?.contains("WEAK_PASSWORD") == true -> 
                        "Senha muito fraca. Use pelo menos 6 caracteres."
                    e.message?.contains("EMAIL_ALREADY_IN_USE") == true -> 
                        "Este email já está em uso."
                    e.message?.contains("NETWORK_ERROR") == true -> 
                        "Erro de rede. Verifique sua conexão."
                    else -> "Erro no login: ${e.message}"
                }
                showStatus(errorMessage)
            } finally {
                showLoading(false)
            }
        }
    }

    private fun registerUser(email: String, password: String) {
        showLoading(true)
        
        lifecycleScope.launch {
            try {
                auth.createUserWithEmailAndPassword(email, password).await()
                showStatus("Conta criada com sucesso!")
                updateUIForLoggedInUser()
            } catch (e: Exception) {
                val errorMessage = when {
                    e.message?.contains("API key not valid") == true -> 
                        "API key inválida. Verifique a configuração do Firebase."
                    e.message?.contains("API_KEY_NOT_VALID") == true -> 
                        "API key inválida. Verifique a configuração do Firebase."
                    e.message?.contains("INVALID_EMAIL") == true -> 
                        "Email inválido."
                    e.message?.contains("EMAIL_ALREADY_IN_USE") == true -> 
                        "Este email já está em uso."
                    e.message?.contains("WEAK_PASSWORD") == true -> 
                        "Senha muito fraca. Use pelo menos 6 caracteres."
                    e.message?.contains("NETWORK_ERROR") == true -> 
                        "Erro de rede. Verifique sua conexão."
                    else -> "Erro ao criar conta: ${e.message}"
                }
                showStatus(errorMessage)
            } finally {
                showLoading(false)
            }
        }
    }

    private fun logoutUser() {
        auth.signOut()
        showStatus("Logout realizado com sucesso!")
        updateUIForLoggedOutUser()
    }

    private fun checkLoginStatus() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            updateUIForLoggedInUser()
        } else {
            updateUIForLoggedOutUser()
        }
    }

    private fun updateUIForLoggedInUser() {
        val user = auth.currentUser
        binding.apply {
            buttonLogin.visibility = View.GONE
            buttonRegister.visibility = View.GONE
            buttonLogout.visibility = View.VISIBLE
            textInputLayoutEmail.visibility = View.GONE
            textInputLayoutPassword.visibility = View.GONE
            textViewStatus.text = "Logado como: ${user?.email}"
        }
    }

    private fun updateUIForLoggedOutUser() {
        binding.apply {
            buttonLogin.visibility = View.VISIBLE
            buttonRegister.visibility = View.VISIBLE
            buttonLogout.visibility = View.GONE
            textInputLayoutEmail.visibility = View.VISIBLE
            textInputLayoutPassword.visibility = View.VISIBLE
            textViewStatus.text = ""
            editTextEmail.setText("")
            editTextPassword.setText("")
        }
    }

    private fun showLoading(show: Boolean) {
        binding.progressBarLogin.visibility = if (show) View.VISIBLE else View.GONE
        binding.buttonLogin.isEnabled = !show
        binding.buttonRegister.isEnabled = !show
    }

    private fun showStatus(message: String) {
        binding.textViewStatus.text = message
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
