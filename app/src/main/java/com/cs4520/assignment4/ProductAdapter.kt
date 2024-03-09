package com.cs4520.assignment4

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.cs4520.assignment4.databinding.ProductItemBinding

class ProductAdapter(private val products: List<Product>) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ProductItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]
        val binding = holder.binding

        binding.productName.text = product.name
        binding.productPrice.text = product.price.toString()
        when (product.type) {
            Product.Type.Food -> {
                binding.productImage.setImageResource(R.drawable.food)
                binding.root.setBackgroundColor(
                    ContextCompat.getColor(
                        holder.itemView.context,
                        R.color.food_product_background
                    )
                )
            }

            Product.Type.Equipment -> {
                binding.productImage.setImageResource(R.drawable.equipment)
                binding.root.setBackgroundColor(
                    ContextCompat.getColor(
                        holder.itemView.context,
                        R.color.equipment_product_background
                    )
                )
            }
        }
        if (product.expiryDate == null) {
            binding.productExpiryDate.visibility = View.GONE
        } else {
            binding.productExpiryDate.visibility = View.VISIBLE
            binding.productExpiryDate.text = product.expiryDate
        }
    }
}