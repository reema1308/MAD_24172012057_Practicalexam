package com.example.mad_24172012057_assignment1.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mad_24172012057_assignment1.R
import com.example.mad_24172012057_assignment1.adapters.OrderAdapter
import com.example.mad_24172012057_assignment1.models.Order

class OrdersFragment: Fragment() {
    private val orders = mutableListOf<Order>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_orders, container, false)
        val rv = v.findViewById<RecyclerView>(R.id.rvOrders)
        rv.layoutManager = LinearLayoutManager(requireContext())
        orders.add(Order("ORD1001","Reema","₹120","Delivered"))
        orders.add(Order("ORD1002","Amit","₹450","Processing"))
        rv.adapter = OrderAdapter(orders)
        return v
    }
}
