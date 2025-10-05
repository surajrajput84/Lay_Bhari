package com.example.laybhari // <-- Change this to your actual package name

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.laybhari.adapters.CategoryAdapter
import com.example.laybhari.adapters.ProductAdapter
import com.example.laybhari.models.Category
import com.example.laybhari.models.Product

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupCategories()
        setupProducts()
    }

    private fun setupCategories() {
        val recyclerViewCategories = findViewById<RecyclerView>(R.id.categoriesRecyclerView)

        // This line makes the category list HORIZONTAL
        recyclerViewCategories.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val categories = listOf(
            Category("Shirt", R.drawable.ic_category_shirt),
            Category("Pants", R.drawable.ic_category_pants),
            Category("Jacket", R.drawable.ic_category_jacket),
            Category("Shorts", R.drawable.ic_category_shorts),
            Category("T-shirt", R.drawable.ic_category_tshirt)
        )

        recyclerViewCategories.adapter = CategoryAdapter(categories)
    }

    private fun setupProducts() {
        val recyclerViewProducts = findViewById<RecyclerView>(R.id.productsRecyclerView)

        // This line makes the product list a 2-COLUMN GRID
        recyclerViewProducts.layoutManager = GridLayoutManager(this, 2)
        recyclerViewProducts.isNestedScrollingEnabled = false

        val products = listOf(
            Product("Mens Shirt", "Rs. 1000", R.drawable.product_1),
            Product("Trouser", "Rs. 3000", R.drawable.product_2),
            Product("Mens T-Shirt", "Rs. 1800", R.drawable.product_3),
            Product("Full shirt", "Rs. 3000", R.drawable.product_4)
        )

        recyclerViewProducts.adapter = ProductAdapter(products)
    }
}