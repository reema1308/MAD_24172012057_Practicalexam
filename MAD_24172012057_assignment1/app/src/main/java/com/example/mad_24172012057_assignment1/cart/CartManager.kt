package com.example.mad_24172012057_assignment1.cart

object CartManager {
    private val cartItems = mutableListOf<Map<String, Any>>()

    fun addItem(item: Map<String, Any>) {
        cartItems.add(item)
    }

    fun getItems(): List<Map<String, Any>> = cartItems

    fun total(): Double {
        return cartItems.sumOf { (it["price"] as? Double ?: 0.0) * (it["quantity"] as? Int ?: 1) }
    }

    fun clear() {
        cartItems.clear()
    }
}
