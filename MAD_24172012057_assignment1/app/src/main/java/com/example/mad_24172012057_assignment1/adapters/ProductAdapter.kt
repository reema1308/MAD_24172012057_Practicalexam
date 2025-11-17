package com.example.mad_24172012057_assignment1.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mad_24172012057_assignment1.R
import com.example.mad_24172012057_assignment1.models.Product
import com.example.mad_24172012057_assignment1.utils.CartManager
import com.google.android.material.snackbar.Snackbar

class ProductAdapter(private val items: List<Product>, private val rootView: View)
    : RecyclerView.Adapter<ProductAdapter.VH>() {
    inner class VH(v: View): RecyclerView.ViewHolder(v) {
        val img: ImageView = v.findViewById(R.id.img)
        val name: TextView = v.findViewById(R.id.tvName)
        val brand: TextView = v.findViewById(R.id.tvBrand)
        val price: TextView = v.findViewById(R.id.tvPrice)
        val btn: Button = v.findViewById(R.id.btnAdd)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(LayoutInflater.from(parent.context).inflate(R.layout.item_product_card, parent, false))
    }
    override fun onBindViewHolder(holder: VH, position: Int) {
        val p = items[position]
        holder.name.text = p.name
        holder.brand.text = p.brand
        holder.price.text = "₹${p.price}"
        holder.img.load(p.imageUrl) { placeholder(R.drawable.ic_placeholder) }
        holder.btn.setOnClickListener { v ->
            val added = CartManager.addToCart(p)
            if (added) Snackbar.make(rootView, "${p.name} added to cart 🛒", Snackbar.LENGTH_SHORT).show()
            else Snackbar.make(rootView, "${p.name} is already in cart!", Snackbar.LENGTH_SHORT).show()
        }
        holder.itemView.setOnClickListener {
            // open ProductDetailActivity
        }
    }
    override fun getItemCount() = items.size
}
