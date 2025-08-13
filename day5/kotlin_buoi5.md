# Buổi 5: Object & Callback
- [Buổi 5: Object \& Callback](#buổi-5-object--callback)
  - [I. Object:](#i-object)
    - [1. Object Declaration (Khai báo object):](#1-object-declaration-khai-báo-object)
      - [a. Mục đích và cách sử dụng:](#a-mục-đích-và-cách-sử-dụng)
      - [b. Data object:](#b-data-object)
      - [c. Companion object:](#c-companion-object)
    - [2. Object Expression (Biểu thức object):](#2-object-expression-biểu-thức-object)
    - [3. Sự khác biệt:](#3-sự-khác-biệt)
    - [4. Hình thái tương đương trong Java:](#4-hình-thái-tương-đương-trong-java)
  - [II. Callback :](#ii-callback-)
    - [1. High-order function:](#1-high-order-function)
    - [2. Function type:](#2-function-type)
    - [3. Lambda function:](#3-lambda-function)
    - [4. Callback:](#4-callback)

## I. Object:
- Object cho phép định nghĩa 1 class và khởi tạo 1 instance của lớp đó trong 1 bước duy nhất -> Hữu ích khi cần **1 thể hiện singleton có thể tái sử dụng** hoặc **1 đối tượng dùng 1 lần**.
- Để xử lý các trường hợp đó, Kotlin cung cấp 2 cách tiếp cận:
  - **Khai báo Object (Object Declaration):** Phù hợp cho các đối tượng toàn cục, tái sử dụng (như singleton)
  - **Biểu thức Object (Object Expression):** Thích hợp cho các đối tượng ngắn hạn, dùng 1 lần (như anonymous, one-time objects).
- Object phù hợp trong các trường hợp:
  - **Sử dụng singleton cho tài nguyên dùng chung**: Khi cần đảm bảo chỉ có **1 instance duy nhất của 1 lớp** tồn tại trong **toàn bộ ứng dụng**.
  - **Tạo phương thức Factory:** Cần 1 cách tiện lợi để khai báo các instance 1 cách hiệu quả. Các **companion object** cho phép định nghĩa hàm và thuộc tính cấp class-level gắn liền với lớp -> Đơn giản hóa việc tạo và quản lý
  - **Thay đổi hành vi của lớp hiện tại 1 cách tạm thời:** Bổ sung hành vi tạm thời cho 1 lớp mà không cần tạo 1 subclass mới
  - **Thiết kế type-safe**: Cần những **implements tạm thời** cho các interface hoặc abstract class bằng cách dùng object expression. (VD như xử lý sự kiện click button với 1 đối tượng dùng 1 lần, an toàn kiểu).
### 1. Object Declaration (Khai báo object):
- Được khai báo, khởi tạo trong lần truy cập đầu tiên.
#### a. Mục đích và cách sử dụng:
- Mục đích: Tạo ra các singleton. Một singleton là 1 đối tượng đảm bảo **1 lớp chỉ có 1 instance** và cung cấp 1 điểm truy cập toàn cục đến nó.
- Cách sử dụng:
  - **Khai báo** 1 singleton bằng từ khóa `object`:
  - **Truy cập** tới 1 object bằng cách `<Tên object>.<Phương thức,...>`:
  - **Phạm vi: Không thể là local**, tức là không thể lồng trực tiếp bên trong 1 hàm, vậy nhưng có thể **lồng trong các Object Declaration khác** hoặc trong các class không phải là `inner class`
  -  **Kế thừa:** Object declaration có thể có các kiểu cha (supertypes), tương tự như các đối tượng ẩn danh (anonymous objects) có thể kế thừa từ các lớp hiện có hoặc triển khai các interface
  ```kotlin
  interface RuleProvider {
        fun provideRules(): List<String>
    }

    // Object singleton kế thừa interface
    object SchoolRuleProvider : RuleProvider {
        private val rules = listOf(
            "Arrive on time.",
            "Wear the school uniform.",
            "No phones during class.",
            "Respect teachers and classmates."
        )

        override fun provideRules(): List<String> = rules
    }

    object RuleManager {
        private val providers = mutableListOf<RuleProvider>()

        fun registerProvider(provider: RuleProvider) {
            providers.add(provider)
        }

        val allRules: List<String>
            get() = providers.flatMap { it.provideRules() }

        //Lồng 1 object declaration bên trong object khác
        object Logger {
            fun log(message: String) {
                println("Log: $message")
            }
        }
    }

    class AppRunner {
    //    inner class tmp () {
    //        object TestObject { } // Không thể khai báo object trong 1 inner class
    //    }
        fun run() {
            // Truy cập phương thức object RuleManagerr
            RuleManager.registerProvider(SchoolRuleProvider)

            RuleManager.Logger.log("Registered SchoolRuleProvider")

            println("=== School Rules ===")
            RuleManager.allRules.forEachIndexed { index, rule ->
                println("${index + 1}. $rule")
            }
        }
    }

    fun main() {
        val runner = AppRunner()
        runner.run()

    //     val obj = object MySingleton { val a = 1 } //Sai vì tạo khai báo object chứ không phải biểu thức object
    }
  ```
#### b. Data object:
- Ta có thể đánh dấu 1 object declaration với từ khóa `data`. Khi đó sẽ có sẵn các phương thức: 
  - `toString()`: trả về tên của object. Data object sẽ tự sử dụng hàm này khi gọi tới:
  ```kotlin
    object MyObject

    data object MyDataObject {
        private val name: String = "Hai"
    //    override fun toString(): String {
    ////        return "$name $this"
    //        return "$name ${this::class.simpleName}"
    //    }
    }

    fun main() {
        println(MyObject) //Name object + hashcode: MyObject@6f496d9f
        println(MyDataObject) //Name data object: MyDataObject
    }
  ```
  - `equals`/`hashCode`: Dùng để phân biệt giữa các object. Không thể override lại 2 hàm này. Khi cần so sánh, cần phân biệt rõ:
  ```kotlin
    println(obj1 == obj2)   // So sánh nội dung → true
    println(obj1 === obj2)  // So sánh tham chiếu → false (có thể)
  ```
  => Luôn dùng `==` để so sánh `data object`, không dùng `===`
    - Việc không thể override giúp đảm bảo mọi instance cùng 1 data object luôn được coi là bằng nhau về mặt logic.
#### c. Companion object:
- 1 khai báo Object Declaration bên trong 1 class được đánh dấu bằng từ khóa `companion`.
- Các member được gọi bằng tên của class chứa object
- 1 class chỉ có 1 companion object.
```kotlin
class User(val name: String) {
    companion object Factory {
        fun create(name: String): User = User(name)
    }
}

fun main(){
    val userInstance = User.create("John Doe")
    println(userInstance.name)
}
```
- Tên của companion object có thể được bỏ qua, trong trường hợp này tên Companion sẽ được sử dụng mặc định
```kotlin
class User(val name: String) {
    companion object { }
}
fun main() {
    val companionUser = User.Companion
}
```
- Các member của class có thể truy cập tới các member private của object
```kotlin
class User(val name: String) {
    companion object {
        private val defaultGreeting = "Hello"
    }

    fun sayHi() {
        println(defaultGreeting)
    }
}
User("Nick").sayHi()
```
- Mặc dù các member của `companion object` trong Kotlin trông giống như các thành viên `static` từ các ngôn ngữ khác, chúng thực sự là các instance members của chính `companion object` -> Vẫn có thể kế thừa, implement,.... 
```kotlin
interface Factory<T> {
    fun create(name: String): T
}

class User(val name: String) {
    // Defines a companion object that implements the Factory interface
    companion object : Factory<User> {
        override fun create(name: String): User = User(name)
    }
}

fun main() {
    // Uses the companion object as a Factory
    val userFactory: Factory<User> = User
    val newUser = userFactory.create("Example User")
    println(newUser.name)
    // Example User
}
```

### 2. Object Expression (Biểu thức object):
- Tạo 1 object (khai báo 1 lớp và tạo instance) nhưng **không đặt tên** của lớp lẫn instance. Các instance này được gọi là **anonymous objects** vì chúng được định nghĩa bằng biểu thức chứ không phải 1 tên cụ thể.
- Mục đích: Tạo ra các đối tượng chỉ sử dụng một lần (one-time objects)
- **Scratch:**
  - Bắt đầu bằng từ khóa `object`. Nếu đối tượng không kế thừa bất kỳ class nào hoặc implements interface nào, ta có thể định nghĩa các member của  object trực tiếp bên trong dấu ngoặc nhọn `{}` sau từ khóa `object`
  ```kotlin
  val helloWorld = object {
    val hello = "Hello"
    val world = "world"
    override fun toString() = "$hello $world"
  }
  print(helloWorld)
  ```
- Kế thừa từ `supertype` hoặc triển khai `interface`:
  - Tạo 1 object kế thừa từ 1 hoặc nhiều kiểu, chỉ định kiểu đó sau từ khóa object và dấu `:`. Sau đó triển khai ghi đè các member
  - Có thể truyền tham số constructor phù hợp cho supertype nếu có constructor
  - Ngăn cách các supertype bằng dấu `,`
  ```kotlin
    open class MyClass(name: String) {
        open val name: String = name
    }

    interface Human {
        fun show()
    }

    fun main() {
        val phuc = object : MyClass("Phuc"), Human {
            override val name = "Object Phuc"
            override fun show() {
                println("After object: $name")
            }
        }
        phuc.show()
    }
  ```

- Sử dụng biểu thức object làm kiểu trả về và kiểu giá trị:
  - Khi sử dụng `anonymous object` khai báo `local` hoặc `private`, all `member` đều có thể được truy cập thông qua hàm, thuộc tính
  ```kotlin
    class Human {
        private fun getObject1() = object {
            val str: String = "tmp1"
        }
        
        public fun getObject2() = object {
            val str: String = "tmp2"
        }
        fun show() {
            println(getObject1().str)
            println(getObject2().str) //Unresolved reference: str
        }
    }
  ```
  - Nếu các hàm, thuộc tính là `public`, `protected` hoặc `internal` thì kiểu thực sự của nó là:
    - `Any` nếu object không có supertype
    - Nếu có `1 supertype` được khai báo thì chính là supertype đó
    - Nếu có nhiều `supertype` -> Phải khai báo `type` rõ ràng
    ```kotlin
    interface A {
        fun a()
    }
    interface B {
        fun b()
    }

    // Sai vì không khai báo kiểu trả về
    // fun createAB() = object : A, B {
    //     override fun a() = println("A")
    //     override fun b() = println("B")
    // }

    fun createAB(): Any {
        return object : A, B {
            override fun a() = println("A")
            override fun b() = println("B")
        }
    }
    ```
  - Các member được thêm vào `anonymous object` đều không thể truy cập đươc. Các member bị ghi đè mới có thể truy cập được
  ```kotlin
    interface A {
        fun a()
    }
    interface B {
        fun b()
    }

    class Tmp {
        fun getObject() = object {
            val x: String = "x"
        }

        fun getObjectA() = object: A {
            override fun a() {}
            val x: String = "x"
        }

        val a = getObjectA().a()
    //    val b: String = getObjectA().x

    // Trả về kiểu B, không thể truy cập tới a() và x
        fun getObjectB(): B = object: A, B { // explicit return type is required
            override fun a() {}
            override fun b() {}
            val x: String = "x"
        }
    //    fun a2() = getObjectB().a() 
        fun b() = getObjectB().b()
    //    fun x2 = getObjectB().x
    }
  ```
### 3. Sự khác biệt:
Điểm khác biệt ở đây là về hành vi khởi tạo:
- Biểu thức object: Được khởi tạo ngay lập tức tại nơi nó sử dụng
- Khai báo object: Khởi tạo khi được truy cập lần đầu tiên
- Companion object: Khởi tạo khi lớp tương ứng được tải (tương đương khởi tạo tĩnh bên java)

### 4. Hình thái tương đương trong Java:
- Object declaration giống singleton trong Java:
  ```kotlin
    object MySingleton {
        fun greet() = "Kotlin"
    }
  ```
  ```java
    public class MySingleton {
        private static final MySingleton INSTANCE = new MySingleton();

        private MySingleton() {}

        public static MySingleton getInstance() {
            return INSTANCE;
        }

        public String greet() {
            return "Hello";
        }
    }
  ```
- Object expression giống Anonymous class trong java
  ```kotlin
    val obj = object : Runnable {
        override fun run() {
            println("Running")
        }
    }
  ```
  ```java
    Runnable obj = new Runnable() {
        @Override
        public void run() {
            System.out.println("Running");
        }
    };
  ```
  - Companion object giống như Static member trong Java
  ```kotlin
    class MyClass {
        companion object {
            fun greet() = "Hi from companion"
    }
  ```
  ```java
    public class MyClass {
        public static String greet() {
            return "Hi from companion";
        }
    }
  ```

## II. Callback :
### 1. High-order function:
Nhận 1 function như 1 param **hoặc** có thể trả về 1 function:
  ```kotlin
  fun main() {
    val sum2 = { a: Int, b: Int -> Int
        a + b
    }
    println(sum2.invoke(2, 2))
    println(operator2Number(1, 1, sum2))
    println(operator2Number(3, 4, ::multiply2))
  }

  fun operator2Number(a: Int, b: Int, operator: (Int, Int) -> Int): Int {
      return operator.invoke(a, b)
  }

  fun multiply2(a: Int, b: Int): Int = a * b
  ```
### 2. Function type:
- Sử dụng các Function types (vdu: `(Int, Int) -> Double`) để đại diện cho các hàm. Tức là có thể khai báo biến hoặc tham số có kiểu là 1 hàm `val onClick() -> Unit = {}`
- Cú pháp:
  - `(A, B) -> C`: Nhận vào 2 đối số kiểu A, B và trả về kiểu C. Danh sách params có thể trống `() -> C`. Không thể bỏ qua kiểu trả về Unit như hàm bình thường `(A, B) -> Unit`. 
  ```kotlin
    val f1: () -> Unit = { println("Hello") }
    val f2: () -> Int = { 5 }
    val f3: () -> { println("Hi") } //ERROR: Ko trả về Unit

    fun sayHi() { println("Hi") }
    fun sayHello(): Unit { println("Hello")}
  ```
  - `A.(B) -> C`: biểu thị một hàm có thể được gọi trên một đối tượng receiver kiểu A với tham số B và trả về giá trị C. 
- 1 số đặc điểm:
  - Hàm `suspend`: dùng để đánh dấu hàm tạm dừng mà không cần chặn luồng hiện tại
  ```kotlin
    suspend fun fetchData(): String {
        delay(1000) // Giả lập lấy dữ liệu mất 1 giây (không chặn luồng)
        return "Dữ liệu đã tải xong"
    }
  ```
  - Tùy chọn để tên tham số: `(x: Int, Int) -> Point`
  - Function type nullable: `((Int, Int) -> Int)?`
  - Kết hợp kiểu hàm: Các kiểu hàm cũng có thể được kết hợp bằng cách sử dụng dấu ngoặc đơn, ví dụ: (Int) -> ((Int) -> Unit)
  ```kotlin
  fun main() {
      val f: (Int) -> ((Int) -> Unit) = { a ->
          { b ->
              println("Sum: ${a + b}")
          }
      }

      val hamCon = f(5)
      hamCon(3)

      f(2)(7)
  }
  ```
  - Đặt tên thay thế cho 1 function type bằng cách sử dụng `typealias`: `typealias ClickHandler = (Button, ClickEvent) -> Unit`
  ```kotlin
  import java.awt.Button

  typealias ClickHandler = (Button) -> Unit

  fun handle(click: ClickHandler) {
      val button = Button("Click me")
      click(button)
  }

  fun main() {
      handle { btn ->
          println("Button ${btn.label} clicked")
      }
  }
  ```
- Khởi tạo instance của 1 `function type`:
  - Sử dụng khối mã bên trong 1 `function literal`, theo 1 trong 2 dạng sau:
    - Biểu thức lambda: 
    ```kotlin
    val sum = { a: Int, b: Int -> 
        a + b
    }
    ```
    - Anonymous function:
    ```kotlin
    fun (s: String): Int { return s.toIntOrNull() ?: 0 }
    ```
    Function literal với receiver có thể được sử dụng như giá trị function type có receiver
  - Sử dụng tham chiếu `function reference` để tham chiếu tới 1 hàm hiện có
    Sử dụng toán tử `::<Tên function>`, kể cả đối với các hàm overload

- Gọi 1 instance `function type`:
  - 1 giá trị của kiểu hàm được gọi bằng cách sử dụng toán tử `invoke(..)` (f.invoke(x)) hoặc f(x)
  - Nếu giá trị có kiểu receiver, đối tượng receiver nên được truyền làm đối số đầu tiên hoặc gọi như 1 hàm mở rộng (1.foo(2))
  ```kotlin
    val stringPlus: (String, String) -> String = String::plus
    val intPlus: Int.(Int) -> Int = Int::plus

    println(stringPlus.invoke("<-", "->"))
    println(stringPlus("Hello, ", "world!"))

    println(intPlus.invoke(1, 1))
    println(intPlus(1, 2))
    println(2.intPlus(3))
  ```
- Mối quan hệ với high-order function & lambda function:
  - Các kiểu hàm là nền tảng cho Hàm Bậc Cao (Higher-order Functions). Một hàm bậc cao là một hàm nhận các hàm khác làm tham số hoặc trả về một hàm. Kiểu hàm xác định "signature" (chữ ký) của các hàm này
  - Biểu thức lambda (Lambda expressions) là cách được sử dụng rộng rãi nhất để truyền một instance của kiểu hàm làm đối số cho các hàm bậc cao. Chúng là các "function literal" (hàm literal) – các hàm không được khai báo theo cách thông thường mà được truyền trực tiếp như một biểu thức
### 3. Lambda function:
- Lambda function là 1 dạng function literal (như trên). Chúng không có tên -> đgl Anonymous function.
- Cú pháp đầy đủ:
`val lambdaName: (paramsType) -> returnType = { argumentList -> functionBody }`
- Lambda function dùng để gán cho 1 hàm bình thường chứ không thể khai báo như 1 hàm dưới dạng `fun`
Ví dụ:
```kotlin
fun main() {
    val sum2: (Int, Int) -> Int = { a: Int, b: Int -> Int
        a + b
    }
    /*Rút gọn:
    val sum2 = {a: Int, b: Int -> Int 
        a + b
    }
    */
    println(sum2.invoke(2, 2))
}
```
- Tham số duy nhất (`it`): Nếu một biểu thức lambda chỉ có một tham số, tham số đó không cần được khai báo và toán tử -> có thể được bỏ qua. Tham số đó sẽ được khai định ngầm định dưới tên it. Ví dụ: `ints.filter { it > 0 }`
```kotlin
fun main() {
    val sqr: (Int) -> Int = { it * it }
    println(sqr(4))
}
```
- Dấu gạch dưới cho biến không sử dụng: Nếu một tham số lambda không được sử dụng, ta có thể đặt dấu gạch dưới `_` thay cho tên của nó. Ví dụ: 
```kotlin
fun main() {
    val map = mapOf("a" to 1, "b" to 2, "c" to 3)

    map.forEach { (_, value) ->
        println("Giá trị: $value")
    }
}
```
- **Trailing Lambda** (Lambda cuối cùng): Theo quy ước Kotlin, nếu tham số cuối cùng của một hàm là một hàm (một kiểu hàm), thì biểu thức lambda được truyền làm đối số tương ứng có thể được đặt bên ngoài dấu ngoặc đơn của lời gọi hàm 
```kotlin
fun main() {
    val items = listOf(1, 2, 3, 4)

    val tich = items.fold(1) { acc, e -> acc * e }

    println("Tích: $tich")
}
```
- Nếu lambda là đối số duy nhất trong lời gọi hàm đó, dấu ngoặc đơn có thể được bỏ qua hoàn toàn. Ví dụ: `run { println("...") }`.
```kotlin
fun <R> run(block: () -> R): R {
    return block() 
}

fun main() {
    run {
        println("Run code block")
    }

    val thongBao = run {
        val ten = "Hai"
        "Xin chao, $ten!"
    }

    println(thongBao)
}
```
- **Giá trị trả về:**
  - Không có return: Trả về dòng cuối của lambda
  - Return rõ ràng
- **So sánh với Hàm Ẩn danh** (Anonymous Functions): 
    Hàm ẩn danh cũng là một loại hàm literal và trông rất giống một khai báo hàm thông thường, nhưng không có tên. Sự khác biệt chính giữa lambda expression và anonymous function là:
    - Chỉ định kiểu trả về: Cú pháp lambda expression không cho phép chỉ định kiểu trả về một cách tường minh, nhưng hàm ẩn danh thì có. Điều này hữu ích khi bạn cần kiểu trả về được chỉ định rõ ràng mà không thể suy luận được.
    - Đặt vị trí khi truyền tham số: Hàm ẩn danh được truyền làm tham số phải nằm bên trong dấu ngoặc đơn, trong khi cú pháp shorthand của lambda cho phép đặt nó bên ngoài (trailing lambda).
    - Hành vi của return: Lệnh return không có nhãn bên trong một biểu thức lambda sẽ trả về từ hàm bao quanh (enclosing function), trong khi return bên trong một hàm ẩn danh sẽ trả về từ chính hàm ẩn danh đó

### 4. Callback:
- Hàm callback là một hàm được truyền làm đối số cho một hàm khác. Trong quá trình thực thi, hàm `callback` có thể được gọi vào thời điểm thích hợp. *(Phải gọi lại hàm truyền vào mới là callback)*
- Hàm callback là ứng dụng của 1 function type. Muốn sử dụng lambda thì phải để callback ở vị trí cuối cùng.
```kotlin
fun doOperation(a: Int, b: Int, callback: (Int) -> Unit) {
    val result = a + b
    callback(result)
}

fun main() {
    doOperation(5, 3) { result ->
        println("Kết quả là: $result")
    }
}
```
- Phù hợp với các mục đích:
  - **Minh họa các khái niệm lập trình hàm** (Functional Programming): Trong lập trình hàm, **các hàm có thể được sử dụng giống như các biến**. Chúng ta có thể gán các hàm ẩn danh cho các biến
  - **Hàm bậc cao** (Higher-Order Functions): Chúng ta có thể truyền các hàm vào các hàm khác, tạo ra các hàm bậc cao
  - **Lập trình bất đồng bộ** (Asynchronous Programming): chia nhỏ việc thực thi một chương trình trên nhiều luồng chạy độc lập với nhau, giúp tránh chặn luồng chính (main thread) bằng cách đẩy một số đoạn mã thực thi vào các luồng nền hoặc luồng worker
  - Phù hợp cho việc **xử lý sự kiện (event handling)**, nơi một sự kiện đại diện cho sự thay đổi trạng thái của một đối tượng bằng cách thực hiện một hành động, chẳng hạn như xử lý đầu vào của người dùng hoặc lên lịch hẹn giờ
- Callback hell:
  - Hiện tượng xảy ra khi lồng ghép nhiều hàm callback được gọi là "callback hell"
  - "Callback hell" làm cho mã trở nên khó hiểu và khó bảo trì.
  - Nó thường được gọi là "kim tự tháp địa ngục" (pyramid of doom) do hình dạng tam giác mà các khoảng thụt lề từ các callback lồng sâu tạo ra
- Để khắc phục các nhược điểm của `callback hell`, các giải pháp như `Coroutines` với hàm `suspend`, `Futures/Promises` và `Reactive Extensions` (Rx) đã được phát triển để cung cấp cách tiếp cận lập trình bất đồng bộ hiệu quả hơn