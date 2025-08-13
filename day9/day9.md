# Giới thiệu về Fragments | Android
## Fragment
Một Fragment(activity con) đại diện cho một phần giao diện người dùng (UI) có thể tái sử dụng trong ứng dụng của bạn. Một fragment định nghĩa và quản lý bố cục (layout) riêng của nó, có vòng đời (lifecycle) riêng, và có thể xử lý các sự kiện đầu vào của riêng nó. Fragment không thể tồn tại độc lập; chúng phải được chứa bên trong một Activity hoặc một Fragment khác. Cây phân cấp view (view hierarchy) của fragment sẽ trở thành một phần của, hoặc gắn vào, cây phân cấp view của thành phần chứa nó.

Lưu ý: Một số thư viện Android Jetpack như Navigation, BottomNavigationView, và ViewPager2 được thiết kế để hoạt động cùng với fragment.
*minh họa* 
![Use cases of Fragments through navigations](https://media.geeksforgeeks.org/wp-content/uploads/20250129124318223545/fragments.png)
Hình dưới đây cho thấy các trường hợp sử dụng của Fragment thông qua các hình thức điều hướng.
## **Modularity**



**Mô-đun (Modularity) của Fragment**
Fragment cho phép chia nhỏ giao diện của một Activity thành nhiều phần độc lập, giúp dễ dàng tái sử dụng và quản lý.

* **Vai trò của Activity và Fragment**

    * Activity thường chứa các thành phần giao diện chung (ví dụ: thanh điều hướng bên, thanh điều hướng dưới).
    * Fragment đảm nhận phần nội dung của một màn hình hoặc một phần màn hình.

* **Ưu điểm khi tách Activity và Fragment**

    * Trên màn hình lớn: có thể dùng **navigation drawer** + danh sách dạng lưới.
    * Trên màn hình nhỏ: có thể dùng **bottom navigation bar** + danh sách dạng dọc.
    * Có thể dễ dàng thay đổi, thêm, thay thế hoặc xóa Fragment khi Activity đang từ trạng thái **STARTED** trở lên.
    * Các thay đổi có thể được lưu trong **back stack** để hoàn tác khi cần.

* **Tính linh hoạt**
    * Có thể tạo nhiều bản sao (instance) của cùng một Fragment trong một hoặc nhiều Activity.
    * Có thể lồng Fragment bên trong Fragment khác.
    * Mỗi Fragment chỉ nên quản lý UI của chính nó, không phụ thuộc trực tiếp vào Fragment khác.
---

![a](https://developer.android.com/static/images/guide/fragments/fragment-screen-sizes.png)
## Fragment manager


`FragmentManager` chịu trách nhiệm quản lý vòng đời và back stack của các fragment. Trong quá trình ứng dụng chạy, `FragmentManager` có thể thực hiện các thao tác như thêm, thay thế hoặc xóa fragment để phản hồi các tương tác của người dùng. Tất cả những thay đổi này được gom lại và thực hiện đồng thời dưới dạng một đơn vị duy nhất gọi là **FragmentTransaction**.

Khi người dùng nhấn nút **Back** trên thiết bị hoặc khi gọi `FragmentManager.popBackStack()`, transaction ở vị trí trên cùng của back stack sẽ bị loại bỏ. Nếu back stack trống và bạn không sử dụng **child fragments**, sự kiện Back sẽ được chuyển lên Activity. Ngược lại, nếu có sử dụng child fragments, bạn cần lưu ý đến cách xử lý riêng cho child và sibling fragments.

Một transaction có thể bao gồm nhiều thao tác khác nhau như thêm nhiều fragment hoặc thay thế fragment ở nhiều container. Khi gọi `addToBackStack()`, toàn bộ các thao tác này sẽ được đưa vào back stack và có thể được đảo ngược cùng lúc khi back stack bị pop. Tuy nhiên, nếu bạn thực hiện thêm một transaction khác trước khi gọi `popBackStack()` và transaction đó **không** được đưa vào back stack, các thay đổi trong transaction đó sẽ không bị đảo ngược. Vì vậy, nên tránh trộn lẫn các thao tác có ảnh hưởng đến back stack với các thao tác không ảnh hưởng trong cùng một `FragmentTransaction`.


### Ví dụ mối quan hệ

* **Hình 1**: Mỗi Activity host hiển thị **BottomNavigationView** để chuyển đổi giữa các màn hình (mỗi màn hình là một fragment riêng).
![a](https://developer.android.com/static/images/guide/fragments/fragment-host.png)
    * **Ví dụ 1**: Host fragment chứa **2 child fragment** → giao diện dạng **split-view**.
    * **Ví dụ 2**: Host fragment chứa **1 child fragment** → giao diện dạng **swipe view**.

* **Hình 2**: Mỗi host có một **FragmentManager riêng** để quản lý các child fragment của nó.
![a](https://developer.android.com/static/images/guide/fragments/manager-mappings.png)
    * `supportFragmentManager`: Dùng trong Activity.
    * `parentFragmentManager`: Dùng để truy cập FragmentManager của fragment cha.
    * `childFragmentManager`: Dùng để quản lý các fragment con.

## **FragmentTransaction **

**Khái niệm**:

* Là “gói” các thao tác thay đổi Fragment (thêm, thay thế, xóa) trong Activity/Fragment.
* Được quản lý bởi **FragmentManager** và chỉ thực thi khi **commit()**.
* Có thể lưu vào **Back Stack** để bấm nút Back quay lại UI trước đó.

---

## **Các phương thức hay dùng**

| Phương thức                    | Dễ hiểu                                                            | Khi nào dùng                                           |
| ------------------------------ | ------------------------------------------------------------------ | ------------------------------------------------------ |
| **add()**                      | Thêm fragment mới vào container.                                   | Khi muốn hiển thị fragment mới bên cạnh fragment cũ.   |
| **replace()**                  | Xóa fragment cũ trong container và thêm fragment mới vào thay thế. | Khi muốn thay màn hình hiện tại bằng fragment khác.    |
| **remove()**                   | Xóa fragment khỏi UI và khỏi FragmentManager.                      | Khi không cần fragment đó nữa.                         |
| **show()** / **hide()**        | Hiện hoặc ẩn fragment mà không phá view.                           | Khi muốn tạm ẩn/hiện nội dung.                         |
| **addToBackStack()**           | Lưu thao tác vào back stack để có thể quay lại.                    | Khi muốn người dùng bấm Back để trở về fragment trước. |
| **setReorderingAllowed(true)** | Cho phép sắp xếp tối ưu thao tác để tránh lifecycle thừa.          | Hầu như lúc nào cũng nên bật.                          |
| **commit()**                   | Thực thi giao dịch bất đồng bộ.                                    | Dùng mặc định.                                         |

---

## **Ví dụ **


```kotlin
fun navigateToSignUp() {
    supportFragmentManager.beginTransaction()
        .replace(R.id.fragment_container, SignUpFragment())
        .addToBackStack(null)
        .commit()
}
```

→ `replace()` thay fragment, `addToBackStack()` lưu để quay lại, `commit()` thực thi.

**Dùng KTX (ngắn gọn):**

```kotlin
fun navigateToSignUp() {
    supportFragmentManager.commit {
        replace<SignUpFragment>(R.id.fragment_container)
        setReorderingAllowed(true)
        addToBackStack(null)
    }
}
```

→ Gọn hơn, dùng generic `<SignUpFragment>` và `setReorderingAllowed(true)` để tối ưu.



1. **onCreate()**: Lần mở đầu → `add` `ExampleFragment` vào container, lưu vào back stack.
2. **showAnotherFragment()**: `replace` `ExampleFragment` bằng `AnotherFragment`, cũng lưu vào back stack.

Cả hai dùng **Fragment Transaction** với `setReorderingAllowed(true)` để tối ưu.

## Vòng đời fragment
![a](https://media.geeksforgeeks.org/wp-content/uploads/20201112160326/FragmentInteractionwithActivity.png)
Mỗi phiên bản **Fragment** có vòng đời (lifecycle) riêng của nó. Khi người dùng điều hướng và tương tác với ứng dụng, các fragment của bạn sẽ chuyển qua nhiều trạng thái khác nhau trong vòng đời khi chúng được thêm vào, gỡ bỏ, hoặc khi xuất hiện và biến mất khỏi màn hình.

Để quản lý vòng đời, **Fragment** triển khai **LifecycleOwner**, cung cấp một đối tượng **Lifecycle** mà bạn có thể truy cập thông qua phương thức `getLifecycle()`.

Mỗi trạng thái có thể có của vòng đời được biểu diễn trong enum **Lifecycle.State**:

* **INITIALIZED**: Fragment đã được tạo instance (object được khởi tạo bằng constructor) nhưng chưa được gắn (attached) vào LifecycleOwner và chưa nhận callback onCreate().
* **CREATED**: Fragment đã được attach vào LifecycleOwner (thường là Activity), onCreate() đã chạy nhưng View có thể chưa được tạo.
* **STARTED**: Fragment đang hiển thị trên màn hình hoặc ít nhất là có thể nhìn thấy một phần, nhưng chưa tương tác trực tiếp (chưa ở trạng thái foreground hoàn toàn).
* **RESUMED**: Fragment đang hiển thị và hoạt động ở foreground, sẵn sàng nhận input người dùng.
* **DESTROYED**: Fragment đã bị gỡ khỏi Activity hoặc bị hủy hoàn toàn.

**LifecycleOwner** là 1 interface dùng để đại diện cho 1 đối tượng có vòng đời (lifecycle) và cho phép các thành phần khác quan sát (observe) sự thay đổi của vòng đời đó.

![a](https://developer.android.com/static/images/guide/fragments/fragment-view-lifecycle.png)
Bằng cách xây dựng Fragment dựa trên **Lifecycle**, bạn có thể sử dụng các kỹ thuật và lớp có sẵn để xử lý vòng đời với **Lifecycle-Aware Components**.

Ngoài việc sử dụng **LifecycleObserver**, lớp **Fragment** còn cung cấp các phương thức callback tương ứng với từng thay đổi trong vòng đời của fragment, bao gồm:
`onCreate()`, `onStart()`, `onResume()`, `onPause()`, `onStop()`, và `onDestroy()`.

Vòng đời của **view** trong fragment được quản lý riêng, độc lập với vòng đời của chính fragment. Các fragment duy trì một **LifecycleOwner** cho view của chúng, có thể truy cập thông qua `getViewLifecycleOwner()` hoặc `getViewLifecycleOwnerLiveData()`. Việc truy cập được vòng đời của view rất hữu ích trong các trường hợp mà một thành phần nhận biết vòng đời chỉ nên hoạt động khi view của fragment tồn tại, chẳng hạn như quan sát **LiveData** chỉ dùng để hiển thị trên màn hình.

**Vòng đời Fragment và ý nghĩa các hàm callback**

Trong vòng đời của một Fragment, Android sẽ gọi các hàm callback theo thứ tự nhất định từ khi fragment được tạo ra cho tới khi bị hủy. Dưới đây là giải thích chi tiết:

1. **`onAttach()`**

    * Fragment được gắn (attach) vào `Activity` chứa nó.
    * Đây là lúc Fragment có thể truy cập đến `Context` hoặc tham chiếu tới Activity cha.
    * Mỗi Fragment phải thuộc về một Activity thì mới chạy được.

2. **`onCreate()`**

    * Được gọi sau `onAttach()`.
    * Thích hợp để khởi tạo các biến, dữ liệu, hoặc các thành phần **không liên quan đến UI**.
    * Chưa nên thao tác trực tiếp với giao diện tại đây.

3. **`onCreateView()`**

    * Được gọi để **tạo giao diện (View)** của Fragment.
    * Trả về một `View` được inflate từ file XML layout.
    * **Lưu ý**: Activity chứa Fragment có thể chưa khởi tạo xong, vì vậy không nên gọi các hàm của Activity ở đây.

4. **`onActivityCreated()`**

    * Được gọi sau khi Activity chứa Fragment đã hoàn tất `onCreate()`.
    * Lúc này có thể an toàn truy cập và tương tác với Activity hoặc dữ liệu được truyền vào.

5. **`onStart()`**

    * Fragment bắt đầu hiển thị trên màn hình (nhưng chưa thể tương tác).
    * Chỉ xảy ra sau khi Activity start.

6. **`onResume()`**

    * Fragment đã hiển thị đầy đủ và sẵn sàng tương tác với người dùng.

7. **`onPause()`**

    * Fragment không còn nhận tương tác từ người dùng, nhưng **có thể vẫn hiển thị**.
    * Xảy ra khi một Fragment khác che lên hoặc khi Activity tạm dừng.

8. **`onStop()`**

    * Fragment không còn hiển thị nữa.
    * Xảy ra khi Activity chứa nó bị dừng hoặc khi Fragment bị ẩn/loại bỏ khỏi màn hình.

9. **`onDestroyView()`**

    * View và các tài nguyên UI được tạo trong `onCreateView()` sẽ bị hủy.
    * Thích hợp để giải phóng tài nguyên liên quan đến UI.

10. **`onDestroy()`**

    * Fragment thực hiện công việc dọn dẹp cuối cùng trước khi bị hủy hoàn toàn.

11. **`onDetach()`**

    * Fragment bị tách ra khỏi Activity chứa nó.
    * Sau bước này, Fragment không còn tham chiếu tới Activity.



## **Giao tiếp giữa Fragment – Fragment và Activity – Fragment**

### **a. Truyền dữ liệu vào Fragment**

**1. Dùng `newInstance()` + `arguments` (Bundle)**

* Tạo phương thức tĩnh `newInstance()` để khởi tạo fragment và truyền dữ liệu.

```kotlin
class MyFragment : Fragment() {
    companion object {
        private const val ARG_TITLE = "arg_title"
        fun newInstance(title: String) = MyFragment().apply {
            arguments = Bundle().apply { putString(ARG_TITLE, title) }
        }
    }
    private var title: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = arguments?.getString(ARG_TITLE)
    }
}
```

**Gọi:**

```kotlin
parentFragmentManager.beginTransaction()
    .replace(R.id.container, MyFragment.newInstance("Hello"))
    .commit()
```

* **Ưu điểm:** Dữ liệu giữ nguyên khi xoay màn hình.
* **Nhược điểm:** Chỉ truyền lúc tạo, muốn thay đổi phải tạo mới fragment.

**2. Tạo Bundle trực tiếp (`apply` / `bundleOf`)**

```kotlin
val bundle = bundleOf("score" to 100, "name" to "Hai")
parentFragmentManager.commit {
    setReorderingAllowed(true)
    add<MyFragment>(R.id.fragment_container_view, args = bundle)
}
```

Hoặc:

```kotlin
val fragment = MyFragment().apply {
    arguments = Bundle().apply {
        putInt("score", 100)
        putString("name", "Hai")
    }
}
parentFragmentManager.beginTransaction()
    .setReorderingAllowed(true)
    .add(R.id.fragment_container_view, fragment)
    .commit()
```

**Lấy dữ liệu trong Fragment:**

```kotlin
val score = requireArguments().getInt("score", 0)
val name = requireArguments().getString("name", "")
```

---

### **b. Fragment ↔ Fragment**

Dùng `setFragmentResult()` và `setFragmentResultListener()`.

**Fragment A lắng nghe dữ liệu từ B:**

```kotlin
parentFragmentManager.setFragmentResultListener("requestKey", viewLifecycleOwner) { _, bundle ->
    val score = bundle.getInt("score")
    val name = bundle.getString("name")
    binding.textView.text = "Name: $name - Score: $score"
}
```

**Fragment B gửi dữ liệu về A:**

```kotlin
binding.buttonSend.setOnClickListener {
    parentFragmentManager.setFragmentResult("requestKey", Bundle().apply {
        putInt("score", 100)
        putString("name", "Hai")
    })
}
```

**Nguyên lý:**

1. A đăng ký lắng nghe với key `"requestKey"`.
2. B gửi dữ liệu với cùng key.
3. A nhận và xử lý dữ liệu khi quay lại.

---

### **c. Activity ↔ Fragment**

**1. Fragment → Activity**

```kotlin
// MainActivity.kt
supportFragmentManager.setFragmentResultListener("requestKey", this) { _, bundle ->
    val result = bundle.getString("bundleKey")
    // Xử lý dữ liệu
}
```

**Fragment gửi:**

```kotlin
supportFragmentManager.setFragmentResult("requestKey", Bundle().apply {
    putString("bundleKey", "Hello from Fragment")
})
```

**2. Activity → Fragment**

```kotlin
// MainActivity.kt
supportFragmentManager.setFragmentResult("request", Bundle().apply {
    putInt("key", 9)
})

// Fragment nhận:
parentFragmentManager.setFragmentResultListener("request", this) { _, bundle ->
    val value = bundle.getInt("key")
}
```

