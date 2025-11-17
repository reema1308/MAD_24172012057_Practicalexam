package com.example.mad_24172012057_assignment1.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mad_24172012057_assignment1.R
import com.example.mad_24172012057_assignment1.adapters.ProductAdapter
import com.example.mad_24172012057_assignment1.models.Product

class ProductsFragment: Fragment() {
    private val products = mutableListOf<Product>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_products, container, false)
        val rv = v.findViewById<RecyclerView>(R.id.rvProducts)
        rv.layoutManager = GridLayoutManager(requireContext(), 2)
        products.add(Product("1","Paracetamol","Cipla",20.0,"https://i.imgur.com/abcd.png"))
        products.add(Product("2","Amoxicillin","Sun Pharma",45.0,"https://i.imgur.com/efgh.png"))
        rv.adapter = ProductAdapter(products, requireActivity().findViewById(android.R.id.content))
        return v
    }
}
