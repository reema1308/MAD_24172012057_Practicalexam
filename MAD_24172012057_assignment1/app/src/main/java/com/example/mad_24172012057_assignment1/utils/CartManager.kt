package com.example.mad_24172012057_assignment1.utils

import com.example.mad_24172012057_assignment1.models.Product

object CartManager {
    private val items = mutableListOf<Pair<Product, Int>>()

    fun addToCart(p: Product): Boolean {
        val idx = items.indexOfFirst { it.first.id == p.id }
        if (idx >= 0) {
            items[idx] = items[idx].first to (items[idx].second + 1)
            return true
        } else {
            items.add(p to 1)
            return true
        }
    }
    fun remove(p: Product) { items.removeAll { it.first.id == p.id } }
    fun list() = items.toList()
    fun clear() = items.clear()
    fun total() = items.sumOf { it.first.price * it.second }
}
