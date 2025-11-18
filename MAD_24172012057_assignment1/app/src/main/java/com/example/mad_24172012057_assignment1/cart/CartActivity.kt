package com.example.mad_24172012057_assignment1.cart

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mad_24172012057_assignment1.R
import com.example.mad_24172012057_assignment1.adapters.CartAdapter
import com.example.mad_24172012057_assignment1.utils.CartManager

class CartActivity : AppCompatActivity() {

    private lateinit var rv: RecyclerView
    private lateinit var tvTotal: TextView
    private lateinit var btnCheckout: Button
    private lateinit var btnClear: Button
    private lateinit var adapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        rv = findViewById(R.id.rvCart)
        tvTotal = findViewById(R.id.tvCartTotal)
        btnCheckout = findViewById(R.id.btnCheckout)
        btnClear = findViewById(R.id.btnClearCart)

        rv.layoutManager = LinearLayoutManager(this)

        adapter = CartAdapter(CartManager.getItems().toMutableList()) { refreshTotal() }
        rv.adapter = adapter

        refreshTotal()

        btnCheckout.setOnClickListener {
            if (CartManager.getItems().isEmpty()) {
                // nothing to checkout
                return@setOnClickListener
            }
            startActivity(Intent(this, CheckoutActivity::class.java))
        }

        btnClear.setOnClickListener {
            CartManager.clear()
            adapter.submitList(CartManager.getItems())
            refreshTotal()
        }
    }

    private fun refreshTotal() {
        val total = CartManager.total()
        tvTotal.text = "Total: ₹${"%.2f".format(total)}"
        // update adapter with fresh items (quantities likely changed)
        adapter.submitList(CartManager.getItems())
    }
}
