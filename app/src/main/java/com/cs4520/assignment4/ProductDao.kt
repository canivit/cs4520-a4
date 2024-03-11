package com.cs4520.assignment4

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProductDao {
    @Query("SELECT * FROM product")
    suspend fun getAllProducts(): List<Product>

    @Insert
    suspend fun insertAllProducts(products: List<Product>)

    @Query("DELETE FROM product")
    suspend fun removeAllProducts()
}