package com.cs4520.assignment4

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Response

class ProductsViewModel : ViewModel() {
    private val _productsResult = MutableLiveData<Result<List<Product>>>()
    val productsResult: LiveData<Result<List<Product>>> = _productsResult

    fun loadProducts() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = Api.productService.getProducts()
                if (response.isSuccessful) {
                    handleSuccessfulResponse(response)
                } else {
                    handleFailedResponse(response)
                }
            } catch (e: Exception) {
                updateProductsResult(Result.Error("Offline, failed to load products"))
            }
        }
    }

    private suspend fun handleSuccessfulResponse(response: Response<List<Product>>) {
        val products = response.body()!!
        if (products.isEmpty()) {
            updateProductsResult(Result.Error("No products available"))
        } else {
            updateProductsResult(Result.Success(products))
        }
    }

    private suspend fun handleFailedResponse(response: Response<List<Product>>) {
        val errorBody = JSONObject(response.errorBody()!!.string())
        val errorMessage = errorBody.getString("message")
        updateProductsResult(Result.Error(errorMessage))
    }

    private suspend fun updateProductsResult(result: Result<List<Product>>) {
        withContext(Dispatchers.Main) {
            _productsResult.value = result
        }
    }
}