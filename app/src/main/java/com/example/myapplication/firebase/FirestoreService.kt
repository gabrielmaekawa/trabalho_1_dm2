package com.example.myapplication.firebase

import android.util.Log
import com.example.myapplication.model.Purchase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.util.Date

object FirestoreService {
    private val db = FirebaseFirestore.getInstance()
    private const val PURCHASES_COLLECTION = "purchases"
    
    suspend fun savePurchase(purchase: Purchase): Result<Unit> {
        return try {
            val userId = FirebaseAuthService.getUserId()
            if (userId == null) {
                Result.failure(Exception("Usuário não está logado"))
            } else {
                val userPurchasesRef = db.collection(PURCHASES_COLLECTION)
                    .document(userId)
                    .collection("user_purchases")
                
                // Criar um Map com os dados da compra para salvar no Firestore
                val purchaseData = mapOf(
                    "itemId" to purchase.itemId,
                    "itemName" to purchase.itemName,
                    "itemValue" to purchase.itemValue,
                    "itemPhoto" to purchase.itemPhoto,
                    "purchaseDate" to purchase.timestamp?.let { com.google.firebase.Timestamp(it) },
                    "userId" to purchase.userId
                )
                
                Log.d("FirestoreService", "Salvando compra: $purchaseData")
                userPurchasesRef.add(purchaseData).await()
                Log.d("FirestoreService", "Compra salva com sucesso!")
                Result.success(Unit)
            }
        } catch (e: Exception) {
            val errorMessage = when {
                e.message?.contains("PERMISSION_DENIED") == true -> 
                    "Permissão negada. Verifique as regras do Firestore."
                e.message?.contains("UNAVAILABLE") == true -> 
                    "Firebase indisponível. Verifique sua conexão."
                else -> "Erro ao salvar compra: ${e.message}"
            }
            Result.failure(Exception(errorMessage))
        }
    }
    
    suspend fun getUserPurchases(): Result<List<Purchase>> {
        return try {
            val userId = FirebaseAuthService.getUserId()
            if (userId == null) {
                Result.failure(Exception("Usuário não está logado"))
            } else {
                val userPurchasesRef = db.collection(PURCHASES_COLLECTION)
                    .document(userId)
                    .collection("user_purchases")
                
                val snapshot = userPurchasesRef.get().await()
                Log.d("FirestoreService", "Documentos encontrados: ${snapshot.documents.size}")
                
                val purchases = snapshot.documents.mapNotNull { document ->
                    try {
                        val data = document.data
                        Log.d("FirestoreService", "Dados do documento: $data")
                        
                        val purchase = Purchase(
                            itemId = data?.get("itemId") as? Int ?: 0,
                            itemName = data?.get("itemName") as? String ?: "",
                            itemValue = (data?.get("itemValue") as? Number)?.toDouble() ?: 0.0,
                            itemPhoto = data?.get("itemPhoto") as? String ?: "",
                            timestamp = (data?.get("purchaseDate") as? com.google.firebase.Timestamp)?.toDate() ?: Date(),
                            userId = data?.get("userId") as? String ?: ""
                        )
                        Log.d("FirestoreService", "Purchase criada: $purchase")
                        purchase
                    } catch (e: Exception) {
                        Log.e("FirestoreService", "Erro ao mapear documento: ${e.message}")
                        null
                    }
                }
                Result.success(purchases)
            }
        } catch (e: Exception) {
            val errorMessage = when {
                e.message?.contains("PERMISSION_DENIED") == true -> 
                    "Permissão negada. Verifique as regras do Firestore."
                e.message?.contains("UNAVAILABLE") == true -> 
                    "Firebase indisponível. Verifique sua conexão."
                else -> "Erro ao carregar compras: ${e.message}"
            }
            Result.failure(Exception(errorMessage))
        }
    }
}
