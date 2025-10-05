// file: adapters/ProductAdapter.kt

package com.example.shopapp.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.shopapp.R
import com.example.shopapp.databinding.ListItemProductBinding
import com.example.shopapp.models.Product
import java.text.NumberFormat
import java.util.*

class ProductAdapter(private val onEditClick: (Product) -> Unit) :
    ListAdapter<Product, ProductAdapter.ProductViewHolder>(ProductDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ListItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product)
    }

    inner class ProductViewHolder(private val binding: ListItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            // Set the edit click listener for the entire card
            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onEditClick(getItem(adapterPosition))
                }
            }
        }

        fun bind(product: Product) {
            binding.tvProductName.text = product.name
            binding.chipCategory.text = product.category.replaceFirstChar { it.titlecase() }

            // Load first image if available, otherwise show placeholder
            if (product.images.isNotEmpty()) {
                binding.ivProductImage.load(product.images[0]) {
                    crossfade(true)
                    placeholder(R.drawable.ic_placeholder) // Add a placeholder drawable
                }
            } else {
                binding.ivProductImage.setImageResource(R.drawable.ic_placeholder)
            }

            // Handle pricing and discount
            val currencyFormat = NumberFormat.getCurrencyInstance(Locale.US)
            if (product.discountPercentage > 0) {
                val discountedPrice = product.price * (1 - product.discountPercentage / 100.0)
                binding.tvProductPrice.text = currencyFormat.format(discountedPrice)
                binding.tvOriginalPrice.text = currencyFormat.format(product.price)
                binding.tvOriginalPrice.paintFlags = binding.tvOriginalPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                binding.tvOriginalPrice.visibility = View.VISIBLE

                binding.tvDiscount.text = "-${product.discountPercentage}%"
                binding.tvDiscount.visibility = View.VISIBLE
            } else {
                binding.tvProductPrice.text = currencyFormat.format(product.price)
                binding.tvOriginalPrice.visibility = View.GONE
                binding.tvDiscount.visibility = View.GONE
            }
        }
    }
}

class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }
}