package com.cs4520.assignment4

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.cs4520.assignment4.databinding.FragmentProductsBinding

class ProductsFragment : Fragment() {
    private lateinit var binding: FragmentProductsBinding
    private lateinit var viewModel: ProductsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ProductsViewModel::class.java]
        viewModel.productsResult.observe(viewLifecycleOwner) { result ->
            binding.spinner.visibility = View.GONE
            when (result) {
                is Result.Error -> showToast(result.msg)
                is Result.Success -> {
                    binding.recyclerView.visibility = View.VISIBLE
                    binding.recyclerView.adapter = ProductAdapter(result.value)
                }
            }
        }

        binding.refreshButton.setOnClickListener {
            binding.recyclerView.visibility = View.GONE
            binding.spinner.visibility = View.VISIBLE
            viewModel.loadProducts()
        }

        binding.recyclerView.visibility = View.GONE
        binding.spinner.visibility = View.VISIBLE
        viewModel.loadProducts()
    }

    private fun showToast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }
}