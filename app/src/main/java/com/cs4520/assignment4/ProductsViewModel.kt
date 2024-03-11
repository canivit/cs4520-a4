package com.cs4520.assignment4

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cs4520.assignment4.Api.ProductApiClient
import com.cs4520.assignment4.Database.DatabaseHandle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Response

class ProductsViewModel(
    private val apiClient: ProductApiClient,
    private val databaseHandle: DatabaseHandle
) : ViewModel() {
    private val _productsResult = MutableLiveData<Result<List<Product>>>()
    val productsResult: LiveData<Result<List<Product>>> = _productsResult

    fun loadProducts() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiClient.getProducts()
                if (response.isSuccessful) {
                    handleSuccessfulResponse(response)
                } else {
                    handleFailedResponse(response)
                }
            } catch (e: Exception) {
                handleOffline()
            }
        }
    }

    private suspend fun handleSuccessfulResponse(response: Response<List<Product>>) {
        val products = response.body()!!
        if (products.isEmpty()) {
            updateProductsResult(Result.Error("No products available"))
        } else {
            updateProductsResult(Result.Success(products))
            databaseHandle.productDao().removeAllProducts()
            databaseHandle.productDao().insertAllProducts(products)
        }
    }

    private suspend fun handleFailedResponse(response: Response<List<Product>>) {
        val errorBody = JSONObject(response.errorBody()!!.string())
        val errorMessage = errorBody.getString("message")
        updateProductsResult(Result.Error(errorMessage))
    }

    private suspend fun handleOffline() {
        val products = databaseHandle.productDao().getAllProducts();
        if (products.isEmpty()) {
            updateProductsResult(Result.Error("Offline, failed to load products"))
        } else {
            updateProductsResult(Result.Success(products))
        }
    }

    private suspend fun updateProductsResult(result: Result<List<Product>>) {
        withContext(Dispatchers.Main) {
            _productsResult.value = result
        }
    }
}