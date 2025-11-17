package com.example.mad_24172012057_assignment1.utils

import com.example.mad_24172012057_assignment1.models.Product
import com.google.firebase.firestore.FirebaseFirestore

object FirestoreRepo {
    private val db = FirebaseFirestore.getInstance()
    fun fetchProducts(onResult: (List<Product>) -> Unit) {
        db.collection("products").get().addOnSuccessListener { snap ->
            val list = snap.documents.map { d ->
                Product(
                    id = d.id,
                    name = d.getString("name") ?: "",
                    brand = d.getString("brand") ?: "",
                    price = d.getDouble("price") ?: 0.0,
                    imageUrl = d.getString("imageUrl") ?: ""
                )
            }
            onResult(list)
        }.addOnFailureListener { onResult(emptyList()) }
    }
    // add other wrappers for orders, carts, wishlist, admin actions...
}
