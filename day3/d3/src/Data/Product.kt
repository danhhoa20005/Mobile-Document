package data

abstract class Product(
    private val name: String,
    private val price: Int,
    private val brand: String
) {
    fun getName() = name
    fun getPrice() = price
    fun getBrand() = brand

    abstract fun getDetails(): String
}
