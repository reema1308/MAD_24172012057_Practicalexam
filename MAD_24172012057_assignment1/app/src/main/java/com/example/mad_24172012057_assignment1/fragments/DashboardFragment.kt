package com.example.mad_24172012057_assignment1.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mad_24172012057_assignment1.R
import com.example.mad_24172012057_assignment1.adapters.ProductAdapter
import com.example.mad_24172012057_assignment1.models.Product
import com.example.mad_24172012057_assignment1.utils.CartManager

class DashboardFragment: Fragment() {
    private val sample = mutableListOf<Product>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_dashboard, container, false)
        val rv = v.findViewById<RecyclerView>(R.id.rv_featured)
        val tvOrders = v.findViewById<TextView>(R.id.tv_total_orders)
        val tvProducts = v.findViewById<TextView>(R.id.tv_total_products)
        val tvCustomers = v.findViewById<TextView>(R.id.tv_total_customers)
        val tvSales = v.findViewById<TextView>(R.id.tv_total_sales)

        loadSample()
        tvOrders.text = "715"
        tvProducts.text = "${sample.size}"
        tvCustomers.text = "2K"
        tvSales.text = "$8,450"

        rv.layoutManager = GridLayoutManager(requireContext(), 2)
        rv.adapter = ProductAdapter(sample, requireActivity().findViewById(android.R.id.content))
        return v
    }
    private fun loadSample() {
        sample.clear()
        sample.add(Product("1","Paracetamol","Cipla",20.0,"https://i.imgur.com/abcd.png"))
        sample.add(Product("2","Amoxicillin","Sun Pharma",45.0,"https://i.imgur.com/efgh.png"))
        sample.add(Product("3","Vitamin C","Himalaya",30.0,"https://i.imgur.com/ijkl.png"))
        // add more or load from Firestore
    }
}
