// file: viewmodels/DashboardViewModel.kt
package com.example.shopapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shopapp.models.Product

class DashboardViewModel : ViewModel() {

    // This would be your full product list, fetched from a database or API
    private val _allProducts = MutableLiveData<List<Product>>()

    // The UI observes this LiveData for the filtered list
    private val _filteredProducts = MutableLiveData<List<Product>>()
    val filteredProducts: LiveData<List<Product>> = _filteredProducts

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        loadProducts()
    }

    private fun loadProducts() {
        _isLoading.value = true
        // Simulate a network/database call
        // In a real app, you'd use something like Retrofit or Room here.
        val sampleProducts = listOf(
            Product("1", "Classic Oxford Shirt", "A timeless classic for any occasion.", "shirts", 59.99, 10, listOf("https://images.unsplash.com/photo-1598032895397-b9472444bf93?w=500"), stock = 50),
            Product("2", "Slim-Fit Chinos", "Comfortable and stylish pants.", "pants", 79.50, 0, listOf("https://images.unsplash.com/photo-1594633312681-425c7b97ccd1?w=500"), stock = 30, isFeatured = true),
            Product("3", "Denim Jacket", "A rugged and versatile jacket.", "jackets", 120.0, 25, listOf("https://images.unsplash.com/photo-1543087904-142c7929def4?w=500"), stock = 15)
        )
        _allProducts.postValue(sampleProducts)
        _filteredProducts.postValue(sampleProducts) // Initially show all
        _isLoading.postValue(false)
    }

    fun filterProducts(query: String, category: String) {
        val all = _allProducts.value ?: return

        val filtered = all.filter { product ->
            val matchesCategory = category == "all" || product.category.equals(category, ignoreCase = true)
            val matchesSearch = product.name.contains(query, ignoreCase = true) ||
                    product.description?.contains(query, ignoreCase = true) == true
            matchesCategory && matchesSearch
        }
        _filteredProducts.value = filtered
    }
}