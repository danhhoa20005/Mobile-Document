package data

class Pencil(
    name: String,
    price: Int,
    brand: String,
    private val color: String,
    private val material: String,
    private val hardness: String
) : Product(name, price, brand) {
    override fun getDetails(): String {
        return "- Màu sắc: $color\n- Chất liệu: $material\n- Độ cứng: $hardness"
    }
}
