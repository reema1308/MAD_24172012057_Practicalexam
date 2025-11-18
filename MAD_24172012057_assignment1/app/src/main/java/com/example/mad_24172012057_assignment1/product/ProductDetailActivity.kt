package com.example.mad_24172012057_assignment1.product

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.mad_24172012057_assignment1.R
import com.example.mad_24172012057_assignment1.models.Product
import com.example.mad_24172012057_assignment1.utils.CartManager
import com.google.android.material.snackbar.Snackbar

class ProductDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        val product = intent.getParcelableExtra<Product>("product")
        if (product == null) {
            Snackbar.make(findViewById(android.R.id.content), "Product not found", Snackbar.LENGTH_LONG).show()
            finish()
            return
        }

        val img = findViewById<ImageView>(R.id.imgDetail)
        val tvName = findViewById<TextView>(R.id.tvDetailName)
        val tvBrand = findViewById<TextView>(R.id.tvDetailBrand)
        val tvPrice = findViewById<TextView>(R.id.tvDetailPrice)
        val btnAdd = findViewById<Button>(R.id.btnAddToCart)

        img.load(product.imageUrl) {
            placeholder(R.drawable.ic_placeholder)
            error(R.drawable.ic_placeholder)
        }

        tvName.text = product.name
        tvBrand.text = product.brand
        tvPrice.text = "₹${product.price}"

        btnAdd.setOnClickListener { v ->
            val added = CartManager.addToCart(product)
            if (added) {
                Snackbar.make(v, "${product.name} added to cart 🛒", Snackbar.LENGTH_SHORT).show()
            } else {
                Snackbar.make(v, "${product.name} quantity increased in cart", Snackbar.LENGTH_SHORT).show()
            }
            btnAdd.text = "Added"
            btnAdd.isEnabled = false
        }
    }
}
