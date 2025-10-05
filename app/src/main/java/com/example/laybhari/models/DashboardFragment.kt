// file: fragments/DashboardFragment.kt
package com.example.shopapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.shopapp.adapters.ProductAdapter
import com.example.shopapp.databinding.FragmentDashboardBinding
import com.example.shopapp.viewmodels.DashboardViewModel
import com.google.android.material.chip.Chip

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DashboardViewModel by viewModels()
    private lateinit var productAdapter: ProductAdapter

    private var currentCategory = "all"
    private var currentSearchQuery = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupFilterBar()
        setupObservers()

        binding.fabAddProduct.setOnClickListener {
            // TODO: Navigate to ProductFormFragment to add a new product
        }
    }

    private fun setupRecyclerView() {
        productAdapter = ProductAdapter { product ->
            // TODO: Navigate to ProductFormFragment with the product to edit
        }
        binding.recyclerViewProducts.adapter = productAdapter
    }

    private fun setupFilterBar() {
        // Add category chips
        val categories = listOf("all", "shirts", "pants", "jackets", "suits", "accessories", "shoes")
        categories.forEach { category ->
            val chip = Chip(requireContext()).apply {
                text = category.replaceFirstChar { it.titlecase() }
                isCheckable = true
                isChecked = category == currentCategory
            }
            chip.setOnCheckedChangeListener { _, _ ->
                currentCategory = category
                viewModel.filterProducts(currentSearchQuery, currentCategory)
            }
            binding.chipGroupCategory.addView(chip)
        }

        // Search listener
        binding.etSearch.doOnTextChanged { text, _, _, _ ->
            currentSearchQuery = text.toString()
            viewModel.filterProducts(currentSearchQuery, currentCategory)
        }
    }

    private fun setupObservers() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.isVisible = isLoading
            binding.recyclerViewProducts.isVisible = !isLoading
        }

        viewModel.filteredProducts.observe(viewLifecycleOwner) { products ->
            productAdapter.submitList(products)
            binding.layoutEmpty.isVisible = products.isEmpty()
            binding.recyclerViewProducts.isVisible = products.isNotEmpty()

            // Update stats
            binding.tvTotalProducts.text = "Total: ${products.size}"
            binding.tvFeatured.text = "Featured: ${products.count { it.isFeatured }}"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}