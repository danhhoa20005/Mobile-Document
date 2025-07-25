package data

class Pen(
    name: String,
    price: Int,
    brand: String,
    private val color: String,
    private val material: String,
    private val inkType: String,
    private val thickness: String
) : Product(name, price, brand) {
    override fun getDetails(): String {
        return "- Màu sắc: $color\n- Chất liệu: $material\n- Loại mực: $inkType\n- Độ mịn: $thickness"
    }
}
