# BUỔI 1: Kotlin cơ bản

## I. Biến, kiểu dữ liệu

### a. Cách để khai báo biến:

-   **Khai báo biến trong Kotlin** sử dụng hai từ khóa:
    -   `val`: Biến không đổi (immutable), chỉ được gán một lần. Giống như mã số sinh viên của bạn.
    -   `var`: Biến có thể thay đổi (mutable), được phép gán lại nhiều lần. Giống như điểm số của bạn qua các kỳ thi.

-   **Ví dụ khai báo biến:**
    -   Không chỉ rõ kiểu dữ liệu (Kotlin tự động suy luận kiểu):
        ```kotlin
        var tenSach = "Lập trình Android"    // kiểu String
        var soLuong = 50                     // kiểu Int
        ```
    -   Chỉ rõ kiểu dữ liệu:
        ```kotlin
        var tenSach: String = "Lập trình Android"
        var soLuong: Int = 50
        ```
    -   Khai báo trước, gán giá trị sau (bắt buộc chỉ rõ kiểu dữ liệu):
        ```kotlin
        var moTa: String
        // ... thực hiện một số thao tác ...
        moTa = "Sách dành cho người mới bắt đầu."
        ```
        Nếu không chỉ rõ kiểu dữ liệu sẽ báo lỗi:
        ```kotlin
        var moTa     // ERROR: chưa biết kiểu dữ liệu của moTa
        moTa = "Sách dành cho người mới bắt đầu."
        ```
    -   Với `val`, không thể thay đổi giá trị sau khi đã gán:
        ```kotlin
        val maSanPham = "SP001"
        maSanPham = "SP002"      // ERROR: maSanPham (val) không thể thay đổi
        ```

### b. Kiểu dữ liệu:

-   **Số nguyên**:
    | Kiểu dữ liệu | Khoảng giá trị                                       |
    | :----------- | :--------------------------------------------------- |
    | Byte         | -128 -> 127 (-2⁷ -> 2⁷ - 1)                          |
    | Short        | -32768 -> 32767 (-2¹⁵ -> 2¹⁵ - 1)                    |
    | Int          | -2,147,483,648 -> 2,147,483,647 (-2³¹ -> 2³¹ - 1)      |
    | Long         | -9,223,372,036,854,775,808 -> 9,223,372,036,854,775,807 (-2⁶³ -> 2⁶³ - 1) |

-   **Số thực**:
    -   `Float`: Lưu 6-7 chữ số thập phân.
    -   `Double`: Lưu 15-16 chữ số thập phân (chính xác hơn và được ưu tiên).

-   **Kiểu đúng sai**:
    -   `Boolean`: chỉ nhận giá trị `true` hoặc `false`.

-   **Kiểu ký tự**:
    -   `Char`: một ký tự duy nhất, đặt trong dấu nháy đơn (ví dụ: 'A', 'b', '9').

-   **Mảng (Array) trong Kotlin**:
    -   Mảng là một bộ sưu tập có kích thước cố định, chứa các phần tử cùng kiểu.
    -   Đại diện bởi class `Array`, có các hàm như: `get`, `set`, `size`,...

-   **Chuỗi trong Kotlin**:
    -   Đại diện bởi class `String`. Một chuỗi ký tự là một instance của class `String`.

## II. Câu lệnh rẽ nhánh

-   **Câu lệnh `if else`**:
    ```kotlin
    val diemTrungBinh = 8.5
    if (diemTrungBinh >= 8.0) {
        println("Học sinh Giỏi.")
    } else if (diemTrungBinh >= 6.5) {
        println("Học sinh Khá.")
    } else {
        println("Học sinh Trung bình.")
    }
    ```
-   **Sử dụng `if else` làm biểu thức gán**:
    ```kotlin
    val diemTrungBinh = 5.0
    val xepLoai = if (diemTrungBinh >= 5.0) "Đạt" else "Không đạt"
    println("Kết quả: $xepLoai")
    ```
-   **`when`: giống `switch-case` nhưng mạnh mẽ hơn**:
    ```kotlin
    val phuongThucThanhToan = "Momo"
    val thongBao = when (phuongThucThanhToan) {
      "Tiền mặt" -> "Vui lòng chuẩn bị đủ tiền mặt."
      "Momo", "ZaloPay" -> "Quét mã QR để thanh toán."
      "Thẻ tín dụng" -> "Chèn thẻ và nhập mã PIN."
      else -> "Phương thức không hợp lệ."
    }
    println(thongBao)
    ```

## III. Vòng lặp

-   **while**: Lặp khi điều kiện còn đúng.
    ```kotlin
    var pin = 20
    println("Bắt đầu sạc pin...")
    while (pin < 100) {
      pin += 10
      println("Pin hiện tại: $pin%")
    }
    println("Pin đã được sạc đầy!")
    ```

-   **for**: Dùng để duyệt qua một dãy hoặc một tập hợp.
    -   Duyệt tuần tự các giá trị trong một khoảng (Closed Range):
        ```kotlin
        // In ra các số từ 1 đến 5
        for (i in 1..5) {
          print("$i ") // Kết quả: 1 2 3 4 5
        }
        ```
    -   Duyệt tuần tự gần hết giá trị (Half-open range):
        ```kotlin
        val soPhanTu = 5
        // Duyệt index từ 0 đến 4
        for (i in 0 until soPhanTu) {
           // ...
        }
        ```
    -   Bước nhảy `step`:
        ```kotlin
        // In ra các số chẵn từ 0 đến 10
        for (i in 0..10 step 2) {
          print("$i ") // Kết quả: 0 2 4 6 8 10
        }
        ```
    -   Duyệt ngược `downTo`:
        ```kotlin
        // Đếm ngược từ 3 về 1
        for (i in 3 downTo 1) {
            println("$i...")
        }
        println("Bắt đầu!")
        ```
    -   Lặp qua các phần tử của một tập hợp:
        ```kotlin
        val danhSachMonHoc = arrayOf("Toán", "Lý", "Hóa")
        for (monHoc in danhSachMonHoc) {
            println("Hôm nay học môn: $monHoc")
        }
        ```
    -   Duyệt kèm chỉ số (index):
        ```kotlin
        val danhSachMonHoc = arrayOf("Toán", "Lý", "Hóa")
        for (i in danhSachMonHoc.indices) {
            println("Môn học tại vị trí $i là: " + danhSachMonHoc[i])
        }
        ```
    -   Vừa lấy index vừa lấy value:
        ```kotlin
        val danhSachMonHoc = arrayOf("Toán", "Lý", "Hóa")
        for ((index, value) in danhSachMonHoc.withIndex()) {
            println("Môn học ${index + 1}: $value")
        }
        ```

## IV. Các Collections trong Kotlin

-   **Collections** bao gồm `List`, `Set`, và `Map`.
-   **Immutable (Bất biến)**: Không thể thay đổi (thêm, sửa, xóa) sau khi khởi tạo.
    ```kotlin
    // List: Danh sách các chapter của một cuốn sách (có thứ tự)
    val danhSachChuong: List<String> = listOf("Chương 1: Giới thiệu", "Chương 2: Cài đặt", "Chương 3: Biến")
    println(danhSachChuong[0]) // In ra "Chương 1: Giới thiệu"

    // Set: Tập hợp các quyền của người dùng (không trùng lặp)
    val quyenNguoiDung: Set<String> = setOf("xem", "sửa", "xóa", "xem")
    println(quyenNguoiDung) // Chỉ in ra: [xem, sửa, xóa]

    // Map: Ánh xạ từ mã quốc gia sang tên quốc gia
    val maQuocGia: Map<String, String> = mapOf("VN" to "Việt Nam", "JP" to "Nhật Bản")
    println("Mã 'VN' tương ứng với: ${maQuocGia["VN"]}")
    ```

-   **Mutable (Khả biến)**: Có thể thay đổi giá trị.
    ```kotlin
    // MutableList: Giỏ hàng online
    val gioHang: MutableList<String> = mutableListOf("iPhone 15", "Ốp lưng")
    gioHang.add("Sạc dự phòng") // Thêm sản phẩm
    gioHang.remove("Ốp lưng") // Bỏ sản phẩm
    println("Sản phẩm trong giỏ: $gioHang")

    // MutableSet: Danh sách người tham gia sự kiện (đảm bảo không ai bị trùng)
    val nguoiThamGia: MutableSet<String> = mutableSetOf("An", "Bình")
    nguoiThamGia.add("An") // Thêm 'An' lần nữa cũng không thay đổi set
    nguoiThamGia.add("Châu")
    println("Người tham gia: $nguoiThamGia")

    // MutableMap: Bảng giá sản phẩm có thể cập nhật
    val bangGia: MutableMap<String, Double> = mutableMapOf("Táo" to 25000.0, "Cam" to 30000.0)
    bangGia["Táo"] = 28000.0 // Cập nhật giá Táo
    bangGia["Xoài"] = 35000.0 // Thêm sản phẩm mới
    println("Bảng giá hôm nay: $bangGia")
    ```
-   Xem thêm các hàm: [Hàm trong collection Kotlin](https://viblo.asia/p/tim-hieu-collections-list-set-map-trong-kotlin-ORNZqbLLl0n)

## V. Null safety

-   Kotlin được thiết kế để loại bỏ lỗi `NullPointerException`. Mặc định, biến không thể có giá trị `null`.
    ```kotlin
    var ten: String = "Sơn"
    // ten = null // Lỗi biên dịch!
    ```
-   Để cho phép biến nhận giá trị `null`, thêm dấu `?` vào sau kiểu dữ liệu.
    ```kotlin
    var tenDem: String? = "Văn"
    tenDem = null // Hoàn toàn hợp lệ
    ```
-   **Safe call (`?.`)**: Dùng khi truy cập thuộc tính/phương thức của biến có thể `null`. Nếu biến là `null`, cả biểu thức sẽ trả về `null` thay vì gây lỗi.
    ```kotlin
    var tieuDeSach: String? = "Lão Hạc"
    val doDaiTieuDe = tieuDeSach?.length // Trả về độ dài nếu tieuDeSach khác null
    println("Độ dài tiêu đề: $doDaiTieuDe")

    tieuDeSach = null
    val doDaiMoi = tieuDeSach?.length // Trả về null
    println("Độ dài mới: $doDaiMoi")
    ```

-   **Toán tử Elvis (`?:`)**: Gán một giá trị mặc định nếu biểu thức bên trái là `null`.
    ```kotlin
    var nickname: String? = null
    val tenHienThi = nickname ?: "Người dùng mới" // Nếu nickname là null, dùng "Người dùng mới"
    println("Chào mừng, $tenHienThi!") // Kết quả: Chào mừng, Người dùng mới!
    ```

## VI. Nhập xuất:

-   **Nhập dữ liệu**:
    ```kotlin
    fun main() {
        print("Vui lòng nhập tên của bạn: ")
        val ten = readln() // Đọc một dòng từ bàn phím

        print("Bạn bao nhiêu tuổi? ")
        val tuoi = readln().toIntOrNull() ?: 0 // Đọc và chuyển sang Int an toàn

        println("Xin chào $ten! Rất vui khi biết bạn $tuoi tuổi.")
    }
    ```

-   **Xuất dữ liệu**:
    -   Dùng `print()` để in ra trên cùng một dòng.
    -   Dùng `println()` để in ra và tự động xuống dòng mới.
    ```kotlin
    print("Học lập trình Kotlin")
    println(" thật thú vị!")
    print("Bắt đầu ngay thôi.")
    // Kết quả:
    // Học lập trình Kotlin thật thú vị!
    // Bắt đầu ngay thôi.
    ```

---

**Tóm tắt:**
Kotlin là ngôn ngữ lập trình hiện đại, cú pháp đơn giản, có nhiều tính năng mạnh mẽ như kiểm soát `null`, collection, lập trình hàm... Rất phù hợp cho phát triển ứng dụng Android và backend hiện đại.