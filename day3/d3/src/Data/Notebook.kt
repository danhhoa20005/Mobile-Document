package data

class Notebook(
    name: String,
    price: Int,
    brand: String,
    private val pageCount: Int,
    private val type: String,
    private val coverColor: String,
    private val paperMaterial: String,
    private val size: String
) : Product(name, price, brand) {
    override fun getDetails(): String {
        return "- Số trang: $pageCount\n- Loại vở: $type\n- Màu bìa: $coverColor\n- Chất liệu giấy: $paperMaterial\n- Kích thước: $size"
    }
}
