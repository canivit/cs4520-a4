package com.cs4520.assignment4

data class Product(val name: String, val price: Double, val expiryDate: String?, val type: Type) {

    enum class Type {
        Food,
        Equipment
    }
}
