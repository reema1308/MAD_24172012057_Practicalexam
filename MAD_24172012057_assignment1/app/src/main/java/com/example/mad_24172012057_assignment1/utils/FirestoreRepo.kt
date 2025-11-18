package com.example.mad_24172012057_assignment1.utils

import com.example.mad_24172012057_assignment1.models.Product
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.tasks.await

object FirestoreRepo {
    private val db = FirebaseFirestore.getInstance()

    private fun Any?.toSafeDouble(): Double {
        return when (this) {
            is Double -> this
            is Long -> this.toDouble()
            is Int -> this.toDouble()
            is Number -> this.toDouble()
            is String -> this.toDoubleOrNull() ?: 0.0
            else -> 0.0
        }
    }

    fun fetchProducts(onResult: (Result<List<Product>>) -> Unit) {
        db.collection("products").get()
            .addOnSuccessListener { snap ->
                try {
                    val list = snap.documents.mapNotNull { d ->
                        Product(
                            id = d.id,
                            name = d.getString("name") ?: "",
                            brand = d.getString("brand") ?: "",
                            price = d.get("price").toSafeDouble(),
                            imageUrl = d.getString("imageUrl") ?: ""
                        )
                    }
                    onResult(Result.success(list))
                } catch (e: Exception) {
                    onResult(Result.failure(e))
                }
            }
            .addOnFailureListener { e -> onResult(Result.failure(e)) }
    }

    suspend fun fetchProductsSuspend(): List<Product> {
        val snap = db.collection("products").get().await()
        return snap.documents.mapNotNull { d ->
            Product(
                id = d.id,
                name = d.getString("name") ?: "",
                brand = d.getString("brand") ?: "",
                price = d.get("price").toSafeDouble(),
                imageUrl = d.getString("imageUrl") ?: ""
            )
        }
    }

    fun fetchProductById(id: String, onResult: (Result<Product?>) -> Unit) {
        db.collection("products").document(id).get()
            .addOnSuccessListener { doc ->
                if (doc.exists()) {
                    val p = Product(
                        id = doc.id,
                        name = doc.getString("name") ?: "",
                        brand = doc.getString("brand") ?: "",
                        price = doc.get("price").toSafeDouble(),
                        imageUrl = doc.getString("imageUrl") ?: ""
                    )
                    onResult(Result.success(p))
                } else onResult(Result.success(null))
            }
            .addOnFailureListener { e -> onResult(Result.failure(e)) }
    }

    fun listenProductsRealtime(onUpdate: (List<Product>) -> Unit): ListenerRegistration {
        return db.collection("products").addSnapshotListener { snapshot, error ->
            if (error != null) return@addSnapshotListener
            if (snapshot == null) return@addSnapshotListener
            val list = snapshot.documents.mapNotNull { d ->
                Product(
                    id = d.id,
                    name = d.getString("name") ?: "",
                    brand = d.getString("brand") ?: "",
                    price = d.get("price").toSafeDouble(),
                    imageUrl = d.getString("imageUrl") ?: ""
                )
            }
            onUpdate(list)
        }
    }
}
