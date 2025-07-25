package data

class Book(
    name: String,
    price: Int,
    private val category: String,
    private val author: String,
    private val publisher: String,
    private val year: Int,
    private val language: String
) : Product(name, price, publisher) {
    override fun getDetails(): String {
        return "- Thể loại: $category\n- Tác giả: $author\n- Nhà xuất bản: $publisher\n- Năm xuất bản: $year\n- Ngôn ngữ: $language"
    }
}
