package service

import data.*

class ProductService {
    private val productList = mutableListOf<Product>()

    fun addProduct(product: Product) {
        productList.add(product)
    }
    fun getAllProducts(): List<Product> {
        return productList.toList()
    }

    fun searchProducts(keyword: String): List<Product> {
        return productList.filter {
            it.getName().contains(keyword, ignoreCase = true) ||
                    it.getDetails().contains(keyword, ignoreCase = true)
        }
    }

    fun display(products: List<Product>, asTable: Boolean) {
        if (products.isEmpty()) {
            println("Không tìm thấy sản phẩm nào.")
            return
        }

        if (asTable) {
            println("Tên sản phẩm\tGiá bán\tThương hiệu\tThông tin thêm")
            for (p in products) {
                println("${p.getName()}\t${p.getPrice()}\t${p.getBrand()}\t${p.getDetails().replace("\n", "\n\t\t\t\t")}")
            }
        } else {
            println("Danh sách sản phẩm tìm kiếm được:")
            for (p in products) {
                println("------")
                println("Tên sản phẩm: ${p.getName()}")
                println("Giá bán: ${p.getPrice()}")
                println("Thương hiệu: ${p.getBrand()}")
                println(p.getDetails())
            }
            println("------")
        }
    }
}
