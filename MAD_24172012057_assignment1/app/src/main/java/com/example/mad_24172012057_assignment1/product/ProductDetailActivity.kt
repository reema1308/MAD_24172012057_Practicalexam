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

class ProductDetailActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)
        val product = intent.getParcelableExtra<Product>("product") // or pass id and load from Firestore
        val img = findViewById<ImageView>(R.id.imgDetail)
        val tvName = findViewById<TextView>(R.id.tvDetailName)
        val tvBrand = findViewById<TextView>(R.id.tvDetailBrand)
        val tvPrice = findViewById<TextView>(R.id.tvDetailPrice)
        val btnAdd = findViewById<Button>(R.id.btnAddToCart)
        product?.let {
            img.load(it.imageUrl)
            tvName.text = it.name
            tvBrand.text = it.brand
            tvPrice.text = "₹${it.price}"
            btnAdd.setOnClickListener { v ->
                val added = CartManager.addToCart(it)
                if (added) Snackbar.make(v, "${it.name} added to cart 🛒", Snackbar.LENGTH_SHORT).show()
                else Snackbar.make(v, "${it.name} is already in cart!", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}
