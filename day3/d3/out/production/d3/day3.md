# TÍNH CHẤT CỦA OOP TRONG KOTLIN
---
## Interface là gì?

**Interface** (giao diện) trong lập trình hướng đối tượng (OOP) là một kiểu cấu trúc cho phép bạn định nghĩa một tập hợp các phương thức (hàm) mà bất kỳ class nào cài đặt interface đó đều phải thực hiện (override).

### Đặc điểm của interface:
- Interface chỉ định nghĩa “bản hợp đồng” về hành vi, không chứa cài đặt chi tiết (mặc dù Kotlin cho phép có hàm mặc định).
- Một class có thể cài đặt nhiều interface cùng lúc (đa kế thừa giao diện).
- Interface không thể khởi tạo thành đối tượng trực tiếp.

### Khi nào dùng interface?
- Khi bạn muốn các class khác nhau cùng thực hiện một nhóm hành vi (method) chung, nhưng mỗi class có cách cài đặt riêng.
- Khi cần viết code theo hướng “lập trình theo giao diện”, giúp phần mềm dễ mở rộng, dễ thay đổi.

---

### Ví dụ sử dụng interface trong Kotlin

```kotlin
interface Drawable {
    fun ve()
}

class HinhTron : Drawable {
    override fun ve() {
        println("Vẽ hình tròn")
    }
}

class HinhVuong : Drawable {
    override fun ve() {
        println("Vẽ hình vuông")
    }
}

fun main() {
    val ds: List<Drawable> = listOf(HinhTron(), HinhVuong())
    for (h in ds) {
        h.ve()
    }
}
```

**Kết quả:**
```
Vẽ hình tròn
Vẽ hình vuông
```

### Lợi ích khi sử dụng interface:
- **Tăng khả năng mở rộng:** Thêm class mới chỉ cần cài đặt interface mà không ảnh hưởng code cũ.
- **Hỗ trợ đa hình:** Dễ dàng xử lý các đối tượng khác nhau qua cùng một interface.
- **Tách biệt giao diện và cài đặt:** Thúc đẩy lập trình “hợp đồng”, giảm phụ thuộc giữa các thành phần trong hệ thống.

---

> **Tóm lại:**  
> Interface là công cụ giúp định nghĩa các hành vi chung, tăng tính linh hoạt, mở rộng và khả năng tái sử dụng trong lập trình hướng đối tượng (OOP/Kotlin).

## ĐÓNG GÓI(ENCAPSULATION)

### 1.1 Encapsulation là gì
**(ĐÓNG GÓI)** là việc ẩn thông tin nội bộ(thuộc tính, dữ liệu) của một lớp và chỉ cho phép truy cập thông qua các phương thức được định nghĩa rõc rạng

#### MỤC ĐÍCH
- bảo vệ dữ liệu khỏi thay đổi sai cách
- giảm rối rắm khi ử dụng đối tượng
- tăng tính an toàn và bảo trì dễ dàng
---
### 1.2 cách triển khai
- dùng từ khó Private để ẩn thuộc tích
- Dùng fun để tạo phương thức truy cập (get, set, show,...).
````kotlin
Class taikhoan{
    private var soDU : double = 0.0
    fun guiTien(tien: double)
    {
        if(tien >0)soDu += tien
    }
    fun show(): double
    {
        return soDu
    }
}
````

#### giải thích
- soDu (private) > bên ngoài class không truy cập trực tiếp được
- chỉ được phép gửi tiền qua guiTien và xem soDu = fun Show
---


## KẾ THỪA(INHERITANCE)

### 2.1 KẾ THỪA LÀ GÌ
**Kế thừa** là một tính chất trong lập trình hướng đối tương(OOP) là một cơ chế cho phép lớp con sử dụng lại các thuộc tính và phương thức của lớp cha. điều này giúp tránh lập lại code và dễ dang mở rộng chương trình

---
### 2.2 quy tắc kế thừa trong kotlin
- mặc dịnh các lớp trong kotlin là final, tức là không kế thừa
- muốn lớp được kế thừa, cần khai báo lớp đó là open
- lớp con kế thừa lớp cha bằng cú pháp class LopCon : LopCha()
#### ví dụ
``` kotlin
open class NhanVien(val ten: String) {
    fun chamCong() {
        println("$ten đã chấm công")
    }

    open fun tinhLuong(): Double {
        return 10000000.0
    }
}

class LapTrinhVien(ten: String, val gioOT: Int) : NhanVien(ten) {
    override fun tinhLuong(): Double {
        return super.tinhLuong() + gioOT * 50000
    }

    fun lamViec() {
        println("$ten đang viết code")
    }
}

fun main() {
    val dev = LapTrinhVien("Hòa", 10)
    dev.chamCong()               // Gọi từ lớp cha
    dev.lamViec()                // Gọi từ lớp con
    println(dev.tinhLuong())     // Gọi hàm override
}
```
````kotlin
kết quả :
Hòa đã chấm công
Hòa đang viết code
10500000.0

````
---
### 2.3 một số tính năng hay dùng trong công việc khi dùng kế thừa
- **open class** : cho phép class được kế thừa từ open class
- **override fun** : cho phép lớp con ghi đè(định nghĩa lại) phương thức của lớp cha
- **super** : gọi hàm hoặc biến của lớp cha(open class)
- tổ chức theo kiểu cha-con: ví dụ người > nhanvien > laptrinhvien
- giúp chia rõ các vai trò trong hệ thông(OOP): chia tách chức năng, dễ mở rộng
ví dụ ghi đè phương thức:
````kotlin
open class DongVat{
    open fun Keu(){
        println("gau gau")
    }
}
class MEO : DongVat(){
    override fun Keu(){
        super.keu()
        println("meo meo")
        }
}
````
---
### 2.4 kết luận
- kế thừa giúp bạn tổ chức hệ thống tốt hơn, tái sử dụng mã, dễ bảo trì
- kotlin chỉ cho kế thừa đơn(1 lớp cha), nhưng có thể kết hợp nhiều interface để hỗ trợ da kế thừa hành vi
- trong cong việc thực tế, kế thừa được đung trong cá hệ thống quản lý
---
## 3. POLYMORPHISM — ĐA HÌNH

### 3.1 Khái niệm (Polymorphism là gì?)

**Polymorphism** (Đa hình) là một trong bốn trụ cột quan trọng của lập trình hướng đối tượng (OOP), bên cạnh Tính đóng gói (Encapsulation), Kế thừa (Inheritance) và Trừu tượng (Abstraction).

**Hiểu đơn giản:**  
Polymorphism là khả năng “một tên – nhiều hành vi”.  
Bạn có thể gọi cùng một phương thức trên nhiều đối tượng khác nhau, và mỗi đối tượng sẽ đáp ứng (thực thi) theo cách riêng của nó.

**Điều này giúp:**
- Viết code tổng quát, dễ bảo trì, dễ mở rộng.
- Không cần biết kiểu cụ thể của đối tượng khi sử dụng.

---

#### **Biểu hiện của đa hình trong OOP:**
- **Override (Ghi đè):** Lớp con cài lại (định nghĩa lại) phương thức của lớp cha với cách thực hiện riêng.
- **Overloading (Nạp chồng):** Nhiều hàm cùng tên nhưng khác tham số (Kotlin hỗ trợ, nhưng đây là đa hình tĩnh).
- **Interface:** Nhiều lớp cùng thực hiện một interface, nhưng hành vi thực tế khi chạy lại phụ thuộc từng lớp.

---

### 3.2 Mục đích và lợi ích

| Mục tiêu                | Ý nghĩa                                                                 |
|-------------------------|------------------------------------------------------------------------|
| Tái sử dụng phương thức | Dùng chung tên phương thức, xử lý riêng tùy đối tượng cụ thể           |
| Tăng linh hoạt          | Viết code tổng quát, thao tác được nhiều loại đối tượng khác nhau      |
| Dễ mở rộng và bảo trì   | Thêm lớp mới chỉ cần override lại hành vi, không đụng đến code gốc     |


> **Tóm lại:**  
> Đa hình giúp hệ thống mở rộng dễ dàng, giảm phụ thuộc, tăng khả năng tái sử dụng, đồng thời làm code gọn gàng, rõ ràng hơn.

---

### 3.3 Ví dụ thực tế OOP: Tính lương nhân viên

**Bài toán:** Công ty có nhiều loại nhân viên: Lập trình viên, Giám đốc..., mỗi loại có cách tính lương khác nhau.  
Bạn muốn quản lý tất cả dưới dạng chung mà không phải viết riêng cho từng loại.

#### **1. Lớp cha (Superclass) định nghĩa giao diện chung**

```kotlin
open class NhanVien(val ten: String) {
    open fun tinhLuong(): Double {
        return 0.0 // Mặc định lương 0, lớp con sẽ override
    }

    fun chao() {
        println("Xin chào, tôi là $ten")
    }
}
```

#### **2. Lớp con kế thừa & override phương thức tính lương**

```kotlin
class LapTrinhVien(ten: String, val soGio: Int) : NhanVien(ten) {
    override fun tinhLuong(): Double {
        return soGio * 50000.0
    }
}

class GiamDoc(ten: String, val coPhan: Double) : NhanVien(ten) {
    override fun tinhLuong(): Double {
        return 20000000.0 + coPhan * 1000000.0
    }
}
```

#### **3. Sử dụng đa hình trong thực tế**

```kotlin
fun main() {
    val dsNhanVien = listOf<NhanVien>(
        LapTrinhVien("Nam", 160),
        GiamDoc("Hà", 0.3),
        LapTrinhVien("Tú", 180)
    )

    for (nv in dsNhanVien) {
        nv.chao()
        println("Lương: ${nv.tinhLuong()} VNĐ")
        println("--------")
    }
}
```

##### **Kết quả chạy:**

```
Xin chào, tôi là Nam
Lương: 8000000.0 VNĐ
--------
Xin chào, tôi là Hà
Lương: 23000000.0 VNĐ
--------
Xin chào, tôi là Tú
Lương: 9000000.0 VNĐ
--------
```

---

### 3.4 Phân tích kỹ thuật OOP

| Thành phần                   | Vai trò                                                                 |
|----------------------------- |-------------------------------------------------------------------------|
| `open class NhanVien`        | Lớp cha (base class), cho phép ghi đè các phương thức                   |
| `override fun tinhLuong()`   | Lớp con định nghĩa lại hành vi tính lương phù hợp với mình              |
| `List<NhanVien>`             | Danh sách kiểu lớp cha, chứa nhiều loại lớp con khác nhau                |
| `nv.tinhLuong()`             | Gọi đúng phiên bản phương thức tùy kiểu đối tượng thực tế lúc runtime   |

- **Đa hình động:** Khi chạy, Kotlin xác định loại thực sự của đối tượng (LapTrinhVien hay GiamDoc) và gọi đúng phương thức `tinhLuong()` tương ứng.
- **Viết code tổng quát:** Dùng danh sách kiểu `NhanVien`, không cần quan tâm kiểu con cụ thể.

---

### 3.5 Các kiểu đa hình thường gặp trong OOP

| Kiểu đa hình      | Mô tả                                                                                          |
|-------------------|-----------------------------------------------------------------------------------------------|
| Đa hình động      | Ghi đè phương thức (`override`) ở lớp con, quyết định phương thức thực thi khi chạy (runtime) |
| Đa hình tĩnh      | Nạp chồng hàm (overloading): nhiều hàm cùng tên, khác tham số (compile time)                  |
| Interface + đa hình | Nhiều lớp cài cùng một interface, mỗi lớp thực hiện hành vi theo cách riêng                  |

---

### 3.6 Vì sao đa hình quan trọng?

- **Tăng khả năng mở rộng:** Thêm lớp mới chỉ cần kế thừa và override, không phải sửa code cũ.
- **Tối ưu hóa bảo trì:** Thay đổi hành vi một loại đối tượng không ảnh hưởng tới nơi sử dụng nó.
- **Tạo ra các API, framework mạnh mẽ:** Giúp các framework, thư viện xử lý được nhiều loại đối tượng khách hàng khác nhau mà không cần biết chi tiết bên trong.

---

> **Tóm lại:**  
> Đa hình là "chìa khóa vàng" để xây dựng hệ thống phần mềm mềm dẻo, mở rộng tốt, code tái sử dụng tối đa, và bảo trì dễ dàng trong OOP/Kotlin.
---
## 4. ABSTRACTION — TRỪU TƯỢNG

### 4.1 Khái niệm

**Abstraction (Trừu tượng)** là một nguyên lý quan trọng của lập trình hướng đối tượng (OOP), cho phép bạn mô tả những đặc điểm, hành vi cốt lõi của đối tượng mà bỏ qua các chi tiết cụ thể không cần thiết.  
Nói cách khác, abstraction giúp bạn xây dựng mô hình tổng quát hóa, tập trung vào cái “gì” đối tượng làm, thay vì “làm như thế nào”.

---

### 4.2 Hiểu theo OOP

- **Trừu tượng** là khả năng xác định những thuộc tính, phương thức “bắt buộc” mà mọi lớp con phải cài đặt.
- Giúp che giấu chi tiết triển khai, chỉ lộ ra những gì cần thiết cho bên ngoài sử dụng.
- Người sử dụng class chỉ quan tâm đến cách tương tác, không cần biết bên trong hoạt động ra sao.

---

### 4.3 Triển khai Abstraction trong Kotlin

Trong Kotlin, abstraction thường được thực hiện qua:
- **Abstract class (lớp trừu tượng):**  
  Lớp chứa một hoặc nhiều phương thức trừu tượng (không có thân hàm), được định nghĩa bằng từ khóa `abstract`. Lớp con kế thừa phải override và cài đặt các phương thức này.
- **Interface (giao diện):**  
  Định nghĩa tập hợp hành vi mà lớp con phải thực hiện. Một lớp có thể cài đặt nhiều interface.

---

### 4.4 Ví dụ về Abstract class

```kotlin
// Lớp trừu tượng mô tả động vật
abstract class DongVat(val ten: String) {
    abstract fun keu() // Phương thức trừu tượng

    fun gioiThieu() {
        println("Tôi là $ten")
    }
}

// Lớp con phải override hàm keu()
class Cho : DongVat("Chó") {
    override fun keu() {
        println("Gâu Gâu!")
    }
}

class Meo : DongVat("Mèo") {
    override fun keu() {
        println("Meo Meo!")
    }
}

fun main() {
    val ds = listOf<DongVat>(Cho(), Meo())
    for (dv in ds) {
        dv.gioiThieu()
        dv.keu()
    }
}
```
**Kết quả:**
```
Tôi là Chó
Gâu Gâu!
Tôi là Mèo
Meo Meo!
```

---

### 4.5 Ví dụ về Interface

```kotlin
interface BayDuoc {
    fun bay()
}

class Chim : BayDuoc {
    override fun bay() {
        println("Chim đang bay trên trời")
    }
}

class MayBay : BayDuoc {
    override fun bay() {
        println("Máy bay cất cánh")
    }
}

fun main() {
    val dsBay = listOf<BayDuoc>(Chim(), MayBay())
    for (obj in dsBay) {
        obj.bay()
    }
}
```
**Kết quả:**
```
Chim đang bay trên trời
Máy bay cất cánh
```

---

### 4.6 Lợi ích của Abstraction

- **Giảm phức tạp:** Người dùng chỉ cần quan tâm tới hành vi chính, không cần biết chi tiết cài đặt.
- **Tăng khả năng mở rộng:** Dễ dàng thêm lớp mới mà không ảnh hưởng tới code hiện tại.
- **Tăng tính bảo mật:** Che giấu chi tiết nội bộ, kiểm soát truy cập thông tin.

---

> **Tóm lại:**  
> Abstraction giúp bạn xây dựng những class/tập giao diện tổng quát, linh hoạt, an toàn và dễ bảo trì trong hệ thống phần mềm OOP/Kotlin.
### So sánh giữa Interface và Abstract class (Lớp trừu tượng) trong Kotlin

| Tiêu chí                      | Interface                                        | Abstract class                                    |
|-------------------------------|--------------------------------------------------|----------------------------------------------------|
| **Mục đích**                  | Định nghĩa “bản hợp đồng” về hành vi chung       | Định nghĩa khung (template) cho các lớp con, có thể chứa cả thuộc tính, hàm có sẵn và hàm trừu tượng |
| **Từ khóa khai báo**          | `interface`                                      | `abstract class`                                  |
| **Kế thừa**                   | Một lớp có thể cài đặt nhiều interface (đa kế thừa giao diện) | Một lớp chỉ kế thừa một abstract class (do Kotlin chỉ hỗ trợ đơn kế thừa class) |
| **Hàm trừu tượng**            | Tất cả hàm trong interface mặc định là abstract (trừ khi có thân hàm mặc định) | Có thể chứa cả hàm trừu tượng (không có thân) và hàm thường (có thân) |
| **Thuộc tính**                | Chỉ khai báo, không có trạng thái lưu trữ (không có field), chỉ getter/setter | Có thể có thuộc tính lưu trữ trạng thái (property), có thể khai báo field và khởi tạo giá trị |
| **Constructor**               | Không có constructor                             | Có thể có constructor                             |
| **Truy cập phạm vi**          | Các hàm/thuộc tính mặc định là `abstract` (trừ khi khai báo thân hàm) | Có thể dùng modifier (`private`, `protected`, ...) cho thuộc tính/hàm |
| **Khi nào dùng?**             | Khi cần nhiều lớp khác nhau cùng thực hiện một bộ hành vi, nhưng không chia sẻ logic cụ thể | Khi có một “gốc chung” với hành vi, thuộc tính, logic chung, và cần ép buộc lớp con phải cài đặt một số hành vi |
| **Ví dụ thực tế**             | Giao diện `Drawable` cho các hình học: vẽ, di chuyển, ... | Lớp trừu tượng `DongVat`: có thuộc tính tên, phương thức “keu()” trừu tượng, phương thức “an()” có sẵn |

---

#### **Bảng tổng hợp chi tiết**

| Đặc điểm                    | Interface                  | Abstract class               |
|-----------------------------|----------------------------|------------------------------|
| Đa kế thừa                  | Có                         | Không                        |
| Có thể có thân hàm          | Có (từ Kotlin 1.1 trở lên) | Có                           |
| Có thuộc tính trạng thái    | Không                      | Có                           |
| Có constructor              | Không                      | Có                           |
| Mục đích chính              | Định nghĩa hành vi         | Định nghĩa khung, logic chung |
| Tính mở rộng                | Rất linh hoạt              | Linh hoạt vừa phải           |

---

#### **Tóm lại:**

- **Interface** phù hợp để mô tả các hành vi chung mà nhiều lớp không cùng “dòng họ” có thể thực hiện (ví dụ: `BayDuoc`, `Drawable`, `Serializable`...).
- **Abstract class** phù hợp khi các lớp con cùng “dòng họ”, chia sẻ logic hoặc thuộc tính chung, nhưng cần ép buộc một số phương thức phải được cài đặt lại.

> **Nguyên tắc chọn:**  
> Nếu chỉ cần định nghĩa hành vi, không quản lý trạng thái => dùng interface.  
> Nếu cần lưu trữ trạng thái, chia sẻ logic, hoặc có thuộc tính chung => dùng abstract class.
## Backing field là gì? (Giải thích dễ hiểu)

### 1. Định nghĩa

**Backing field** là “chỗ lưu trữ bí mật” bên trong một đối tượng, dùng để giữ giá trị thực sự của một thuộc tính (property) khi bạn muốn kiểm soát quá trình lấy (get) hoặc gán (set) giá trị.  
Nói cách khác, khi bạn tự viết getter/setter cho một thuộc tính, backing field sẽ giúp bạn lưu và truy xuất giá trị đó mà không cần tạo thêm biến riêng.

---

### 2. Vì sao cần backing field?

Khi bạn viết code kiểu này:

```kotlin
var age: Int = 18
```

Kotlin tự động tạo cho bạn một vùng nhớ ẩn để lưu giá trị `age`.  
Nhưng khi bạn muốn tự kiểm soát việc lấy/gán giá trị (tùy biến getter/setter), bạn cần tham chiếu đến vùng nhớ ẩn đó — chính là **backing field**.

Nếu không có backing field, khi bạn set giá trị mới, bạn sẽ không biết lưu nó ở đâu; khi get thì không biết lấy ra từ đâu!

---

### 3. Cách dùng backing field trong Kotlin

- Kotlin cung cấp từ khóa đặc biệt **`field`**, chỉ dùng được bên trong getter hoặc setter của thuộc tính đó.
- `field` chính là cái “vùng nhớ ngầm” lưu giá trị thực tế của thuộc tính.

**Ví dụ:**

```kotlin
var name: String = "Hoa"
    get() = field.uppercase()  // Khi lấy giá trị, trả về chữ HOA
    set(value) {
        field = value.trim()   // Khi gán giá trị, tự động loại bỏ khoảng trắng đầu cuối
    }
```

**Giải thích:**
- Khi bạn gán: `name = "  Lan  "`, setter thực hiện `field = value.trim()` ⇒ field lưu "Lan".
- Khi bạn lấy: `println(name)`, getter thực hiện `field.uppercase()` ⇒ ra "LAN".

---

### 4. Hình dung dễ hiểu

Hãy tưởng tượng property là một cái “hộp thư”:
- **Backing field** là cái hộc tủ thật sự bên trong để cất giữ lá thư (giá trị).
- Khi bạn viết riêng getter/setter, chỉ có thể truy cập cái hộc tủ này qua từ khóa `field`.

---

### 5. Khi nào cần, khi nào không cần backing field?

- **Cần:** Khi bạn muốn kiểm soát quá trình get/set mà vẫn cần lưu giá trị property (ví dụ: kiểm tra, chuyển đổi, format, kiểm soát dữ liệu đầu vào...).
- **Không cần:** Nếu property chỉ để tính toán động (không lưu giá trị), bạn có thể chỉ viết getter, không dùng backing field.

**Ví dụ không cần backing field:**

```kotlin
val now: Long
    get() = System.currentTimeMillis() // Chỉ trả về thời gian hiện tại, không cần lưu lại
```

---

### 6. Lưu ý khi dùng backing field

- Bạn **chỉ dùng được** từ khóa `field` bên trong getter/setter của property đó.
- Nếu tự khai báo biến riêng ngoài property, nó không còn là backing field nữa.

---

### 7. Tổng kết

- **Backing field** là “bộ nhớ ẩn” giúp property lưu giữ giá trị khi bạn tuỳ biến getter/setter.
- Trong Kotlin, dùng từ khóa **`field`** bên trong getter/setter để thao tác với backing field.
- Nhờ backing field, bạn có thể kiểm soát dữ liệu khi truy xuất hoặc gán giá trị cho thuộc tính một cách an toàn và linh hoạt.

---
**Hình minh họa logic:**
```
var x = 10
// Kotlin tạo backing field lưu giá trị 10

get() = field + 1
set(value) { field = value.coerceAtLeast(0) }
// Khi get: trả về field + 15
// Khi set: chỉ lưu các giá trị >= 0
```
--- 
**Tóm lại:**  
Backing field giúp bạn “giữ bí mật” giá trị thật bên trong property, nhưng vẫn cho phép bạn kiểm soát cách lấy hoặc gán giá trị một cách thông minh!



