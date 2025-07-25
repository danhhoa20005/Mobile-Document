package ui

import data.*
import service.ProductService
import java.util.Scanner

// Hàm khởi động toàn bộ ứng dụng
fun runApp() {
    val scanner = Scanner(System.`in`)
    val service = ProductService()

    // Dữ liệu mẫu ban đầu
    service.addProduct(Pencil("Bút chì", 5000, "Thiên Long", "Đen", "Gỗ", "HB"))
    service.addProduct(Pen("Bút mực", 10000, "Thiên Long", "Đen", "Nhựa", "Mực dầu", "0.5mm"))
    service.addProduct(Book("Sách Kí Ức Đen", 50000, "Tiểu thuyết", "Nguyễn Nhật Ánh", "Kim Đồng", 2010, "Tiếng Việt"))
    service.addProduct(Book("Sách Đắc Nhân Vật", 100000, "Kỹ năng sống", "Dale Carnegie", "NXB Đồng Đen", 2010, "Tiếng Việt"))

    // Vòng lặp menu chính
    while (true) {
        println("\n===== MENU =====")
        println("1. Tìm kiếm sản phẩm")
        println("2. Hiển thị toàn bộ sản phẩm")
        println("3. Thêm sản phẩm mới")
        println("0. Thoát")
        print("Chọn chức năng: ")

        when (scanner.nextLine().trim()) {
            "1" -> {
                print("Nhập từ khóa tìm kiếm: ")
                val keyword = scanner.nextLine()
                print("Chọn kiểu hiển thị (1 = bảng, 2 = danh sách): ")
                val choice = scanner.nextLine().trim()
                val result = service.searchProducts(keyword)
                displayProducts(result, choice == "1")
            }

            "2" -> {
                val all = service.getAllProducts()
                print("Chọn kiểu hiển thị (1 = bảng, 2 = danh sách): ")
                val choice = scanner.nextLine().trim()
                displayProducts(all, choice == "1")
            }

            "3" -> {
                addProductFromInput(scanner, service)
            }

            "0" -> {
                println("Đã thoát chương trình.")
                break
            }

            else -> println("⚠️ Lựa chọn không hợp lệ. Vui lòng thử lại.")
        }
    }
}

// Hàm hiển thị danh sách sản phẩm với 2 kiểu: bảng hoặc danh sách
private fun displayProducts(products: List<Product>, isTable: Boolean) {
    if (products.isEmpty()) {
        println("⚠️ Không tìm thấy sản phẩm nào.")
        return
    }

    if (isTable) {
        println("| Tên sản phẩm       | Giá bán | Thương hiệu  | Thông tin thêm")
        println("|--------------------|---------|---------------|----------------")
        for (p in products) {
            val name = p.getName().padEnd(19)
            val price = p.getPrice().toString().padEnd(7)
            val brand = p.getBrand().padEnd(13)
            val details = p.getDetails().replace("\n", "\n${" ".repeat(48)}")
            println("| $name | $price | $brand | $details")
        }
    } else {
        println("\nDanh sách sản phẩm tìm kiếm được:")
        for (p in products) {
            println("\nTên sản phẩm: ${p.getName()}")
            println("Giá bán: ${p.getPrice()}")
            println("Thương hiệu: ${p.getBrand()}")
            println(p.getDetails())
            println("------")
        }
    }
}

// Hàm nhập và thêm sản phẩm từ người dùng
private fun addProductFromInput(scanner: Scanner, service: ProductService) {
    println("\n--- THÊM SẢN PHẨM ---")
    println("1. Bút chì")
    println("2. Bút mực")
    println("3. Vở")
    println("4. Sách")
    print("Chọn loại sản phẩm: ")
    when (scanner.nextLine().trim()) {
        "1" -> {
            println("Nhập lần lượt: tên, giá, thương hiệu, màu, chất liệu, độ cứng:")
            val name = scanner.nextLine()
            val price = scanner.nextLine().toInt()
            val brand = scanner.nextLine()
            val color = scanner.nextLine()
            val material = scanner.nextLine()
            val hardness = scanner.nextLine()
            val product = Pencil(name, price, brand, color, material, hardness)
            service.addProduct(product)
            println("✅ Đã thêm bút chì.")
        }

        "2" -> {
            println("Nhập: tên, giá, thương hiệu, màu, chất liệu, loại mực, độ mịn:")
            val name = scanner.nextLine()
            val price = scanner.nextLine().toInt()
            val brand = scanner.nextLine()
            val color = scanner.nextLine()
            val material = scanner.nextLine()
            val inkType = scanner.nextLine()
            val thickness = scanner.nextLine()
            val product = Pen(name, price, brand, color, material, inkType, thickness)
            service.addProduct(product)
            println("✅ Đã thêm bút mực.")
        }

        "3" -> {
            println("Nhập: tên, giá, thương hiệu, số trang, loại vở, màu bìa, chất liệu giấy, kích thước:")
            val name = scanner.nextLine()
            val price = scanner.nextLine().toInt()
            val brand = scanner.nextLine()
            val pageCount = scanner.nextLine().toInt()
            val type = scanner.nextLine()
            val coverColor = scanner.nextLine()
            val paperMaterial = scanner.nextLine()
            val size = scanner.nextLine()
            val product = Notebook(name, price, brand, pageCount, type, coverColor, paperMaterial, size)
            service.addProduct(product)
            println("✅ Đã thêm vở.")
        }

        "4" -> {
            println("Nhập: tên, giá, thể loại, tác giả, nhà xuất bản, năm, ngôn ngữ:")
            val name = scanner.nextLine()
            val price = scanner.nextLine().toInt()
            val category = scanner.nextLine()
            val author = scanner.nextLine()
            val publisher = scanner.nextLine()
            val year = scanner.nextLine().toInt()
            val language = scanner.nextLine()
            val product = Book(name, price, category, author, publisher, year, language)
            service.addProduct(product)
            println("✅ Đã thêm sách.")
        }

        else -> println("⚠️ Lựa chọn không hợp lệ.")
    }
}
