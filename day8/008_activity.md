# Buổi 8: Activity
- [Buổi 8: Activity](#buổi-8-activity)
  - [I. Foreground \& Background Application:](#i-foreground--background-application)
    - [a. Định nghĩa:](#a-định-nghĩa)
    - [b. Điều kiện:](#b-điều-kiện)
    - [c. Sơ đồ trạng thái của 1 ứng dụng:](#c-sơ-đồ-trạng-thái-của-1-ứng-dụng)
    - [d. Vòng đời của 1 application:](#d-vòng-đời-của-1-application)
  - [II. Context, Activity:](#ii-context-activity)
    - [a. Context:](#a-context)
    - [b. Activity:](#b-activity)
    - [c. Activity lifecycle:](#c-activity-lifecycle)
    - [d. Activity backstack](#d-activity-backstack)
    - [e. Launch mode:](#e-launch-mode)
  - [III. Intent:](#iii-intent)
    - [a. Intent](#a-intent)
    - [b. Cấu trúc của 1 intent:](#b-cấu-trúc-của-1-intent)
    - [c. Phân biệt các loại Intent:](#c-phân-biệt-các-loại-intent)
    - [d. Các Action:](#d-các-action)
    - [e. Các Flag khi dùng intent:](#e-các-flag-khi-dùng-intent)
  - [IV. Truyền dữ liệu giữa các activity:](#iv-truyền-dữ-liệu-giữa-các-activity)
    - [a. putExtra:](#a-putextra)
    - [b. putExtras:](#b-putextras)
    - [c. Activity for Results:](#c-activity-for-results)
  - [VII. Tìm hiểu thêm:](#vii-tìm-hiểu-thêm)
    - [a. Multi-resume:](#a-multi-resume)
    - [b. onTopResumedActivityChanged()](#b-ontopresumedactivitychanged)

---

## I. Foreground & Background Application:

### a. **Định nghĩa:**

* **Foreground Application**: Là ứng dụng đang **tương tác trực tiếp với người dùng**, tức là người dùng có thể nhìn thấy và tương tác với nó (ví dụ: một Activity đang hiện trên màn hình).
* **Background Application**: Là ứng dụng **không còn hiển thị trên màn hình** và **không tương tác trực tiếp với người dùng**, nhưng vẫn có thể đang thực hiện các tác vụ như dịch vụ chạy nền.

*Lý do tồn tại hai loại này là do thiết bị di động có màn hình nhỏ, chỉ hiển thị một ứng dụng tại một thời điểm, khác với máy tính có thể hiển thị nhiều cửa sổ cùng lúc.*

### b. **Điều kiện:**

Một ứng dụng được xem là **foreground** khi thỏa một trong các điều kiện sau:

* Có một **Activity hiển thị ra màn hình** (ngay cả khi Activity đó đã bị pause nhưng vẫn nhìn thấy được).
* Ứng dụng có **foreground service** đang chạy (dịch vụ ưu tiên, có thông báo hiện trên thanh trạng thái).
* Ứng dụng đang được một **foreground app khác kết nối tới**, thông qua:

  * **Service** được bind
  * **ContentProvider** được sử dụng bởi app foreground

Nếu không đáp ứng các điều kiện trên, thì ứng dụng được xem là **background**.

### c. Sơ đồ trạng thái của 1 ứng dụng:
```bash
 +------------------------+
 |    Ứng dụng KHÔNG chạy |
 | (Killed / Not running) |
 +-----------+------------+
             |
             | Người dùng khởi động app
             v
 +-----------+------------+
 |  Foreground Application|
 | (Activity hiển thị UI) |
 +-----------+------------+
             |
     +-------+---------------+
     |                       |
     | Người dùng            | Người dùng bấm Home hoặc mở app khác
     | tương tác             v
     | liên tục  +-----------+-------------+
     |           | Background Application |
     |           | (UI không hiển thị)    |
     |           +-----------+------------+
     |                        |
     |   Android có thể kill app để giải phóng bộ nhớ
     |                        v
     +---------------->+------------------------+
                       |    Ứng dụng KHÔNG chạy |
                       +------------------------+


```

### d. Vòng đời của 1 application:
- Application lifecycle: diễn ra ở cấp process của app.
- Bắt đầu từ lúc process được tạo → Kết thúc khi process bị hệ thống kill.
- Trong suốt vòng đời này:
  - `onCreate()`chỉ gọi một lần duy nhất khi process khởi động.
  - `onTerminate()` hầu như không bao giờ gọi trên thiết bị thật, chỉ có trong môi trường giả lập/testing.
  - `onLowMemory()` và `onTrimMemory()` gọi khi hệ thống cần giải phóng RAM.
  - `onConfigurationChanged()` gọi khi cấu hình thay đổi (xoay màn hình, đổi ngôn ngữ, thay đổi font size…).
- Chi tiết các callback này:
  - `onCreate()` – được gọi trước khi các thành phần đầu tiên của ứng dụng bắt đầu.
  - `onLowMemory()` – được gọi khi hệ thống Android yêu cầu ứng dụng dọn dẹp bộ nhớ.
  - `onTrimMemory()` – được gọi khi hệ thống Android yêu cầu ứng dụng dọn dẹp bộ nhớ. Lời gọi này bao gồm một hằng số chỉ ra trạng thái hiện tại của ứng dụng.
  Ví dụ: hằng số **TRIM_MEMORY_MODERATE** cho biết tiến trình (process) của ứng dụng đang ở khoảng giữa của danh sách LRU (Least Recently Used) trong background; việc giải phóng bộ nhớ lúc này có thể giúp hệ thống giữ được các tiến trình khác nằm ở phần sau của danh sách, giúp cải thiện hiệu năng tổng thể.
  - `onTerminate()` – chỉ dùng để kiểm thử, không được gọi trong môi trường production.
  - `onConfigurationChanged()` – được gọi bất cứ khi nào cấu hình thay đổi.

- LRU cache:
  - LRU hay Least Recently Used Cache là bộ đệm được HĐH Android sử dụng để đẩy các ứng dụng ít được sử dụng nhất trong thời gian gần. 
  - Ví dụ: nếu bạn đang chạy ứng dụng âm nhạc cùng với ứng dụng Email, Facebook, Instagram và Whatsapp thì ứng dụng mà bạn không sử dụng trong một thời gian dài sẽ được đặt ở vị trí đầu của bộ đệm và ứng dụng được sử dụng gần đây sẽ được đặt ở phía sau của hàng đợi bộ đệm LRU.
  - Ngoài ra, nếu ứng dụng được khởi động lại hoặc mở lại, thì nó sẽ được đặt lại ở phía sau trong hàng đợi của bộ đệm.
![alt text](image.png)
- Độ ưu tiên của Android Application:
  - Android gán mức ưu tiên cho tiến trình ứng dụng dựa trên trạng thái hoạt động, nhằm tối ưu **bộ nhớ** và **pin**. Thứ tự ưu tiên từ **cao → thấp**:

    - **Foreground process**
       * Người dùng đang trực tiếp tương tác.
       * Ví dụ: Xem video trên YouTube.
       * Ưu tiên cao nhất, khó bị kill.

    - **Visible process**
       * Hoạt động vẫn hiển thị nhưng không ở foreground (bị một activity khác phủ một phần hoặc dialog).
       * Ví dụ: Màn hình Instagram phía sau hộp thoại xin quyền.

    - **Service process**
       * Chạy nền để thực hiện tác vụ (download, upload…).
       * Ví dụ: Google Drive upload file ở background.

    - **Background process**
       * `onStop()` đã gọi, không hiển thị, nhưng được lưu trong bộ đệm LRU để khôi phục nhanh.
       * Ví dụ: Nhấn Home để ẩn app.

    - **Empty process**
       * Không còn thành phần hoạt động; chỉ tồn tại để cache.
       * Sẽ bị kill khi hệ thống cần thêm RAM.

---
## II. Context, Activity:
### a. Context:
- Context là 1 abstract class chứa thông tin toàn cục về môi trường ứng dụng và cách truy cập tài nguyên hệ thống. 
- Chức năng chính của context:
  - Truy cập tài nguyên (resources): hình ảnh, chuỗi, màu sắc… (getResources(), getString()…)
  - Tạo và chạy các component: startActivity(), startService()
  - Truy cập hệ thống: location, wifi, notification…
  - Lấy thông tin cấu hình ứng dụng (getPackageName(), getPackageManager()…)
![](https://i.ytimg.com/vi/S22NlX4iXJU/maxresdefault.jpg)
- Có 2 loại context chính:
  - Application context: 
    - Có thể truy cập tại bất cứ nơi đâu. Dùng để xử lý các tác vụ cần ở mức toàn ứng dụng, không phụ thuộc vào 1 Activity cụ thể nào.
    - Sử dụng: `getApplicationContext()`
    - VDu: Dùng để tạo singleton dùng chung trong app, dùng cho các class không gán với Activity.
  - Activity context:
    - Gán với 1 Activity cụ thể, chỉ hoạt động khi Activity hoạt động.
    - Sử dụng từ khóa `this`. Vdu: `Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show()`
    - Vdu: Dùng để inflate layout, chạy 1 Activity từ 1 Activity khác, ...

| Tiêu chí       | Application Context                  | Activity Context              |
| -------------- | ------------------------------------ | ----------------------------- |
| **Vòng đời**   | Sống cùng app                        | Sống cùng Activity            |
| **Theme / UI** | Không gắn với theme của Activity     | Gắn theme Activity            |
| **Dùng khi**   | Khi cần context chung, không dính UI | Khi thao tác UI hoặc tạo View |


### b. Activity:
- Activity là 1 component của ứng dụng, đại diện cho 1 màn hình giao diện người dùng (UI) và các chức năng đáp lại sự tương tác của người dùng. 
- Các chức năng chính của Activity:
  - Hiển thị UI cho người dùng
  - Xử lý sự kiện người dùng (click, nhập dữ liệu, vuốt, ...)
  - Quản lý vòng đời của màn hình (khi mở, tạm dừng, hủy, ...)
  - Chuyển dữ liệu giữa các Activity thông qua Intent
- 1 Activity được xây dựng bằng cách kế thừa từ lớp `Activity` thông qua `AppCompatActivity()`. Vdu:
```kotlin
package com.example.myapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapp.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textViewName.text = "Nguyễn Văn A"
        binding.buttonBack.setOnClickListener {
            finish() 
        }
    }
}
```
- Các Activity khác nhau không thể truy cập tới các phương thức hay dữ liệu của hàm khác. Ta phải sử dụng **Intent** từ Activity này sang Activity khác và **Content Provider**. 
```kotlin
val intent = Intent(this, ProfileActivity::class.java)
startActivity(intent)
```
- Đặc điểm:
  - 1 app thường có nhiều Activity và các Activity liên kết với nhau thông qua Intent
  - Vòng đời của 1 Activity bao gồm các phương thức: `onCreate(), onStart(), onResume(), onPause(), onStop(), onDestroy()`
  - Activity sẽ thiết lập người dùng qua `setContentView()` và xử lý các thao tác của người dùng.

### c. Activity lifecycle:
![](https://images.viblo.asia/4a53c8a8-f709-4153-925e-f1add8dccdb8.png)
- Hoạt động:
  - Bắt đầu khi ActivityLaunched -> Hệ thống đẩy vào BackStack -> Lần lượt callback tới `onCreate(), onStart(), onResume()`
  - Sau khi gọi hết các callback trên, lúc này Activity mới chính thức được xem là đang chạy.
  - Nếu có Activity khác đang chiếm quyền **foreground** thì Activity hiện tại rơi vào trạng thái `onPause`, nếu muốn quay về Activity cũ thì `onResume()` sẽ được gọi.
  - Nếu Activity rơi vào `onStop()` rồi mà người dùng muốn quay lại thì `onRestart()` sẽ được gọi.
  - Trong cả 2 trường hợp Activity rơi vào `onPause()` hoặc `onStop()` -> Rất dễ bị hệ thống thu hồi (kill) để giải phóng tài nguyên -> Nếu quay lại Activity cũ thì `onCreate()` sẽ được gọi thay vì `onResume()` hoặc `onRestart()`.
  - Nếu 1 Activity bị hủy có chủ đích -> `onDestroy()` sẽ được kích hoạt và Activity kết thúc vòng đời của nó.
- Các thời điểm trong activity lifecycle:
  - **Entire Lifetime**: Bắt đầu từ `onCreate()` -> `onDestroy()`. Thời gian tồn tại của Activity, từ khi khởi tạo đến khi bị hủy
  - **Visible Lifetime**: Bắt đầu từ `onStart()` -> `onStop()`. Khoảng thời gian Activity có thể nhìn thấy trên màn hình, dù có thể không tương tác được (ví dụ: bị một dialog che mất focus).
  - **Foreground Lifetime**: Bắt đầu từ `onResume()` -> `onPause()`. Khoảng thời gian Activity ở foreground và người dùng tương tác trực tiếp.
- Các callback chính:

<details>
<summary><strong>onCreate()</strong></summary>

Hàm này được gọi khá sớm, ngay khi Activity được kích hoạt và thậm chí người dùng còn chưa thấy gì cả thì callback này đã được gọi rồi.  
Ngoài ra, callback này chỉ được gọi **một lần duy nhất** khi Activity được khởi tạo.  
Nó có thể được gọi lại nếu hệ thống xóa Activity này đi để giải phóng tài nguyên, hoặc khi bạn xoay màn hình (ngang/dọc).  

**Công việc thường làm ở đây:**
- Load giao diện cho Activity bằng `setContentView()`
- Khởi tạo các logic chỉ chạy một lần ban đầu
- Gọi API, load database
- Tạo item list, Navigation Drawer...

</details>

<details>
<summary><strong>onStart()</strong></summary>

Sau khi gọi đến `onCreate()`, hệ thống sẽ gọi đến `onStart()`.  
Hoặc gọi lại sau `onRestart()` nếu Activity trước đó bị che khuất hoàn toàn và rơi vào `onStop()`.  

**Đặc điểm:**
- Activity **được nhìn thấy** nhưng **chưa tương tác được**.  
- Ít khi dùng, nhưng có thể tận dụng để chuẩn bị dữ liệu hiển thị.

</details>

<details>
<summary><strong>onResume()</strong></summary>

Được gọi khi:
- Activity vừa khởi tạo và đi qua `onStart()`
- Hoặc sau khi một Activity khác che đi, rồi quay lại Activity hiện tại  

**Đặc điểm:**
- Người dùng **đã nhìn thấy và tương tác được** với giao diện.  
- Được gọi nhiều lần trong vòng đời Activity.

**Ứng dụng:**
- Khôi phục tác vụ đang dang dở sau `onPause()`
- Ví dụ: Khi đang soạn nội dung mà có cuộc gọi đến → lưu tạm ở `onPause()` → khôi phục lại ở `onResume()`.

</details>


<details>
<summary><strong>onPause()</strong></summary>

Được gọi khi:
- Có thành phần khác che Activity hiện tại, người dùng **vẫn nhìn thấy nhưng không tương tác được** (ví dụ: popup, dialog).

**Đặc điểm:**
- Được gọi khá nhanh → chỉ nên lưu dữ liệu **nhỏ và nhanh**.  
- Nếu muốn lưu dữ liệu nặng hoặc gọi API, nên thực hiện ở `onStop()`.

</details>

<details>
<summary><strong>onStop()</strong></summary>

Được gọi khi:
- Activity **không còn nhìn thấy** nữa (bị che hoàn toàn hoặc app chạy nền).  

**Ứng dụng:**
- Lưu dữ liệu ứng dụng
- Giải phóng tài nguyên
- Ngưng các API đang chạy

**Lưu ý:**
- Đây chưa phải kết thúc Activity.  
- Nếu người dùng quay lại, sẽ gọi `onRestart()` → `onStart()`.

</details>

<details>
<summary><strong>onDestroy()</strong></summary>

Được gọi khi vòng đời Activity kết thúc.  
**Ứng dụng:**
- Giải phóng tài nguyên hệ thống mà `onStop()` chưa xử lý.
- Dọn dẹp cuối cùng trước khi Activity bị hủy.

</details>

### d. Activity backstack
Một **task** là một tập hợp các **activity** mà người dùng tương tác khi thực hiện một công việc nhất định. Các **activity** này được sắp xếp trong một ngăn xếp (stack), được gọi là **back stack**.
<details>
<summary><strong>Task</strong></summary>

- Một **task** là một tập hợp các hoạt động (activities) mà người dùng tương tác khi cố gắng thực hiện một việc gì đó trong ứng dụng
- Màn hình chính (Home screen) của thiết bị là nơi bắt đầu của hầu hết các **task**
- Khi người dùng chạm vào biểu tượng của một ứng dụng hoặc lối tắt trên trình khởi chạy ứng dụng hoặc màn hình chính, task của ứng dụng đó sẽ được đưa ra tiền cảnh (foreground).
- Nếu không có task nào tồn tại cho ứng dụng đó, một task mới sẽ được tạo và hoạt động chính của ứng dụng đó sẽ mở ra như hoạt động gốc (root activity) trong ngăn xếp (stack).
- Một task là một đơn vị gắn kết (cohesive unit) có thể di chuyển sang nền (background) khi người dùng bắt đầu một task mới hoặc về màn hình chính
</details>

<details>
<summary><strong>BackStack</strong></summary>

![](https://vncoder.vn/upload/img/post/images/post/2020_03_1/3e2b622c-1ed1-4a3f-a7c1-9a71d5a72467.png)
- Các hoạt động trong một task được sắp xếp trong một ngăn xếp được gọi là back stack theo thứ tự mỗi hoạt động được mở.
- Back stack hoạt động như một cấu trúc đối tượng "vào sau, ra trước" (last in, first out - LIFO).
- Các hoạt động trong ngăn xếp không bao giờ được sắp xếp lại; chúng chỉ được đẩy vào ngăn xếp khi chúng được hoạt động hiện tại khởi động và bị loại bỏ khỏi ngăn xếp khi người dùng loại bỏ thông qua nút hoặc cử chỉ Quay lại (Back)
</details>

### e. Launch mode:
- Launch Mode quyết định cách hệ thống tạo và quản lý Activity khi mở Activity, đặc biệt liên quan đến Task và Back Stack.
- Có 4 loại launch mode chính:

| Launch Mode               | Mô tả                                                                                                                                         | Khi nào dùng                                                                                 |
| ------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------- | -------------------------------------------------------------------------------------------- |
| **standard** *(mặc định)* | Mỗi khi start Activity → tạo **một instance mới** và đưa lên top của back stack.                                                              | Khi Activity có thể xuất hiện nhiều lần độc lập.                                             |
| **singleTop**             | Nếu Activity đã nằm trên top stack → **tái sử dụng instance đó** và gọi `onNewIntent()`. Nếu không → tạo mới.                                 | Dùng cho màn hình mà mở lại khi đã ở trên top thì không cần tạo mới (VD: màn hình chi tiết). |
| **singleTask**            | Nếu Activity đã tồn tại ở **bất kỳ vị trí nào** trong stack → **xóa tất cả Activity phía trên nó**, gọi `onNewIntent()`. Nếu không → tạo mới. | Dùng cho màn hình main/home của app.                                                         |
| **singleInstance**        | Activity chạy **trong một task riêng**, không có Activity khác chung stack.                                                                   | Dùng cho Activity đặc biệt, độc lập (VD: cuộc gọi đến, trình duyệt).                         |

- Cách khai báo:
`AndroidManifest.xml`
```xml
<activity
    android:name=".MainActivity"
    android:launchMode="singleTop" />
```
hoặc bằng Intent Flag (Run time)
```kt
val intent = Intent(this, MainActivity::class.java)
intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
startActivity(intent)
```
- Liên hệ với lifecycle:

| Launch Mode    | Khi Activity đã tồn tại    | Callback        |
| -------------- | -------------------------- | --------------- |
| standard       | Tạo mới                    | `onCreate()`    |
| singleTop      | Nếu trên top → tái sử dụng | `onNewIntent()` |
| singleTask     | Nếu tồn tại → clear top    | `onNewIntent()` |
| singleInstance | Luôn riêng task            | `onNewIntent()` |

---

singleTask + documentLaunchMode.

## III. Intent:
### a. Intent
- **Intent** là 1 object được sử dụng để gửi thông điệp giữa các thành phần của ứng dụng như: Activity, Service, BroadcastReceiver. Nó đóng vai trò như một cách để yêu cầu một hành động cụ thể, chẳng hạn như mở một Activity mới, bắt đầu một Service, hoặc phát sóng một thông báo.
- **Intents** có thể bao gồm dữ liệu thông qua **Bundle**. **Bundle** giống như một cái hộp. Bên nhận sẽ mở **bundle** ra nhờ key và lấy ra dữ liệu.

### b. Cấu trúc của 1 intent:
| Thành phần    | Mô tả                                                                           |
| ------------- | ------------------------------------------------------------------------------- |
| **Action**    | Hành động cần thực hiện (ví dụ: `ACTION_VIEW`, `ACTION_SEND`, `ACTION_DIAL`)    |
| **Data**      | Dữ liệu cần xử lý, thường là `Uri` (ví dụ: `content://contacts/people/1`)       |
| **Category**  | Phân loại thêm cho Intent (ví dụ: `CATEGORY_LAUNCHER`, `CATEGORY_BROWSABLE`)    |
| **Type**      | Kiểu MIME của dữ liệu (ví dụ: `image/png`, `text/plain`)                        |
| **Component** | Tên cụ thể của Activity/Service nhận Intent (ví dụ: `com.example.MainActivity`) |
| **Extras**    | Dữ liệu bổ sung dạng key–value (Bundle) gửi kèm                                 |

Vdu:
```kotlin
val intent = Intent().apply {
    // 1. Action
    action = Intent.ACTION_VIEW
    
    // 2. Data (URI)
    data = Uri.parse("https://www.google.com")
    
    // 3. Category
    addCategory(Intent.CATEGORY_BROWSABLE)
    
    // 4. Type (nếu cần)
    type = "text/plain"
    
    // 5. Component (nếu muốn chỉ rõ Activity đích)
    // component = ComponentName("com.example.app", "com.example.app.MainActivity")
    
    // 6. Extras
    putExtra("username", "john_doe")
    putExtra("age", 25)
}
startActivity(intent)
```

### c. Phân biệt các loại Intent:
- Explicit Intent (Intent tường minh)
  - Chỉ định rõ component nhận Intent.
  - Dùng để khởi chạy Activity/Service trong cùng ứng dụng.

```kotlin
val intent = Intent(this, SecondActivity::class.java)
intent.putExtra("message", "Hello")
startActivity(intent)
```
- Implicit Intent (Intent ngầm định)
  - Không chỉ định component → Hệ thống sẽ tìm Activity/Service phù hợp dựa trên action, data, type.

```kotlin
val intent = Intent(Intent.ACTION_VIEW)
intent.data = Uri.parse("https://www.google.com")
startActivity(intent)
```

### d. Các Action:

| STT | Activity Action Intent               | Miêu tả                                                        |
| --- | ------------------------------------ | -------------------------------------------------------------- |
| 1   | ACTION\_ALL\_APPS                    | Liệt kê tất cả ứng dụng có sẵn trên thiết bị                   |
| 2   | ACTION\_ANSWER                       | Xử lý một cuộc gọi đến                                         |
| 3   | ACTION\_ATTACH\_DATA                 | Gắn dữ liệu tới một vị trí khác                                |
| 4   | ACTION\_BATTERY\_CHANGED             | Sticky broadcast chứa trạng thái pin, mức độ và thông tin khác |
| 5   | ACTION\_BATTERY\_LOW                 | Thông báo pin yếu                                              |
| 6   | ACTION\_BATTERY\_OKAY                | Gửi sau ACTION\_BATTERY\_LOW khi pin đã ổn định                |
| 7   | ACTION\_BOOT\_COMPLETED              | Broadcast sau khi hệ thống khởi động xong                      |
| 8   | ACTION\_BUG\_REPORT                  | Mở Activity báo cáo lỗi                                        |
| 9   | ACTION\_CALL                         | Thực hiện cuộc gọi tới số trong Data                           |
| 10  | ACTION\_CALL\_BUTTON                 | Nhấn nút "call" để mở giao diện gọi                            |
| 11  | ACTION\_CAMERA\_BUTTON               | Nút Camera bị nhấn                                             |
| 12  | ACTION\_CHOOSER                      | Hiển thị Activity Chooser cho người dùng chọn                  |
| 13  | ACTION\_CONFIGURATION\_CHANGED       | Cấu hình thiết bị thay đổi (orientation, locale, …)            |
| 14  | ACTION\_DATE\_CHANGED                | Ngày đã thay đổi                                               |
| 15  | ACTION\_DEFAULT                      | Action mặc định (tương tự ACTION\_VIEW)                        |
| 16  | ACTION\_DELETE                       | Xóa dữ liệu khỏi Container                                     |
| 17  | ACTION\_DEVICE\_STORAGE\_LOW         | Thiết bị đang thiếu bộ nhớ                                     |
| 18  | ACTION\_DEVICE\_STORAGE\_OK          | Bộ nhớ thiết bị đã trở lại bình thường                         |
| 19  | ACTION\_DIAL                         | Mở quay số với số trong Data                                   |
| 20  | ACTION\_DOCK\_EVENT                  | Thay đổi trạng thái dock của thiết bị                          |
| 21  | ACTION\_DREAMING\_STARTED            | Hệ thống bắt đầu Dreaming                                      |
| 22  | ACTION\_DREAMING\_STOPPED            | Hệ thống dừng Dreaming                                         |
| 23  | ACTION\_EDIT                         | Cho phép chỉnh sửa dữ liệu                                     |
| 24  | ACTION\_FACTORY\_TEST                | Entry chính cho chế độ kiểm tra nhà máy                        |
| 25  | ACTION\_GET\_CONTENT                 | Cho phép chọn dữ liệu và trả về                                |
| 26  | ACTION\_GTALK\_SERVICE\_CONNECTED    | Đã kết nối GTalk                                               |
| 27  | ACTION\_GTALK\_SERVICE\_DISCONNECTED | Mất kết nối GTalk                                              |
| 28  | ACTION\_HEADSET\_PLUG                | Tai nghe được cắm hoặc rút                                     |
| 29  | ACTION\_INPUT\_METHOD\_CHANGED       | Phương thức nhập liệu thay đổi                                 |
| 30  | ACTION\_INSERT                       | Thêm item trống vào Container                                  |
| 31  | ACTION\_INSERT\_OR\_EDIT             | Chọn hoặc thêm item mới rồi chỉnh sửa                          |
| 32  | ACTION\_INSTALL\_PACKAGE             | Chạy installer ứng dụng                                        |
| 33  | ACTION\_LOCALE\_CHANGED              | Locale của thiết bị thay đổi                                   |
| 34  | ACTION\_MAIN                         | Main entry point, không nhận Data                              |
| 35  | ACTION\_MEDIA\_BUTTON                | Nút Media bị nhấn                                              |
| 36  | ACTION\_MEDIA\_CHECKING              | Media ngoài đang được kiểm tra                                 |
| 37  | ACTION\_MEDIA\_EJECT                 | Gỡ bỏ media ngoài                                              |
| 38  | ACTION\_MEDIA\_REMOVED               | Media ngoài bị xóa                                             |
| 39  | ACTION\_NEW\_OUTGOING\_CALL          | Chuẩn bị thực hiện cuộc gọi đi                                 |
| 40  | ACTION\_PASTE                        | Tạo item mới từ Clipboard                                      |
| 41  | ACTION\_POWER\_CONNECTED             | Nguồn được kết nối với thiết bị                                |
| 42  | ACTION\_REBOOT                       | Thiết bị khởi động lại (chỉ system code)                       |
| 43  | ACTION\_RUN                          | Chạy dữ liệu bất kể ý nghĩa                                    |
| 44  | ACTION\_SCREEN\_OFF                  | Màn hình tắt                                                   |
| 45  | ACTION\_SCREEN\_ON                   | Màn hình bật                                                   |
| 46  | ACTION\_SEARCH                       | Thực hiện tìm kiếm                                             |
| 47  | ACTION\_SEND                         | Gửi dữ liệu                                                    |
| 48  | ACTION\_SENDTO                       | Gửi tin nhắn tới địa chỉ trong Data                            |
| 49  | ACTION\_SEND\_MULTIPLE               | Gửi nhiều dữ liệu                                              |
| 50  | ACTION\_SET\_WALLPAPER               | Chọn wallpaper                                                 |
| 51  | ACTION\_SHUTDOWN                     | Thiết bị đang tắt                                              |
| 52  | ACTION\_SYNC                         | Đồng bộ dữ liệu                                                |
| 53  | ACTION\_TIMEZONE\_CHANGED            | Timezone thay đổi                                              |
| 54  | ACTION\_TIME\_CHANGED                | Thời gian đã được thiết lập                                    |
| 55  | ACTION\_VIEW                         | Hiển thị dữ liệu                                               |
| 56  | ACTION\_VOICE\_COMMAND               | Bắt đầu Voice Command                                          |
| 57  | ACTION\_WALLPAPER\_CHANGED           | Wallpaper đã thay đổi                                          |
| 58  | ACTION\_WEB\_SEARCH                  | Thực hiện tìm kiếm web                                         |


### e. Các Flag khi dùng intent:

| Flag                              | Miêu tả                                                                                                                |
| --------------------------------- | ---------------------------------------------------------------------------------------------------------------------- |
| `FLAG_ACTIVITY_NEW_TASK`          | Mở Activity mới trong một **task** mới (thường dùng khi start từ Context ngoài Activity, như Service).                 |
| `FLAG_ACTIVITY_CLEAR_TOP`         | Nếu Activity được gọi đã tồn tại trong back stack, tất cả Activity trên nó sẽ bị đóng và Activity đó được đưa lên top. |
| `FLAG_ACTIVITY_SINGLE_TOP`        | Nếu Activity ở trên cùng back stack và được gọi lại, **không tạo Activity mới**, chỉ gọi `onNewIntent()`.              |
| `FLAG_ACTIVITY_NO_HISTORY`        | Activity không được lưu vào back stack, thoát ra là mất luôn.                                                          |
| `FLAG_ACTIVITY_CLEAR_TASK`        | Xóa toàn bộ task chứa Activity hiện tại trước khi start Activity mới (thường dùng kèm NEW\_TASK).                      |
| `FLAG_GRANT_READ_URI_PERMISSION`  | Cấp quyền đọc URI cho Activity nhận Intent.                                                                            |
| `FLAG_GRANT_WRITE_URI_PERMISSION` | Cấp quyền ghi URI cho Activity nhận Intent.                                                                            |
| `FLAG_RECEIVER_FOREGROUND`        | Khi gửi broadcast, yêu cầu xử lý ở chế độ foreground, ưu tiên cao.                                                     |
| `FLAG_INCLUDE_STOPPED_PACKAGES`   | Gửi broadcast tới cả các ứng dụng đang ở trạng thái stopped.                                                           |
| `FLAG_EXCLUDE_STOPPED_PACKAGES`   | Gửi broadcast nhưng bỏ qua các ứng dụng đang stopped.                                                                  |

- Cách sử dụng:
  - Dùng `intent.flags = ` để gán 1 flag
  ```kotlin
  val intent = Intent(this, DetailActivity::class.java)
  intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
  startActivity(intent)
  ```
  - Dùng `addFlags(...)` để thêm flag:
  ```kotlin
  val intent = Intent(this, MainActivity::class.java)
  intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
  //addFlags sẽ thêm flag vào
  startActivity(intent)
  ```
  - Dùng `setFlags(...)` để ghi đè flag hiện tại (xoá các flag trước):
  ```kotlin
  val intent = Intent(this, HomeActivity::class.java)

  // Ghi đè toàn bộ flag của Intent, chỉ giữ FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TASK
  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)

  startActivity(intent)
  ```

---
## IV. Truyền dữ liệu giữa các activity:
### a. putExtra:
- Sử dụng phương thức: `putExtra(Key, Value)` với `key` là từ khóa để truy cập tới nội dung trong phương thức truyền và `value` là nội dung truyền
- Bên gửi:
```kotlin
val intent = Intent(this, SignUpActivity::class.java)
intent.putExtra("name", "Hai Nguyen")
startActivity(intent)
```
- Bên nhận:
```kotlin
val name = intent.getStringExtra("name")
```

### b. putExtras:
- Đóng gói các dữ liệu vào `Bundle`, khi cần lấy thì lấy từ `Bundle` ra:
- Bên gửi:
```kotlin
val intent = Intent(this, SignUpActivity::class.java)
val bundle = Bundle()
bundle.putString("name", "Hai Nguyen")
bundle.putString("email", "abc@gmail.com")
bundle.putString("password", "12345abcde")
startActivity(intent)
```
- Bên nhận:
```kotlin
val bundle = intent.extras
val name = bundle?.getString("name")
val email = bundle?.getString("email")
val password = bundle?.getString("password")
```

### c. Activity for Results:
- Là cơ chế cho phép 1 activity mở 1 activity khác và nhận dữ liệu trả về khi Activity kia kết thúc
- Cách cũ:
  - Dùng `startActivityForResult(intent, requestCode)` và nhận kết quả ở `onActivityResult()`
    ```kotlin
    startActivityForResult(Intent(this, SecondActivity::class.java), 100)

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 100 && resultCode == RESULT_OK) {
            val result = data?.getStringExtra("key")
        }
    }
    ```
- Cách mới:
  - Dùng `registerForActivityResult()` và `ActivityResultContracts`
    ```kotlin
    val launcher = registerForActivityResult(
      ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data?.getStringExtra("key")
        }
    }
    launcher.launch(Intent(this, SecondActivity::class.java))
    ```
  - Một số ActivityResultContracts thường dùng
    - `StartActivityForResult` → Mở Activity và nhận kết quả.
    - `RequestPermission` / `RequestMultiplePermissions` → Xin quyền.
    - `TakePicture` / `TakeVideo` → Chụp ảnh, quay video.
    - `PickContact` → Chọn contact
- Lý do cần thay đổi:
  - Quản lý vòng đời tốt hơn:
    - `startActivityForResult()` phụ thuộc vào callback `onActivityResult()` của **Activity/Fragment**, và callback này luôn được gọi kể cả khi Activity vừa bị hủy và tạo lại (rotation, backstack restore…). Điều này khiến ta phải tự lưu state để tránh crash hoặc xử lý sai.
    - `registerForActivityResult()` tự động gắn với vòng đời của Activity/Fragment, chỉ chạy callback khi component còn tồn tại → tránh lỗi memory leak hoặc callback bị gọi nhầm.
  - Dễ dàng bảo trì:
    - `startActivityForResult()` cần requestCode để phân biệt nhiều kết quả → dễ nhầm lẫn, code rối.
    - `registerForActivityResult()` định nghĩa callback ngay tại chỗ → không cần requestCode, dễ đọc và maintain.
  - Hỗ trợ mạnh cho Fragment:
    - Với API cũ, khi dùng trong Fragment, `onActivityResult()` của Fragment có thể không được gọi nếu kết quả trả về cho Activity.
    - API mới đảm bảo callback đúng vị trí bạn đăng ký (Activity hoặc Fragment) → không cần override `onActivityResult` ở cha.

---
## VII. Tìm hiểu thêm:
### a. Multi-resume:
- **Lý do ra đời tính năng:** Android đã có tính năng chia đôi màn hình từ rất lâu và 2 ứng dụng khác nhau chạy trên màn hình chia đôi đã được ứng dụng trên nhiều dòng điện thoại. Vậy nhưng có 1 vấn đề quan trọng là thực tế dù là màn hình chia đôi nhưng thực chất chỉ có 1 bên màn hình là thực sự hoạt động và màn hình còn lại sẽ ở chế độ pause -> Điều này sẽ gây ra sự bất tiện khi mà 1 cửa sổ đang chat, 1 cửa sổ đang xem phim thì nếu dùng màn tin nhắn thì phim sẽ bị dừng hay ngược lại. Chính vì vậy người ta đã sinh ra tính năng Multi-Resume trên Android Q hay là Android 10 (không chỉ dừng lại chia đôi màn hình).
- Trước android Q thì chỉ có 1 acitivity ở trạng thái **resumed** tại 1 thời điểm, các activity khác trong chế độ multi-window sẽ bị **paused**.
- Khi sử dụng tính năng `multi-resume`, nhiều **Activity** có thể ở trạng thái **Resumed** -> cho phép hoạt động và tương tác đầy đủ với người dùng
![](https://source.android.com/static/docs/core/display/images/multi_display_01a.png)

=> Để sử dụng `multi-resume` ta sẽ dùng callback `onTopResumedActivityChanged()`

### b. onTopResumedActivityChanged()
```kotlin
override fun onTopResumedActivityChanged(isTopResumedActivity: boolean) {
    super.onTopResumedActivityChanged(isTopResumedActivity);
    if (isTopResumedActivity) {
        // Activity này là top-most activity, đang hiển thị và nhận input chính
    } else {
        // Activity vẫn RESUMED nhưng không phải cửa sổ chính
    }
}
```
- Ý nghĩa:
  - Trong multi-resume, nhiều Activity có thể ở trạng thái RESUMED.
  - Nhưng chỉ 1 Activity duy nhất là “top-resumed” → nhận focus bàn phím, phản ứng với back button, và một số event ưu tiên khác.
  - Callback này cho biết khi Activity được hoặc mất vị trí top-resumed.
- Phân biệt rõ `RESUMED` và `TOP-RESUMED`:
  - `RESUMED`:  đang hiển thị và chạy logic UI
  - `TOP-RESUMED`:  activity foreground thực sự, nhận focus & input chính.

