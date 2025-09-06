# Navigation Component

## Lý do có Navigation Component

Trước đây việc quản lý điều hướng giữa các màn hình trong Android (Activity và Fragment) khá phức tạp, lập trình viên phải tự dùng `Intent` hoặc `FragmentTransaction`, tự xử lý back stack của `FragmentManager`, xoay màn hình, đồng bộ Toolbar, nút *Back/Up* và truyền dữ liệu thủ công qua Bundle dễ gây lỗi. Khi ứng dụng nhiều màn hình, luồng điều hướng trở nên rối rắm, khó bảo trì, dễ phát sinh bug và gần như không hỗ trợ deep link. Chính vì thế, từ năm 2018 Google giới thiệu **Navigation Component** trong Android Jetpack để chuẩn hóa điều hướng: cung cấp **Navigation Graph** trực quan quản lý toàn bộ flow, back stack được xử lý tự động, hỗ trợ truyền dữ liệu an toàn với **Safe Args**, dễ dàng tích hợp với Toolbar, Drawer, Bottom Navigation, đồng thời hỗ trợ deep link và cả Compose. Nhờ đó, mã nguồn gọn gàng, dễ bảo trì, luồng điều hướng rõ ràng và trải nghiệm người dùng luôn tuân thủ chuẩn Android.

---

## 1. Navigation Component là gì?

Navigation Component (thuộc Jetpack) là bộ thư viện hỗ trợ quản lý điều hướng trong ứng dụng Android. Nó giúp thay thế cách viết thủ công `FragmentTransaction` vốn dễ phức tạp và khó bảo trì.

* Dựa trên mô hình **Single-Activity**: một Activity chính chứa `NavHostFragment`, toàn bộ các màn hình còn lại là Fragment.
* Giúp quản lý điều hướng thống nhất, an toàn, dễ tích hợp với ViewModel, Deep Link và Back stack.

---

### 2. Thành phần trong kiến trúc Navigation

### NavHost SSSSSS

* Là vùng giao diện dùng để hiển thị điểm đến hiện tại.
* Khi người dùng chuyển sang một destination khác, NavHost sẽ thay fragment hiển thị bên trong nó.
* Thường dùng `NavHostFragment`.

### NavController

* Là bộ điều khiển trung tâm quản lý điều hướng.
* Nắm quyền điều khiển NavHost và quản lý Back stack.
* Cung cấp API để điều hướng, xử lý nút Back/Up, deep link.

### NavGraph

* Là sơ đồ định nghĩa tất cả các điểm đến (destinations) và mối liên hệ giữa chúng.
* Được khai báo bằng file XML (ví dụ: `nav_graph.xml`).
* Bao gồm các thành phần:

    * **NavDestination**: mỗi màn hình (Fragment/Activity).
    * **Action**: hành động chuyển từ một destination sang destination khác.
    * Có thể kèm tham số, tùy chọn `popUpTo` để điều chỉnh back stack.

### NavDestination

* Mỗi điểm đến trong NavGraph (ví dụ: `HomeFragment`, `DetailFragment`).
* Khi NavController điều hướng, NavHost sẽ hiển thị nội dung của destination này.

### Action

* Mối liên kết điều hướng giữa các điểm đến.
* Có thể chỉ định hiệu ứng chuyển cảnh, hoặc quy tắc back stack.

### Route

* Là một định danh duy nhất cho một destination, có thể đi kèm dữ liệu (tham số).
* Thường thấy rõ trong Navigation Compose hoặc dùng route string (vd: `"profile/{userId}"`).

---

### 3. Điểm nổi bật của Navigation Component

* **Quản lý tự động FragmentTransaction**: không cần thao tác replace/add thủ công.
* **Điều khiển nút Back/Up chuẩn Android**: hệ thống sẽ tự xử lý đúng theo quy tắc.
* **Truyền dữ liệu an toàn**: nhờ plugin Safe Args, tham số truyền được kiểm tra kiểu dữ liệu tại compile-time.
* **Hỗ trợ Deep Link**: mở ứng dụng đến một điểm đến cụ thể từ bên ngoài.
* **Tích hợp ViewModel**: có thể scope ViewModel theo NavGraph để chia sẻ dữ liệu giữa các màn hình trong cùng luồng.
* **Hỗ trợ sẵn nhiều mẫu UI điều hướng**: như Bottom Navigation, Navigation Drawer.

---

### 4. Phân biệt NavGraph và Back Stack

* **NavGraph**: sơ đồ tĩnh định nghĩa các điểm đến và đường đi giữa chúng (không thay đổi khi app chạy).
* **Back Stack**: ngăn xếp động lưu lại lịch sử điều hướng thực tế khi người dùng thao tác.

Ví dụ:

* Trong NavGraph có `HomeFragment` → `DetailFragment`.
* Khi người dùng bấm vào món ăn để sang chi tiết, `DetailFragment` được đẩy vào Back Stack.
* Khi nhấn Back, NavController lấy từ Back Stack ra để hiển thị lại `HomeFragment`.


---

## 2. Cấu hình NavGraph & Navigation (Kotlin)

### 1) Thiết lập môi trường và Dependencies

**Cách làm**

* Trong **build.gradle (project)** thêm plugin Safe Args:

```gradle
plugins {
    // ... các plugin khác
    id("androidx.navigation.safeargs.kotlin") version "2.7.7" apply false
}
```

* Trong **build.gradle (app)** bật plugin và thêm thư viện Navigation (nếu muốn dùng ViewBinding cho ví dụ, bật luôn):

```gradle
plugins {
    // ... các plugin khác
    id("androidx.navigation.safeargs.kotlin")
}

android {
    // ...
    buildFeatures {
        viewBinding = true   // để dùng binding trong Fragment/Activity
    }
}

dependencies {
    // ... các dependencies khác
    val nav_version = "2.7.7" // dùng bản ổn định mới nhất của bạn

    // Kotlin
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")

    // Nếu cần:
    // implementation("androidx.navigation:navigation-dynamic-features-fragment:$nav_version")
    // androidTestImplementation("androidx.navigation:navigation-testing:$nav_version")
    // implementation("androidx.navigation:navigation-compose:$nav_version")
}
```

**Cách hoạt động**

* `navigation-fragment-ktx` cung cấp `NavHostFragment`, `NavController` và các hàm mở rộng (`findNavController()`…).
* `navigation-ui-ktx` cung cấp tiện ích nối `NavController` với Toolbar/BottomNavigation/NavigationView.
* Plugin **Safe Args** sinh code `XxxDirections` (điều hướng + tham số) và `XxxArgs` (nhận tham số) → truyền dữ liệu **an toàn kiểu**.
* `viewBinding = true` sinh lớp binding cho mỗi layout (ví dụ: `ActivityMainBinding`, `FragmentABinding`…).

---

### 2) Tạo Navigation Graph

**Cách làm**

* Trong `res/` tạo thư mục **navigation** → tạo file `nav_graph.xml` (Resource type: *Navigation*).

**Cách hoạt động**

* `nav_graph.xml` là **sơ đồ điều hướng**: khai báo tất cả **destinations** (thường là `Fragment`) và **actions** (đường đi giữa các màn hình).
* Android Studio có **Design Editor** để kéo-thả trực quan, nhưng bạn cũng có thể chỉnh XML thủ công.

---

### 3) Thêm Destinations (Fragments)

**Cách làm**

* Mở `nav_graph.xml`, thêm các `Fragment` đang có (hoặc tạo mới ngay trong editor).

Ví dụ tối giản:

```xml
<navigation xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    app:startDestination="@id/fragmentA">

    <fragment
        android:id="@+id/fragmentA"
        android:name="com.example.app.ui.FragmentA"
        android:label="Màn A" />

    <fragment
        android:id="@+id/fragmentB"
        android:name="com.example.app.ui.FragmentB"
        android:label="Màn B" />
</navigation>
```

**Cách hoạt động**

* `app:startDestination` xác định màn hình khởi đầu khi `NavHost` được nạp.
* Mỗi `<fragment>` chỉ là **khai báo siêu dữ liệu** (id, class, label…). Khi điều hướng, `NavController` sẽ khởi tạo/hiển thị fragment tương ứng.

---

### 4) Thêm Actions để nối các Destinations

**Cách làm**

* Trong *Design Editor*: rê chuột từ **cạnh phải** của `Fragment A` → kéo sang `Fragment B` để tạo **action**.
* Chọn mũi tên (action) vừa tạo → có thể cấu hình:

  * **ID** (tự sinh, có thể đổi tên).
  * **Arguments** (tham số gửi kèm).
  * **Options** (anim chuyển cảnh, `popUpTo`, `inclusive`…).

Ví dụ XML:

```xml
<fragment
    android:id="@+id/fragmentA"
    android:name="com.example.app.ui.FragmentA"
    android:label="Màn A">
    <action
        android:id="@+id/action_A_to_B"
        app:destination="@id/fragmentB" />
</fragment>
```

**Cách hoạt động**

* **Action** là “đường đi” định danh giữa 2 màn hình.
* Khi gọi `navigate(R.id.action_A_to_B)`, `NavController` dùng graph để:

  * Xác định đích đến (`fragmentB`),
  * Quản lý **back stack** (push A, hiển thị B),
  * Áp dụng **options** (anim, popUpTo… nếu có).

---

### 5) Thêm NavHostFragment vào Layout của Activity

**Cách làm** (ví dụ trong `activity_main.xml`):

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <!-- NavHostFragment là khung chứa các Fragment trong nav_graph -->
    <androidx.fragment.app.FragmentContainerView
        android:id="@+id/nav_host_fragment"
        android:name="androidx.navigation.fragment.NavHostFragment"
        android:layout_width="0dp"
        android:layout_height="0dp"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:defaultNavHost="true"          <!-- bắt nút Back hệ thống -->
        app:navGraph="@navigation/nav_graph"/> <!-- gắn với graph -->
</androidx.constraintlayout.widget.ConstraintLayout>
```

**Cách hoạt động**

* `NavHostFragment` thay thế nội dung bên trong bằng **destination hiện tại** theo điều khiển của `NavController`.
* `app:defaultNavHost="true"` giúp `NavHostFragment` nhận sự kiện **Back** và chuyển cho `NavController` xử lý pop stack.

---

### 6) Điều hướng giữa các Destinations với NavController

**Cách làm**

* Trong **Fragment nguồn**, lấy `NavController` và gọi `navigate()` theo **ID action** đã tạo ở Bước 4.
* Dưới đây là **2 phiên bản**: dùng `findViewById` như bạn đưa và **dùng View Binding** (khuyến nghị).

**(a) Theo mẫu của bạn – dùng `findViewById`)**

```kotlin
class FragmentA : Fragment(R.layout.fragment_a) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_go_to_b).setOnClickListener {
            val navController = findNavController()
            navController.navigate(R.id.action_fragmentA_to_fragmentB)
        }
    }
}
```

**(b) Dùng View Binding (gọn & an toàn)**

```kotlin
class FragmentA : Fragment(R.layout.fragment_a) {

    private var _b: FragmentABinding? = null
    private val b get() = _b!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _b = FragmentABinding.bind(view)

        b.buttonGoToB.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentA_to_fragmentB)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _b = null
    }
}
```

**Cách hoạt động**

* `findNavController()` tìm `NavController` gắn với `NavHostFragment` bao quanh Fragment hiện tại.
* `navigate(idAction)` tra graph → đẩy destination mới lên **back stack** và hiển thị nó.

---

### 7) Truyền dữ liệu giữa các Destinations bằng Safe Args

**Cách làm**

1. Khai báo **Argument** trong `nav_graph.xml` (ở **destination đích** hoặc ngay **action**):

```xml
<fragment
    android:id="@+id/fragmentB"
    android:name="com.example.app.ui.FragmentB"
    android:label="Màn B">
    <argument
        android:name="userId"
        app:argType="integer" />
    <argument
        android:name="userName"
        app:argType="string" />
</fragment>
```

2. **Sync Gradle** → sinh lớp `FragmentADirections` và `FragmentBArgs`.

3. **Gửi dữ liệu** từ A → B (theo mẫu bạn đưa, có binding):

```kotlin
class FragmentA : Fragment(R.layout.fragment_a) {

    private var _b: FragmentABinding? = null
    private val b get() = _b!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _b = FragmentABinding.bind(view)

        b.btnGoToB.setOnClickListener {
            val userId = 123
            val userName = "Alice"

            val action = FragmentADirections.actionFragmentAToFragmentB(userId, userName)
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() { super.onDestroyView(); _b = null }
}
```

4. **Nhận dữ liệu** ở B (theo mẫu bạn đưa, có binding):

```kotlin
class FragmentB : Fragment(R.layout.fragment_b) {

    private var _b: FragmentBBinding? = null
    private val b get() = _b!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _b = FragmentBBinding.bind(view)

        val args: FragmentBArgs = FragmentBArgs.fromBundle(requireArguments())
        val userId = args.userId
        val userName = args.userName

        b.textUserInfo.text = "User ID: $userId, Name: $userName"
    }

    override fun onDestroyView() { super.onDestroyView(); _b = null }
}
```

**Cách hoạt động**

* Safe Args sinh code **type-safe**:

  * Ở A có `FragmentADirections.actionFragmentAToFragmentB(userId, userName)` → đóng gói Bundle **đúng key/kiểu**.
  * Ở B có `FragmentBArgs.fromBundle(...)` hoặc `by navArgs()` để giải nạp an toàn.
* Sai kiểu/sai tham số sẽ **bắt ngay khi biên dịch**, tránh crash lúc chạy.

---

### 8) Xử lý **Đi lên (Up)** và **Quay lại (Back)**

**Cách làm**

* Trong `MainActivity`, nối `ActionBar` với `NavController`:

```kotlin
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val host = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = host.navController

        setupActionBarWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
```

**Cách hoạt động**

* **Back**: `NavController` tự pop **back stack** dựa trên graph (không cần bạn can thiệp).
* **Up**: `setupActionBarWithNavController` tự hiển thị mũi tên Up khi **không** ở `startDestination`; bấm Up → `navigateUp()` (tôn trọng Drawer nếu có, hoặc pop stack).

---

### 9) Tích hợp với UI khác (BottomNavigationView, NavigationView)

**Cách làm**

* **BottomNavigationView** (ví dụ id `bottom_nav_view`):

```kotlin
val navHost = supportFragmentManager
    .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
val navController = navHost.navController

val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
bottomNav.setupWithNavController(navController)
bottomNav.setOnItemReselectedListener { /* no-op để giữ trạng thái mỗi tab */ }
```

* **NavigationView** trong **DrawerLayout** (ví dụ id `nav_view`):

```kotlin
val navView = findViewById<NavigationView>(R.id.nav_view)
navView.setupWithNavController(navController)
```

**Cách hoạt động**

* `setupWithNavController(...)` ánh xạ **id menu item** ↔ **id destination** trong `nav_graph.xml`.
* Người dùng chạm item → `NavigationUI` gọi `navigate()` tới destination tương ứng, tự đồng bộ **chọn/hilight** mục menu, **tiêu đề** ActionBar và **Back/Up**.

---

## tóm
* **NavGraph**: nơi định nghĩa màn hình & đường đi.
* **NavHostFragment**: khung hiển thị màn hình hiện tại.
* **NavController**: ra lệnh điều hướng (`navigate`, `navigateUp`, `popBackStack`).
* **Safe Args**: truyền dữ liệu **type-safe** giữa các màn hình.
* **NavigationUI**: gắn `NavController` với BottomNav/Drawer/Toolbar trong **1–2 dòng code**.
* **View Binding**: truy cập view an toàn, gọn hơn `findViewById`.
###  Các navigation component: **Bottom Navigation**, **TabLayout + ViewPager2**
---

### a) Bottom Navigation

#### 1) Khi nào dùng

* Phù hợp khi có **3 → 5** khu vực chính (Home, Favorite…).
* Giữ mỗi khu vực là **top-level destination** (không hiện nút Up), chuyển nhanh giữa các màn hình độc lập.

---

#### 2) Tạo menu `res/menu/bottom_nav_menu.xml`

**Cách làm**

```xml
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android">
    <item
        android:id="@+id/homeFragment"
        android:title="Home"
        android:icon="@drawable/it_home"/>

    <item
        android:id="@+id/favoriteFragment"
        android:title="Favorite"
        android:icon="@drawable/it_farvotie"/>
</menu>
```

**Cách hoạt động**

* `android:id` của mỗi item **phải trùng** `id` destination trong `navigation/nav_graph.xml` để có thể **liên kết tự động** bằng `setupWithNavController()`.

---

#### 3) Khai báo NavHost + BottomNavigation trong `res/layout/activity_main.xml`

**Cách làm**

```xml
<androidx.coordinatorlayout.widget.CoordinatorLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <!-- Khung chứa Fragment theo nav_graph -->
    <androidx.fragment.app.FragmentContainerView
        android:id="@+id/nav_host"
        android:name="androidx.navigation.fragment.NavHostFragment"
        app:navGraph="@navigation/nav_graph"
        app:defaultNavHost="true"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>

    <!-- Bottom Navigation -->
    <com.google.android.material.bottomnavigation.BottomNavigationView
        android:id="@+id/bottomNav"
        app:menu="@menu/bottom_nav_menu"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_gravity="bottom"/>
</androidx.coordinatorlayout.widget.CoordinatorLayout>
```

**Cách hoạt động**

* `NavHostFragment` hiển thị **destination hiện tại**.
* `defaultNavHost="true"` giúp host **bắt phím Back** hệ thống và chuyển cho `NavController`.

---

#### 4) Liên kết trong `MainActivity.kt` (dùng **ActivityMainBinding**)

**Cách làm**

```kotlin
class MainActivity : AppCompatActivity() {

    private lateinit var b: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)

        val nav = findNavController(R.id.nav_host)

        // 4.1 Liên kết BottomNav ↔ NavController (khuyên dùng)
        b.bottomNav.setupWithNavController(nav)

        // 4.2 Cấu hình AppBar (Home, Favorite là top-level: không hiện Up)
        val top = AppBarConfiguration(setOf(R.id.homeFragment, R.id.favoriteFragment))
        setupActionBarWithNavController(nav, top)

        // 4.3 (Tuỳ chọn) Nếu muốn tự xử lý onItemSelected thay vì setupWithNavController
        // b.bottomNav.setOnItemSelectedListener { item ->
        //     when (item.itemId) {
        //         R.id.homeFragment      -> { nav.navigate(R.id.homeFragment); true }
        //         R.id.favoriteFragment  -> { nav.navigate(R.id.favoriteFragment); true }
        //         else -> false
        //     }
        // }
        // b.bottomNav.setOnItemReselectedListener { /* no-op: giữ trạng thái tab */ }
    }

    override fun onSupportNavigateUp(): Boolean {
        val nav = findNavController(R.id.nav_host)
        return nav.navigateUp() || super.onSupportNavigateUp()
    }
}
```

**Cách hoạt động**

* `setupWithNavController()` **ánh xạ** `id` menu ↔ `id` destination → chọn tab = `navigate()` tới screen tương ứng.
* `AppBarConfiguration` cho biết đâu là **top-level** để ActionBar **ẩn/hiện Up** đúng chuẩn.
* Nếu tự dùng `setOnItemSelectedListener`: bạn **chịu trách nhiệm** điều hướng và chống điều hướng lặp.

---

### b) TabLayout + ViewPager2

> Mẫu này phù hợp khi **nhiều nhóm nội dung** trong **cùng 1 màn hình** (ví dụ: các **Category** của Home).
> Thực thi dưới dạng **Fragment chứa TabLayout + ViewPager2** (ví dụ `HomeFragment`).

---

#### 1) Thuộc tính TabLayout thường dùng

* `app:tabMode="fixed|scrollable"`: cố định hoặc cuộn khi nhiều tab.
* `app:tabGravity="fill|center"`: cách dàn tab.
* `app:tabTextColor`, `app:tabSelectedTextColor`: màu chữ.
* `app:tabIndicatorColor`: màu gạch chỉ báo tab đang chọn.

---

#### 2) Layout màn chứa Tab (ví dụ `res/layout/fragment_home.xml`)

**Cách làm**

```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <com.google.android.material.tabs.TabLayout
        android:id="@+id/tabLayout"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:tabMode="scrollable"
        app:tabIndicatorColor="@color/purple_500"/>

    <androidx.viewpager2.widget.ViewPager2
        android:id="@+id/viewPager"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1"/>
</LinearLayout>
```

**Cách hoạt động**

* `ViewPager2` là một **RecyclerView** đặc biệt → hỗ trợ cập nhật động, `DiffUtil`, **transformer**, vuốt ngang/dọc.
* `TabLayoutMediator` (ở bước sau) sẽ **đồng bộ** tab ↔ trang.

---

#### 3) Enum Category & dữ liệu mẫu

**Cách làm** (ví dụ nhanh)

```kotlin
enum class Category { ALL, VIET, TRUNG, HAN }

object DemoData {
    fun foodsBy(cat: Category): List<Food> = when (cat) {
        Category.ALL  -> listOf(/* tổng hợp */)
        Category.VIET -> listOf(/* đồ Việt */)
        Category.TRUNG-> listOf(/* đồ Trung */)
        Category.HAN  -> listOf(/* đồ Hàn */)
    }
}
```

---

#### 4) Adapter cho ViewPager2 – `CategoryPagerAdapter.kt`

**Cách làm**

```kotlin
class CategoryPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = 4
    override fun createFragment(position: Int): Fragment = when (position) {
        1 -> CategoryFragment.newInstance(Category.VIET)
        2 -> CategoryFragment.newInstance(Category.TRUNG)
        3 -> CategoryFragment.newInstance(Category.HAN)
        else -> CategoryFragment.newInstance(Category.ALL)
    }
}
```

**Cách hoạt động**

* `FragmentStateAdapter` **tạo/huỷ** fragment theo nhu cầu, **giải phóng** tài nguyên khi trang không dùng, tự quản vòng đời theo `Fragment` cha (ở đây là `HomeFragment`).
* Mỗi trang là **một Fragment riêng** (`CategoryFragment`) → tách logic rõ ràng.

---

#### 5) Fragment từng trang – `CategoryFragment.kt` (dùng **FragmentCategoryBinding**)

**Cách làm**

```kotlin
class CategoryFragment : Fragment() {

    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var category: Category
    private val adapter = FoodAdapter() // adapter RecyclerView item món ăn

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        category = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getSerializable(ARG_CAT, Category::class.java) ?: Category.ALL
        } else {
            arguments?.getSerializable(ARG_CAT) as Category? ?: Category.ALL
        }
    }

    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?): View {
        _binding = FragmentCategoryBinding.inflate(i, c, false)
        return binding.root
    }

    override fun onViewCreated(v: View, s: Bundle?) {
        super.onViewCreated(v, s)
        setupRecyclerView()
        loadCategoryData()
    }

    private fun setupRecyclerView() {
        binding.rvFoods.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvFoods.adapter = adapter
    }

    private fun loadCategoryData() {
        adapter.submitList(DemoData.foodsBy(category))
    }

    override fun onDestroyView() {
        super.onDestroyView(); _binding = null
    }

    companion object {
        private const val ARG_CAT = "arg_category"
        fun newInstance(cat: Category) = CategoryFragment().apply {
            arguments = Bundle().apply { putSerializable(ARG_CAT, cat) }
        }
    }
}
```

**Cách hoạt động**

* Fragment nhận `Category` qua `arguments` (ở ví dụ cơ bản dùng `Serializable`; dự án thực tế có thể chuyển sang `Safe Args` để **type-safe**).
* `RecyclerView` hiển thị danh sách món theo `category`.
* Giải phóng binding trong `onDestroyView()` để tránh memory leak.

---

#### 6) Gắn TabLayout ↔ ViewPager2 trong `HomeFragment.kt` (dùng **FragmentHomeBinding**)

**Cách làm**

```kotlin
class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _b: FragmentHomeBinding? = null
    private val b get() = _b!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _b = FragmentHomeBinding.bind(view)

        // 6.1 Cấp adapter cho ViewPager2
        b.viewPager.adapter = CategoryPagerAdapter(this)

        // 6.2 Liên kết TabLayout với ViewPager2
        TabLayoutMediator(b.tabLayout, b.viewPager) { tab, position ->
            tab.text = when (position) {
                1 -> "Việt"
                2 -> "Trung"
                3 -> "Hàn"
                else -> "Tất cả"
            }
        }.attach()

        // (tuỳ chọn) Vuốt dọc
        // b.viewPager.orientation = ViewPager2.ORIENTATION_VERTICAL
    }

    override fun onDestroyView() {
        super.onDestroyView(); _b = null
    }
}
```

**Cách hoạt động**

* `TabLayoutMediator` lắng nghe thay đổi trang của `ViewPager2` và **đồng bộ** tab: đổi tab → đổi trang, vuốt trang → tab tương ứng được chọn.
* `FragmentStateAdapter(this)` gắn vòng đời của các trang với **HomeFragment** (fragment cha), tránh rò rỉ.

---

#### 7) Gợi ý tích hợp cùng Navigation

* `HomeFragment` vẫn là **destination** trong `nav_graph.xml`.
* Bên trong `HomeFragment`, `TabLayout + ViewPager2` chỉ điều hướng **nội bộ** giữa các danh mục, **không ảnh hưởng** đến back stack của Navigation Graph.
* Khi người dùng Back từ các danh mục, họ vẫn **ở HomeFragment** (đúng kỳ vọng).

---

#### 8) Lỗi hay gặp & mẹo

* **ID menu không trùng ID destination** → `setupWithNavController()` không điều hướng được.
* **Nhấn lại tab đang ở → điều hướng chồng**: thêm `setOnItemReselectedListener { /* no-op */ }` để giữ trạng thái.
* **Fragment + Binding leak**: luôn set `_binding = null` trong `onDestroyView()`.
* **ViewPager2 trong Nested Scroll**: cần bật `isUserInputEnabled=false` nếu dùng tab chỉ để **chạm**, không vuốt.

---

#### 9) Tóm tắt cách hoạt động (luồng)

1. `MainActivity` tạo `ActivityMainBinding`, liên kết `BottomNavigationView` ↔ `NavController`.
2. `NavHost` nạp `startDestination` (ví dụ `HomeFragment`).
3. `HomeFragment` khởi tạo `ViewPager2` + `TabLayoutMediator`.
4. Người dùng chọn tab/ vuốt trang → `ViewPager2` hiển thị `CategoryFragment` tương ứng.
5. Chuyển sang tab “Favorite” ở BottomNav → `NavController` điều hướng đến `FavoriteFragment` (top-level).
6. Back hệ thống:

  * Nếu đang ở `FavoriteFragment` → thoát app (vì top-level và là start hoặc theo cấu hình).
  * Nếu đang ở `HomeFragment` → cũng thoát hoặc theo flow của bạn.
  * Việc chuyển trang trong ViewPager2 **không** tạo back stack Navigation nên Back không quay từng danh mục (đây là mong muốn thường gặp).


Tất nhiên rồi. Dưới đây là bộ “ngắn gọn mà đủ xài” cho **Data Binding**, **Bottom Sheet**, và **Navigation Drawer** – đều theo chuẩn AndroidX/Material, có **định nghĩa**, **cách làm**, **code mẫu**, và **cách hoạt động**. Mình dùng đúng phong cách trước: View Binding/Data Binding, giữ cấu trúc file quen thuộc.

---

## 1) Data Binding

### Định nghĩa

**Data Binding** cho phép viết **biểu thức trong XML** để gắn dữ liệu ↔ UI. Hỗ trợ:

* One-way (Model → UI), Two-way `@={…}` (UI ↔ Model).
* Tự cập nhật khi LiveData đổi (khi set `lifecycleOwner`).
* Mở rộng bằng `@BindingAdapter` (format, ẩn/hiện, load ảnh…).

### Cách làm (tối thiểu)

**app/build.gradle.kts**

```kotlin
android {
    buildFeatures {
        dataBinding = true
    }
}
```

**Model + ViewModel**

```kotlin
// data/UserProfile.kt
data class UserProfile(val fullName: String, val age: Int, val avatarUrl: String?)

// ui/profile/ProfileViewModel.kt
class ProfileViewModel : ViewModel() {
    val profile = MutableLiveData(UserProfile("Nguyễn An", 24, null))
    val nickname = MutableLiveData("anan") // two-way với EditText
}
```

**BindingAdapter (tuỳ chọn)**

```kotlin
// ui/binding/BindingAdapters.kt
@BindingAdapter("imageUrl")
fun ImageView.loadImage(url: String?) {
    if (url.isNullOrBlank()) setImageResource(R.drawable.ic_user_placeholder)
    else Glide.with(this).load(url).into(this)
}
```

**XML dùng Data Binding**

```xml
<!-- res/layout/activity_profile.xml -->
<layout xmlns:android="http://schemas.android.com/apk/res/android" xmlns:app="http://schemas.android.com/apk/res-auto">
  <data>
    <variable name="vm" type="com.example.a011_navigation.ui.profile.ProfileViewModel"/>
  </data>

  <LinearLayout android:orientation="vertical" android:padding="16dp"
      android:layout_width="match_parent" android:layout_height="match_parent">

    <ImageView android:layout_width="96dp" android:layout_height="96dp"
        app:imageUrl='@{vm.profile.avatarUrl}'/>

    <TextView android:layout_width="wrap_content" android:layout_height="wrap_content"
        android:text='@{vm.profile.fullName}'/>

    <EditText android:layout_width="match_parent" android:layout_height="wrap_content"
        android:hint="Nickname" android:text='@={vm.nickname}'/>
  </LinearLayout>
</layout>
```

**Activity**

```kotlin
class ProfileActivity : AppCompatActivity() {
    private lateinit var b: ActivityProfileBinding
    private val vm: ProfileViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(b.root)
        b.lifecycleOwner = this
        b.vm = vm
    }
}
```

### Cách hoạt động

* `<layout><data>` kích hoạt Data Binding compiler sinh `ActivityProfileBinding`.
* Gán `b.vm = vm` + `b.lifecycleOwner = this` → XML tự lắng nghe LiveData, two-way với EditText.

---

## 2) Bottom Sheet (Material Components)

### Định nghĩa

**Bottom Sheet** là panel trượt từ mép dưới.

* **Persistent**: “ghim” trong layout, kéo lên/xuống (không phải dialog).
* **Modal**: dạng `BottomSheetDialogFragment`, có nền mờ, đóng/mở tạm thời.

### Cách làm (tối thiểu)

**Thêm thư viện**

```kotlin
dependencies { implementation("com.google.android.material:material:1.12.0") }
```

**Persistent Bottom Sheet**

```xml
<!-- res/layout/activity_main.xml -->
<androidx.coordinatorlayout.widget.CoordinatorLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent" android:layout_height="match_parent">

  <FrameLayout android:id="@+id/main_content"
      android:layout_width="match_parent" android:layout_height="match_parent"/>

  <LinearLayout
      android:id="@+id/bottom_sheet"
      android:layout_width="match_parent" android:layout_height="320dp"
      android:orientation="vertical" android:background="@color/black"
      app:layout_behavior="com.google.android.material.bottomsheet.BottomSheetBehavior"
      app:behavior_peekHeight="72dp" app:behavior_hideable="true">

    <TextView android:id="@+id/tvPeek" android:layout_width="match_parent"
        android:layout_height="72dp" android:gravity="center" android:text="Kéo lên"
        android:textColor="@android:color/white"/>
    <TextView android:layout_width="match_parent" android:layout_height="match_parent"
        android:gravity="center" android:text="Nội dung chi tiết" android:textColor="@android:color/white"/>
  </LinearLayout>
</androidx.coordinatorlayout.widget.CoordinatorLayout>
```

```kotlin
class MainActivity : AppCompatActivity() {
    private lateinit var b: ActivityMainBinding
    private lateinit var behavior: BottomSheetBehavior<LinearLayout>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater); setContentView(b.root)
        behavior = BottomSheetBehavior.from(b.bottomSheet)
        behavior.state = BottomSheetBehavior.STATE_COLLAPSED
        b.tvPeek.setOnClickListener { behavior.state = BottomSheetBehavior.STATE_EXPANDED }
    }
}
```

**Modal Bottom Sheet**

```xml
<!-- res/layout/bs_actions.xml -->
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:padding="16dp" android:orientation="vertical"
    android:layout_width="match_parent" android:layout_height="wrap_content">
  <Button android:id="@+id/btnA" android:layout_width="match_parent" android:layout_height="wrap_content" android:text="Action A"/>
  <Button android:id="@+id/btnB" android:layout_width="match_parent" android:layout_height="wrap_content" android:text="Action B"/>
</LinearLayout>
```

```kotlin
class ActionsBottomSheet : BottomSheetDialogFragment() {
    private var _b: BsActionsBinding? = null; private val b get() = _b!!
    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?): View {
        _b = BsActionsBinding.inflate(i, c, false); return b.root
    }
    override fun onViewCreated(v: View, s: Bundle?) {
        b.btnA.setOnClickListener { /* handle */ dismiss() }
        b.btnB.setOnClickListener { /* handle */ dismiss() }
    }
    override fun onDestroyView() { super.onDestroyView(); _b = null }
}
```

Gọi:

```kotlin
ActionsBottomSheet().show(supportFragmentManager, "Actions") // trong Activity
// hoặc
ActionsBottomSheet().show(childFragmentManager, "Actions")   // trong Fragment
```

### Cách hoạt động

* Persistent: `BottomSheetBehavior` quản state (peek/collapsed/expanded/hidden).
* Modal: `BottomSheetDialogFragment` dựng dialog theo Material, có scrim, kéo để đóng.

---

## 3) Navigation Drawer (DrawerLayout + NavigationView + Navigation Component)

### Định nghĩa

**Navigation Drawer** là panel trượt từ mép **trái/phải**, chứa danh sách điều hướng (menu). Thường dùng cho **nhiều mục** hoặc mục **ít dùng** hơn Bottom Nav. Kết hợp **Navigation Component** để điều hướng gọn.

### Cách làm (chuẩn với Navigation)

**Menu Drawer**

```xml
<!-- res/menu/menu_drawer.xml -->
<menu xmlns:android="http://schemas.android.com/apk/res/android">
  <group android:checkableBehavior="single">
    <item android:id="@+id/homeFragment" android:title="Trang chủ" android:icon="@drawable/it_home"/>
    <item android:id="@+id/favoriteFragment" android:title="Yêu thích" android:icon="@drawable/it_favorite"/>
    <item android:id="@+id/profileFragment" android:title="Hồ sơ" android:icon="@drawable/it_person"/>
  </group>
</menu>
```

**Layout chính có Drawer**

```xml
<!-- res/layout/activity_main.xml -->
<androidx.drawerlayout.widget.DrawerLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/drawerLayout"
    android:layout_width="match_parent" android:layout_height="match_parent">

  <!-- Nội dung chính + AppBar -->
  <androidx.coordinatorlayout.widget.CoordinatorLayout
      android:layout_width="match_parent" android:layout_height="match_parent">

    <com.google.android.material.appbar.MaterialToolbar
        android:id="@+id/toolbar" android:layout_width="match_parent"
        android:layout_height="?attr/actionBarSize" app:title="Demo Drawer"/>

    <androidx.fragment.app.FragmentContainerView
        android:id="@+id/nav_host"
        android:name="androidx.navigation.fragment.NavHostFragment"
        app:navGraph="@navigation/nav_graph"
        app:defaultNavHost="true"
        android:layout_width="match_parent" android:layout_height="match_parent"
        android:layout_marginTop="?attr/actionBarSize"/>
  </androidx.coordinatorlayout.widget.CoordinatorLayout>

  <!-- Pane Drawer -->
  <com.google.android.material.navigation.NavigationView
      android:id="@+id/navView"
      android:layout_width="wrap_content" android:layout_height="match_parent"
      android:layout_gravity="start"
      app:menu="@menu/menu_drawer"
      app:headerLayout="@layout/drawer_header"/>
</androidx.drawerlayout.widget.DrawerLayout>
```

**Header (tuỳ chọn)**

```xml
<!-- res/layout/drawer_header.xml -->
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:padding="16dp" android:orientation="vertical"
    android:layout_width="match_parent" android:layout_height="wrap_content">
  <ImageView android:layout_width="64dp" android:layout_height="64dp" android:src="@drawable/ic_user_placeholder"/>
  <TextView android:id="@+id/tvUser" android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="Xin chào!"/>
</LinearLayout>
```

**MainActivity (View Binding + NavigationUI)**

```kotlin
class MainActivity : AppCompatActivity() {

    private lateinit var b: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)

        setSupportActionBar(b.toolbar)

        val navController = findNavController(R.id.nav_host)

        // Khai báo các top-level để NavigationUI hiện icon “hamburger”
        val appBarConfig = AppBarConfiguration(
            setOf(R.id.homeFragment, R.id.favoriteFragment, R.id.profileFragment),
            b.drawerLayout // rất quan trọng để “Up” thành “hamburger”
        )
        setupActionBarWithNavController(navController, appBarConfig)

        // Gắn Drawer menu ↔ NavController
        b.navView.setupWithNavController(navController)

        // (Tuỳ chọn) Xử lý reselect (mặc định NavigationUI đã navigate giúp)
        b.navView.setNavigationItemSelectedListener { item ->
            val handled = NavigationUI.onNavDestinationSelected(item, navController)
            if (handled) b.drawerLayout.closeDrawers()
            handled
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host)
        return NavigationUI.navigateUp(navController, b.drawerLayout) || super.onSupportNavigateUp()
    }
}
```

**nav\_graph.xml** (đảm bảo id trùng menu)

```xml
<navigation xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    app:startDestination="@id/homeFragment">

  <fragment android:id="@+id/homeFragment" android:name="com.example.a011_navigation.ui.home.HomeFragment" android:label="Trang chủ"/>
  <fragment android:id="@+id/favoriteFragment" android:name="com.example.a011_navigation.ui.home.FavoriteFragment" android:label="Yêu thích"/>
  <fragment android:id="@+id/profileFragment" android:name="com.example.a011_navigation.ui.profile.ProfileFragment" android:label="Hồ sơ"/>
</navigation>
```

### Cách hoạt động

* `DrawerLayout` chứa **content** + **NavigationView** (ngăn kéo).
* `setupActionBarWithNavController(nav, appBarConfig)`:

  * Khi có `drawerLayout`, nút **Up** sẽ thành **hamburger** ở các **top-level**.
  * Ở màn con, icon tự đổi thành **Up**.
* `navView.setupWithNavController(nav)` ánh xạ item id ↔ destination id → bấm menu tự `navigate()`; NavigationUI cũng tự **đánh dấu checked**.

---
