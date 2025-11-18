package com.example.mad_24172012057_assignment1.utils

import com.example.mad_24172012057_assignment1.models.Product

object CartManager {

    data class CartItem(val product: Product, var quantity: Int = 1)

    private val items = mutableMapOf<String, CartItem>()

    /**
     * Returns true if item was newly added, false if already existed (quantity incremented).
     */
    fun addToCart(p: Product): Boolean {
        val id = p.id.ifEmpty { return false }
        val existing = items[id]
        return if (existing != null) {
            existing.quantity += 1
            false
        } else {
            items[id] = CartItem(p, 1)
            true
        }
    }

    fun remove(p: Product) {
        items.remove(p.id)
    }

    fun updateQuantity(p: Product, qty: Int) {
        if (qty <= 0) items.remove(p.id) else items[p.id]?.quantity = qty
    }

    fun getItems(): List<CartItem> = items.values.toList()

    fun getItemCount(): Int = items.values.sumOf { it.quantity }

    fun total(): Double = items.values.sumOf { it.product.price * it.quantity }

    fun clear() = items.clear()
}
