// file: models/Product.kt

package com.example.shopapp.models

import android.os.Parcelable
import kotlinx.coroutines.CoroutineName.Parcelize

sealed class
data class Product(
    val id: String = java.util.UUID.randomUUID().toString(), // Auto-generate ID
    var name: String,
    var description: String?,
    var category: String,
    var price: Double,
    var discountPercentage: Int = 0,
    var images: List<String> = emptyList(), // URLs to images
    var sizes: List<String> = emptyList(),
    var stock: Int = 0,
    var isFeatured: Boolean = false
) : Parcelable