package com.example.mad_24172012057_assignment1.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mad_24172012057_assignment1.R
import com.example.mad_24172012057_assignment1.utils.CartManager

class CartAdapter(
    private var items: MutableList<CartManager.CartItem>,
    private val onChange: () -> Unit
) : RecyclerView.Adapter<CartAdapter.VH>() {

    inner class VH(v: View) : RecyclerView.ViewHolder(v) {
        val img: ImageView = v.findViewById(R.id.imgProduct)
        val name: TextView = v.findViewById(R.id.txtName)
        val brand: TextView = v.findViewById(R.id.txtBrand)
        val price: TextView = v.findViewById(R.id.txtPrice)
        val btnInc: ImageButton = v.findViewById(R.id.btnInc)
        val btnDec: ImageButton = v.findViewById(R.id.btnDec)
        val tvQty: TextView = v.findViewById(R.id.tvQty)
        val btnRemove: ImageButton = v.findViewById(R.id.btnRemove)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_cart_product, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val ci = items[position]
        holder.name.text = ci.product.name
        holder.brand.text = ci.product.brand
        holder.price.text = "₹${ci.product.price}"
        holder.tvQty.text = ci.quantity.toString()
        holder.img.load(ci.product.imageUrl) {
            placeholder(R.drawable.ic_placeholder)
            error(R.drawable.ic_placeholder)
        }

        holder.btnInc.setOnClickListener {
            CartManager.updateQuantity(ci.product, ci.quantity + 1)
            ci.quantity += 1
            holder.tvQty.text = ci.quantity.toString()
            onChange()
        }
        holder.btnDec.setOnClickListener {
            val newQ = ci.quantity - 1
            if (newQ <= 0) {
                // remove
                CartManager.remove(ci.product)
                items.removeAt(position)
                notifyItemRemoved(position)
            } else {
                CartManager.updateQuantity(ci.product, newQ)
                ci.quantity = newQ
                holder.tvQty.text = ci.quantity.toString()
                notifyItemChanged(position)
            }
            onChange()
        }

        holder.btnRemove.setOnClickListener {
            CartManager.remove(ci.product)
            items.removeAt(position)
            notifyItemRemoved(position)
            onChange()
        }
    }

    override fun getItemCount(): Int = items.size

    fun submitList(newList: List<CartManager.CartItem>) {
        items = newList.toMutableList()
        notifyDataSetChanged()
    }
}
