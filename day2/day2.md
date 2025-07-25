# DAY2
Trong Lập trình Hướng đối tượng, một **class** không chỉ là bản thiết kế cho dữ liệu; nó là thực thể đóng gói chặt chẽ **trạng thái (state)** và **hành vi (behavior)**. Kotlin cung cấp các công cụ mạnh mẽ để hiện thực hóa các nguyên lý OOP một cách thanh lịch và hiệu quả.

---

## 1.1 Access Modifier và Nguyên tắc Đóng gói (Encapsulation) 📦

**Encapsulation (Tính đóng gói)** là nguyên tắc cốt lõi của OOP: che giấu trạng thái bên trong của một đối tượng và chỉ cho phép tương tác thông qua một tập hợp các hàm công khai (**public methods**). Điều này giúp bảo vệ sự toàn vẹn của dữ liệu và giảm sự phụ thuộc lẫn nhau giữa các phần của hệ thống.

**Access modifiers** trong Kotlin là công cụ chính để thực thi nguyên tắc này:

- **private**  
  Mức độ đóng gói mạnh nhất. Bất cứ thứ gì được đánh dấu `private` (thuộc tính, hàm) đều là chi tiết triển khai nội bộ của class. Bên ngoài không thể thấy hoặc thay đổi chúng. Điều này cho phép bạn tự do thay đổi logic bên trong mà không ảnh hưởng đến các phần khác của code đang sử dụng class đó.

- **protected**  
  Cho phép class con kế thừa và sử dụng các thành viên của class cha, tạo ra một "API được bảo vệ" cho việc mở rộng, nhưng vẫn ẩn chúng khỏi thế giới bên ngoài.

- **internal**  
  Là một giải pháp thực tế cho các dự án lớn. Cho phép các class trong cùng một module (ví dụ: một thư viện hoặc một feature) tương tác chặt chẽ với nhau mà không cần phải `public` chúng cho toàn bộ ứng dụng.

- **public**  
  Là "API công khai" của class bạn. Đây là những gì bạn cam kết sẽ không thay đổi một cách đột ngột.

**Tư duy OOP:**  
Thay vì mặc định mọi thứ là `public`, hãy bắt đầu với `private`. Chỉ nâng cấp phạm vi truy cập (private → protected → internal → public) khi thực sự cần thiết. Điều này sẽ tạo ra các class mạnh mẽ, dễ bảo trì và ít lỗi hơn.

---

### Ví dụ nâng cao:

```kotlin
class BankAccount(private var balance: Double) { // 'balance' được đóng gói chặt chẽ

    // Hành vi public để tương tác với trạng thái private
    fun deposit(amount: Double) {
        if (amount > 0) {
            balance += amount
            logTransaction("Deposited", amount)
        }
    }

    // Hành vi public
    fun withdraw(amount: Double): Boolean {
        if (amount > 0 && amount <= balance) {
            balance -= amount
            logTransaction("Withdrew", amount)
            return true
        }
        return false
    }

    // Chi tiết triển khai nội bộ, không cần thiết cho bên ngoài
    private fun logTransaction(type: String, amount: Double) {
        println("[$type] Amount: $amount, New Balance: $balance")
    }
}
```

Ở đây, `balance` là **private**.  
Cách duy nhất để thay đổi nó là thông qua các hàm `deposit` và `withdraw`.  
Chúng ta có thể thêm logic xác thực hoặc ghi log (`logTransaction`) bên trong mà không ảnh hưởng đến cách `BankAccount` được sử dụng từ bên ngoài.

---
## 1.2 Constructor trong Kotlin

**Constructor** (hàm khởi tạo) là thành phần đặc biệt dùng để tạo và khởi tạo một đối tượng từ class. Kotlin hỗ trợ hai loại constructor: **primary constructor** (hàm khởi tạo chính) và **secondary constructor** (hàm khởi tạo phụ).

---

### Primary constructor (Hàm khởi tạo chính)

Primary constructor được khai báo cùng với tên class, thường dùng để khởi tạo các thuộc tính cơ bản của đối tượng. Các tham số truyền vào primary constructor có thể được khai báo là `val` (readonly) hoặc `var` (có thể thay đổi).

**Ví dụ:**

```kotlin
class Person(val name: String, var age: Int)
```
- Khi tạo đối tượng:  
  `val p = Person("An", 20)`

Các thuộc tính trong constructor sẽ được khởi tạo ngay khi đối tượng được tạo.

---

### Truyền modifier cho constructor

Có thể truyền modifier như `private`, `public`, hoặc annotation cho constructor:

```kotlin
class SecretUser private constructor(val username: String)
```
- Constructor này là `private`, tức là không thể tạo đối tượng từ bên ngoài class.

---

### Nếu không dùng `val` hoặc `var` trong constructor

Khi khai báo constructor mà không dùng `val` hoặc `var`, tham số đó chỉ có tác dụng trong khối khởi tạo (init block) hoặc các hàm bên trong class:

```kotlin
class Animal(name: String) {
    val animalName = name
}
```

---

### Gán giá trị mặc định cho tham số

Có thể đặt giá trị mặc định cho tham số trong primary constructor:

```kotlin
class Product(val name: String, val price: Double = 0.0)
```
- Tạo đối tượng:  
  `val p1 = Product("Book")`        // price = 0.0  
  `val p2 = Product("Pen", 2.5)`    // price = 2.5

---

### Sử dụng logic trong quá trình khởi tạo với init block

Nếu cần kiểm tra hoặc xử lý logic khi khởi tạo, dùng thêm `init block`:

```kotlin
class Account(val username: String, var balance: Double) {
    init {
        require(balance >= 0) { "Balance phải lớn hơn hoặc bằng 0" }
    }
}
```

---

### Secondary constructor (Hàm khởi tạo phụ)

Secondary constructor được khai báo bên trong thân class với từ khóa `constructor`.  
Thường dùng khi bạn muốn hỗ trợ nhiều cách khởi tạo cho class, hoặc cần xử lý logic phức tạp hơn, đặc biệt khi có sự kết hợp giữa nhiều tham số hoặc khi cần khởi tạo từng phần.

**Ví dụ:**

```kotlin
class Student(val name: String) {
    var grade: Int = 0

    // Secondary constructor
    constructor(name: String, grade: Int) : this(name) {
        this.grade = grade
    }
}
```
- Khi tạo đối tượng:  
  `val st1 = Student("Hoa")`  
  `val st2 = Student("Lan", 12)`

Secondary constructor thường gọi lại primary constructor bằng từ khóa `this(...)`.

---

### Có thể có nhiều secondary constructor

```kotlin
class Box(val width: Int, val height: Int) {
    var color: String = "white"

    constructor(width: Int, height: Int, color: String) : this(width, height) {
        this.color = color
    }
}
```

---

### Thứ tự thực thi khi khởi tạo

Khi tạo đối tượng, thứ tự thực thi là:
1. Primary constructor (nếu có).
2. Init block (nếu có).
3. Secondary constructor (nếu được sử dụng).

**Ví dụ minh họa:**

```kotlin
class Demo(val value: Int) {
    init {
        println("Init block called. Value = $value")
    }

    constructor(value: Int, text: String) : this(value) {
        println("Secondary constructor called. Text = $text")
    }
}

val demo1 = Demo(10)
// Output: Init block called. Value = 10

val demo2 = Demo(20, "Hello")
// Output: Init block called. Value = 20
//         Secondary constructor called. Text = Hello
```

---

**Tóm lại về Constructor trong Kotlin:**
- Primary constructor: khai báo đơn giản, gắn liền với tên class, dùng cho thuộc tính chính.
- Secondary constructor: khai báo bổ sung trong thân class, dùng cho các trường hợp khởi tạo đặc biệt.
- Có thể kết hợp với init block để xử lý kiểm tra hoặc logic bổ sung khi khởi tạo đối tượng.

### 1.3. Init block (Khối khởi tạo)

**Init block** là một khối lệnh đặc biệt trong class Kotlin, được thực thi ngay sau khi primary constructor hoàn thành việc khởi tạo thuộc tính.  
Init block cho phép bạn thực hiện các thao tác bổ sung như kiểm tra dữ liệu đầu vào, ghi log, hoặc thiết lập trạng thái cho đối tượng.

---

#### Đặc điểm của init block

- Được khai báo bên trong thân class với từ khóa `init`.
- Có thể có nhiều init block trong một class; chúng sẽ được thực thi theo thứ tự xuất hiện khi khởi tạo đối tượng.
- Init block thường dùng để kiểm tra tính hợp lệ của dữ liệu, hoặc thực hiện các thao tác khởi tạo nâng cao.

---

#### Ví dụ đơn giản

```kotlin
class User(val name: String, var age: Int) {
    init {
        require(age >= 0) { "Tuổi phải >= 0" }
        println("User $name được tạo với tuổi $age")
    }
}
```

Khi tạo đối tượng:
```kotlin
val u1 = User("Hoa", 20)
// Output: User Hoa được tạo với tuổi 20

val u2 = User("Lan", -5)
// Lỗi: IllegalArgumentException: Tuổi phải >= 0
```

---

#### Có thể sử dụng nhiều init block

```kotlin
class Customer(val id: Int, val name: String) {
    init {
        println("Khởi tạo id: $id")
    }
    init {
        println("Khởi tạo name: $name")
    }
}
val c = Customer(1, "An")
// Output:
// Khởi tạo id: 1
// Khởi tạo name: An
```

---

#### Kết hợp init block với secondary constructor

Nếu class có secondary constructor, init block vẫn sẽ được thực thi sau khi primary constructor được gọi (qua `this(...)`).

```kotlin
class Product(val name: String) {
    var price: Double = 0.0

    init {
        println("Init block: $name, price = $price")
    }

    constructor(name: String, price: Double) : this(name) {
        this.price = price
        println("Secondary constructor: $name, price = $price")
    }
}

val p1 = Product("Book")
// Output: Init block: Book, price = 0.0

val p2 = Product("Pen", 5.0)
// Output: Init block: Pen, price = 0.0
//         Secondary constructor: Pen, price = 5.0
```

---

**Tóm lại về init block:**
- Init block giúp kiểm tra, thiết lập hoặc xử lý dữ liệu ngay khi đối tượng được tạo ra.
- Được thực thi ngay sau khi primary constructor kết thúc.
- Có thể có nhiều init block, chạy lần lượt từ trên xuống dưới.
- Kết hợp tốt với secondary constructor và các thao tác logic khởi tạo.
- --
## 1.3 Init block (Khối khởi tạo)

**Init block** là khối lệnh đặc biệt trong class Kotlin, dùng để thực thi các thao tác bổ sung ngay sau khi primary constructor hoàn tất việc khởi tạo thuộc tính. Init block giúp kiểm tra dữ liệu đầu vào, ghi log, hoặc thiết lập trạng thái ban đầu cho đối tượng.

---

### Đặc điểm của Init block

- Được khai báo bên trong thân class với từ khóa `init`.
- Có thể khai báo nhiều init block trong một class; chúng được thực thi theo thứ tự xuất hiện.
- Init block thường dùng để kiểm tra tính hợp lệ của dữ liệu hoặc thực hiện các thao tác khởi tạo nâng cao.

---

### Ví dụ cơ bản về Init block

```kotlin
class User(val name: String, var age: Int) {
    init {
        require(age >= 0) { "Tuổi phải >= 0" }
        println("User $name được tạo với tuổi $age")
    }
}
```

Tạo đối tượng:
```kotlin
val u1 = User("Hoa", 20)
// Output: User Hoa được tạo với tuổi 20

val u2 = User("Lan", -5)
// Lỗi: IllegalArgumentException: Tuổi phải >= 0
```

---

### Nhiều Init block trong một class

```kotlin
class Customer(val id: Int, val name: String) {
    init {
        println("Khởi tạo id: $id")
    }
    init {
        println("Khởi tạo name: $name")
    }
}
val c = Customer(1, "An")
// Output:
// Khởi tạo id: 1
// Khởi tạo name: An
```

---

### Kết hợp Init block với Secondary constructor

Nếu class có secondary constructor, init block sẽ được thực thi sau khi primary constructor được gọi (qua `this(...)`).

```kotlin
class Product(val name: String) {
    var price: Double = 0.0

    init {
        println("Init block: $name, price = $price")
    }

    constructor(name: String, price: Double) : this(name) {
        this.price = price
        println("Secondary constructor: $name, price = $price")
    }
}

val p1 = Product("Book")
// Output: Init block: Book, price = 0.0

val p2 = Product("Pen", 5.0)
// Output: Init block: Pen, price = 0.0
//         Secondary constructor: Pen, price = 5.0
```

---

**Tóm lại về Init block:**
- Init block giúp kiểm tra, thiết lập hoặc xử lý dữ liệu ngay khi đối tượng được tạo ra.
- Được thực thi ngay sau khi primary constructor kết thúc.
- Có thể khai báo nhiều init block, chạy lần lượt từ trên xuống dưới.
- Kết hợp tốt với secondary constructor và các thao tác logic khởi tạo.
- --
## 1.4 Class members (Thành viên lớp)

**Class members** là các thành phần bên trong một class, bao gồm:
- **Properties (thuộc tính):** Dữ liệu hoặc trạng thái của đối tượng.
- **Methods (phương thức):** Hành vi hoặc thao tác mà đối tượng thực hiện.
- **Nested class / inner class:** Class con nằm bên trong class cha (nâng cao).

Khai báo class members giúp bạn tổ chức dữ liệu và logic của đối tượng một cách rõ ràng.

---

### Ví dụ về thuộc tính và phương thức

```kotlin
class Rectangle(var width: Double, var height: Double) {
    // Property
    val area: Double
        get() = width * height

    // Method
    fun resize(newWidth: Double, newHeight: Double) {
        width = newWidth
        height = newHeight
    }
}
```
- `width`, `height`: Thuộc tính có thể thay đổi.
- `area`: Thuộc tính chỉ đọc, giá trị được tính động qua getter.
- `resize`: Phương thức cho phép thay đổi kích thước hình chữ nhật.

---

### Visibility modifiers dành cho class members

Bạn có thể dùng các access modifier (`private`, `protected`, `internal`, `public`) để kiểm soát phạm vi truy cập của từng thuộc tính hoặc phương thức.

```kotlin
class User(private val password: String) {
    fun checkPassword(input: String): Boolean {
        return input == password
    }
}
```
Ở đây, `password` là thuộc tính private, chỉ kiểm tra được qua hàm public.

---

## 1.5 Companion object (Đối tượng đồng hành)

**Companion object** là một đối tượng tĩnh bên trong class.  
Nó cho phép bạn định nghĩa các thành phần tĩnh (static members), factory method, hoặc hằng số liên quan tới class mà không cần tạo instance.

- Được khai báo với từ khóa `companion object`.
- Có thể truy cập trực tiếp qua tên class.

---

### Khi nào dùng companion object?

- Định nghĩa hằng số dùng chung cho tất cả instance của class.
- Viết hàm factory để tạo đối tượng từ dữ liệu đặc biệt.
- Định nghĩa các phương thức tiện ích liên quan đến class.

---

### Ví dụ companion object

```kotlin
class MathUtils {
    companion object {
        const val PI = 3.1415

        fun square(x: Double): Double {
            return x * x
        }
    }
}

val piValue = MathUtils.PI
val sqVal = MathUtils.square(5.0)
```
- `PI` là hằng số tĩnh.
- `square()` là phương thức tĩnh, gọi qua tên class mà không cần tạo instance.

---

### Factory method với companion object

```kotlin
class Student private constructor(val name: String, val age: Int) {
    companion object {
        fun createFromString(data: String): Student {
            val parts = data.split(",")
            return Student(parts[0], parts[1].toInt())
        }
    }
}

val st = Student.createFromString("Hoa,20")
```
- Hàm `createFromString` cho phép tạo Student từ chuỗi mà không public constructor.

---

**Tóm lại về class members & companion object:**
- Class members là các thuộc tính và phương thức của class, kiểm soát truy cập bằng access modifier.
- Companion object dùng để định nghĩa thành phần tĩnh hoặc phương thức tiện ích, truy cập qua tên class mà không cần tạo đối tượng.
## 2. Special Class: Data class, Enum class, Sealed class

---

### 2.1 Data class

**Data class** trong Kotlin là loại class chuyên dùng để lưu trữ dữ liệu (data holder).  
Khi khai báo với từ khóa `data`, Kotlin tự động sinh ra các hàm:
- `toString()`: chuyển đối tượng thành chuỗi dễ đọc.
- `equals()`: so sánh hai đối tượng dựa trên giá trị các thuộc tính.
- `hashCode()`: sinh mã băm dựa trên thuộc tính.
- `copy()`: tạo một bản sao đối tượng, cho phép thay đổi giá trị một số thuộc tính.

**Điều kiện để là data class:**
- Phải có ít nhất một thuộc tính trong primary constructor.
- Các thuộc tính phải khai báo với `val` hoặc `var`.
- Không được là abstract, open, sealed, hoặc inner class.

**Ứng dụng:**  
Data class cực kỳ hữu ích khi cần truyền dữ liệu giữa các thành phần của ứng dụng, đặc biệt trong các kiến trúc MVVM/MVP, API response, lưu trữ trạng thái UI.

**Ví dụ:**

```kotlin
data class User(val id: Int, val name: String)

val u1 = User(1, "Hoa")
val u2 = User(1, "Hoa")

println(u1 == u2) // true, vì data class tự động so sánh theo giá trị
println(u1.toString()) // User(id=1, name=Hoa)

val u3 = u1.copy(name = "Lan")
println(u3) // User(id=1, name=Lan)
```

---

### 2.2 Enum class

**Enum class** là class đặc biệt dùng để định nghĩa một tập hợp giá trị hằng số (constant).  
Mỗi giá trị của enum là một instance (đối tượng) của enum class đó.

**Đặc điểm:**
- Enum class giúp code dễ đọc, kiểm soát giá trị hợp lệ, tránh lỗi do giá trị không mong muốn.
- Có thể khai báo thuộc tính và phương thức cho từng giá trị.
- Thường dùng cho trạng thái, lựa chọn, loại (status, type, option,...).

**Ứng dụng:**  
Dùng cho các trường hợp như trạng thái của đơn hàng, ngày trong tuần, màu sắc, loại tài khoản,...

**Ví dụ:**

```kotlin
enum class Color {
    RED, GREEN, BLUE
}

val c = Color.RED
println(c.name)    // "RED" - tên enum
println(c.ordinal) // 0 - vị trí trong enum
```

**Enum nâng cao với thuộc tính và phương thức:**

```kotlin
enum class Direction(val degree: Int) {
    NORTH(0),
    EAST(90),
    SOUTH(180),
    WEST(270);

    fun turnRight(): Direction {
        return values()[(ordinal + 1) % values().size]
    }
}

val d = Direction.NORTH
println(d.degree)      // 0
println(d.turnRight()) // EAST
```

---

### 2.3 Sealed class

**Sealed class** là loại class dùng để giới hạn một tập hợp lớp con (subclass) cụ thể.  
Chỉ các lớp con được khai báo trong cùng file mới được phép kế thừa sealed class.

**Đặc điểm:**
- Sealed class không thể khởi tạo trực tiếp, chỉ khởi tạo qua các subclass.
- Dùng để biểu diễn các trạng thái, kết quả, hoặc các trường hợp trong luồng xử lý logic (pattern matching).
- Khi dùng với `when`, compiler sẽ cảnh báo nếu có trường hợp chưa xử lý.

**Ứng dụng:**  
Xử lý kết quả trả về (success, error), trạng thái UI, các loại sự kiện (event), v.v.

**Ví dụ:**

```kotlin
sealed class Result

data class Success(val data: String): Result()
data class Error(val error: Exception): Result()

fun handle(result: Result) {
    when (result) {
        is Success -> println("Thành công: ${result.data}")
        is Error -> println("Lỗi: ${result.error.message}")
        // Không cần else, vì tất cả subclass đã được liệt kê
    }
}
```

**Sealed class giúp kiểm soát luồng logic một cách toàn diện, tăng tính an toàn cho code.**

---

**Tóm lại về Special Class trong Kotlin:**
- **Data class:** Lưu trữ dữ liệu, tự động sinh các hàm tiện ích, so sánh theo giá trị.
- **Enum class:** Định nghĩa tập giá trị cố định, có thể bổ sung thuộc tính/hàm cho từng giá trị.
- **Sealed class:** Giới hạn tập lớp con, dùng cho xử lý trạng thái phức tạp, an toàn với `when`.