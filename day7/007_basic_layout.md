# Buổi 7: Basic Layout
- [Buổi 7: Basic Layout](#buổi-7-basic-layout)
  - [I. Layout:](#i-layout)
    - [a. FrameLayout:](#a-framelayout)
    - [b. RelativeLayout:](#b-relativelayout)
    - [c. ConstraintLayout:](#c-constraintlayout)
    - [d. LinearLayout:](#d-linearlayout)
  - [II. ViewBinding:](#ii-viewbinding)

## I. Layout:

### a. FrameLayout:
- Android Framelayout là một lớp con ViewGroup được sử dụng để xác định vị trí của nhiều view được đặt chồng lên nhau để biểu diễn một màn hình view duy nhất.
- Ở đây, tất cả các view hoặc phần tử con được thêm vào theo định dạng ngăn xếp, nghĩa là view con được thêm gần đây nhất sẽ được hiển thị ở trên cùng của màn hình.
- Tuy nhiên, ta có thể thêm nhiều view con và kiểm soát vị trí của chúng chỉ bằng cách sử dụng `android:layout_gravity` trong FrameLayout. 1 số cách sử dụng:

| Giá trị           | Vị trí của view con   |
| ----------------- | --------------------- |
| bottom            | Nằm dưới FrameLayout  |
| top               | Trên FrameLayout      |
| center            | Nằm giữa FrameLayout  |
| center_horizontal | Giữa theo chiều ngang |
| center_vertical   | Giữa theo chiều dọc   |
| start             | Bắt đầu FrameLayout   |
| end               | Cuối FrameLayout      |
| right             | Bên phải              |
| left              | Bên trái              |

### b. RelativeLayout:
- RelativeLayout là một ViewGroup được sử dụng để giúp các view con xác định vị trí của nó so với View cha ( View con căn lề trên với View cha ) hoặc với View con khác ( View con A căn lề trái với View con B ).

![](https://freetuts.net/upload/tut_post/images/2020/02/28/2219/1.png)

- Cách định vị View trong RelativeLayout:

| **Thuộc tính**                     | **Mô tả**                                                                                    |
| ---------------------------------- | -------------------------------------------------------------------------------------------- |
| `android:layout_alignParentBottom` | Nếu `true`, làm cạnh dưới của view này kết nối với cạnh dưới của view cha.                   |
| `android:layout_alignParentEnd`    | Nếu `true`, làm cạnh kết thúc (end) của view này kết nối với cạnh kết thúc của view cha.     |
| `android:layout_alignParentLeft`   | Nếu `true`, làm cạnh trái của view này kết nối với cạnh trái của view cha.                   |
| `android:layout_alignParentRight`  | Nếu `true`, làm cạnh phải của view này kết nối với cạnh phải của view cha.                   |
| `android:layout_alignParentStart`  | Nếu `true`, làm cạnh bắt đầu (start) của view này kết nối với cạnh bắt đầu của view cha.     |
| `android:layout_alignParentTop`    | Nếu `true`, làm cạnh trên của view này kết nối với cạnh trên của view cha.                   |
| `android:layout_centerHorizontal`  | Nếu `true`, căn chỉnh giữa view này theo chiều ngang trong view cha.                         |
| `android:layout_centerInParent`    | Nếu `true`, căn chỉnh giữa view này theo cả chiều ngang và dọc trong view cha.               |
| `android:layout_centerVertical`    | Nếu `true`, căn chỉnh giữa view này theo chiều dọc trong view cha.                           |
| `android:layout_alignBottom`       | Làm cạnh dưới của view này kết nối với cạnh dưới của view có ID được chỉ định.               |
| `android:layout_alignTop`          | Làm cạnh trên của view này kết nối với cạnh trên của view có ID được chỉ định.               |
| `android:layout_alignLeft`         | Làm cạnh trái của view này kết nối với cạnh trái của view có ID được chỉ định.               |
| `android:layout_alignRight`        | Làm cạnh phải của view này kết nối với cạnh phải của view có ID được chỉ định.               |
| `android:layout_alignStart`        | Làm cạnh bắt đầu (start) của view này kết nối với cạnh bắt đầu của view có ID được chỉ định. |
| `android:layout_above`             | Đặt cạnh dưới của view này ở trên view có ID được chỉ định.                                  |
| `android:layout_below`             | Đặt cạnh trên của view này dưới view có ID được chỉ định.                                    |
| `android:layout_toEndOf`           | Đặt cạnh bắt đầu (start) của view này tới cạnh kết thúc (end) của view có ID được chỉ định.  |
| `android:layout_toLeftOf`          | Đặt cạnh phải của view này tới cạnh trái của view có ID được chỉ định.                       |
| `android:layout_toRightOf`         | Đặt cạnh trái của view này tới cạnh phải của view có ID được chỉ định.                       |
| `android:layout_toStartOf`         | Đặt cạnh kết thúc (end) của view này tới cạnh bắt đầu (start) của view có ID được chỉ định.  |

### c. ConstraintLayout:
- `ConstraintLayout` là 1 **ViewGroup** với đặc tính Constraint, việc này thể hiện ở các View trong ViewGroup luôn có những kết nối chặt chẽ với nhau. Tức là View này sẽ phụ thuộc View kia để xác định 4 thuộc tính cơ bản: X, Y, width, height.
- Trong đó ít nhất cần ràng buộc thuộc chiều ngang và ràng buộc theo chiều dọc cho thành phần view đó.
- Các ràng buộc:

| **Ràng buộc**                                    | **Mô tả**                                                                             |
| ------------------------------------------------ | ------------------------------------------------------------------------------------- |
| `app:layout_constraintTop_toTopOf/toBottomOf`    | Ràng buộc cạnh trên của view này vào **cạnh trên**/**cạnh dưới** của view khác        |
| `app:layout_constraintBottom_toBottomOf/toTopOf` | Ràng buộc cạnh dưới của view này vào **cạnh dưới**/**cạnh trên** của view khác        |
| `layout_constraintLeft_toLeftOf/toRightOf`         | Ràng buộc cạnh trái của phần tử tới bên **trái/phải** của view khác                   |
| `layout_constraintRight_toLeftOf/toRightOf   `     | Ràng buộc cạnh phải của phần tử tới cạnh **trái/phải** của view khác                  |
| `app:layout_constraintStart_toStartOf/toEndOf`   | Ràng buộc cạnh **trái (start)** của view này vào cạnh **trái**/**phải** của view khác |
| `app:layout_constraintEnd_toEndOf/toStartOf`     | Ràng buộc cạnh phải vào cạnh **phải**/**trái** của view khác                          |

- Guideline:
  - Cần neo nhiều view theo 1 trật tự thẳng hàng theo chiều ngang hoặc dọc, thì có thể tạo 1 line ẩnn để các view khác neo vào
    ```xml
    <?xml version="1.0" encoding="utf-8"?>
    <androidx.constraintlayout.widget.ConstraintLayout 
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <androidx.constraintlayout.widget.Guideline
            android:id="@+id/guideline_1"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:orientation="vertical"
            app:layout_constraintGuide_percent="0.3" />
            
    </androidx.constraintlayout.widget.ConstraintLayout>
    ```
    - Trong đó `android:orientation="vertical"` để chỉnh theo chiều ngang (horizontal) hoặc chiều dọc (vertical).
    - `app:layout_constraintGuide_percent="0.3"` là tỉ lệ cách cạnh trái/cạnh trên của ConstraintLayout. 

- Barrier:
  - Dùng để tạo ranh giới động (dynamic boundary) dựa trên vị trí của một hoặc nhiều View khác.

| Thuộc tính                      | Ý nghĩa    |
| ------------------------------- | ---------------------------------------------------------------- |
| `app:barrierDirection`          | Hướng rào chắn: `start`, `end`, `top`, `bottom`, `left`, `right` |
| `app:constraint_referenced_ids` | Danh sách ID của các View mà `Barrier` dựa vào                   |
| `app:barrierAllowsGoneWidgets`  | (mặc định `true`) Có tính luôn View bị `GONE` hay không          |
  - Nên sử dụng khi mà các View có chiều dài/thấp thay đổi
    ```xml
    <TextView
    android:id="@+id/textShort"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="Hello"
    app:layout_constraintTop_toTopOf="parent"
    app:layout_constraintStart_toStartOf="parent"/>

    <TextView
        android:id="@+id/textLong"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Xin chào, đây là một đoạn văn bản rất dài"
        app:layout_constraintTop_toBottomOf="@id/textShort"
        app:layout_constraintStart_toStartOf="parent"/>

    <androidx.constraintlayout.widget.Barrier
        android:id="@+id/textBarrier"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:barrierDirection="end"
        app:constraint_referenced_ids="textShort,textLong" />

    <Button
        android:id="@+id/button"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Tiếp"
        app:layout_constraintTop_toTopOf="@id/textShort"
        app:layout_constraintStart_toEndOf="@id/textBarrier"/>

    ```


- Chains - Tạo 1 chuỗi view:
  - Chains cho phép điều khiển khoảng trống giữa các thành phần và cách mà các thành phần sử dụng khoảng trống đó.
  - Có 4 chế độ: spread_inside, packed, spread và weighted.
  ![](https://images.viblo.asia/de49e339-b06f-4943-9cbe-0d71025bcd34.gif)
  - Trong XML 1 view nếu có dùng chains thì sẽ như sau:
    ```xml
    <?xml version="1.0" encoding="utf-8"?>
    <androidx.constraintlayout.widget.ConstraintLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <TextView
            android:id="@+id/textView"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            app:layout_constraintLeft_toLeftOf="parent"
            app:layout_constraintRight_toRightOf="parent"
            app:layout_constraintTop_toTopOf="parent"
            android:text="Hello Guys"/>

        <Button
            android:id="@+id/button"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            app:layout_constraintEnd_toStartOf="@id/button_two"
            app:layout_constraintHorizontal_chainStyle="packed"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toBottomOf="@id/textView"
            android:text="1" />

        <Button
            android:id="@+id/button_two"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            app:layout_constraintStart_toEndOf="@id/button"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintTop_toTopOf="@id/button"
            android:text="2"/>

    </androidx.constraintlayout.widget.ConstraintLayout>
    ```
  - Đầu tiên cần thiết lập phần tử đầu tiên của xích bằng `app:layout_constraintHorizontal_chainStyle` hoặc `app:layout_constraintVertical_chainStyle` và gán bằng kiểu chain muốn sử dụng.
  ![](https://images.viblo.asia/2d0c6894-15a8-403b-82e7-503808a130a9.png)

- Group:
  - Nhóm các view lại với nhau 1 cách logic. 
  - Group trong ConstraintLayout chỉ chứa các tham chiếu đến các view ids và sẽ không chứa các view bên trong nó (khác với GroupView).
  - Với group, ta có thể set việc hiển thị cho toàn bộ view mà group chứa đến. Nó sẽ hữu dụng khi mà ta dùng để hiển thị màn hình lỗi hoặc loading khi mà 1 vài thành phần của view cần ẩn hiện.  
  - Dùng `app:constraint_referenced_ids` để chứa danh sách các id trong group
    ```xml
        <android.support.constraint.Group
        android:id="@+id/group"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:constraint_referenced_ids="button_load,text_view_status" />
    ```

### d. LinearLayout:
- Là layout cơ bản dùng để sắp xếp nhiều sub-view tuần tự theo 1 hướng duy nhất, tức là theo chiều ngang (horizontal) hoặc chiều dọc (vertical). Để có thể sắp xếp thì dùng `android:orientation="horizontal"` -> các thành phần sắp xếp theo chiều ngang
![](https://media.geeksforgeeks.org/wp-content/uploads/20250116153653633822/New-Project.png)
- 1 số thuộc tính:

| **Attributes**            | **Mô tả (Description - Tiếng Việt)**                                                                                |
| ------------------------- | ------------------------------------------------------------------------------------------------------------------- |
| `android:id`              | Gán một ID duy nhất cho layout.                                                                                     |
| `android:orientation`     | Xác định cách sắp xếp các view con trong layout. Có thể là `"horizontal"` (ngang) hoặc `"vertical"` (dọc).          |
| `android:layout_width`    | Thiết lập chiều rộng cho layout.                                                                                    |
| `android:layout_height`   | Thiết lập chiều cao cho layout.                                                                                     |
| `android:layout_weight`   | Gán riêng cho từng view con, thuộc tính này xác định cách layout cha chia phần không gian còn lại cho các view con. |
| `android:weightSum`       | Được định nghĩa trong layout, thuộc tính này thiết lập tổng trọng số của tất cả các view con bên trong layout.      |
| `android:layout_gravity`  | Gán cho các view con, thuộc tính này thiết lập vị trí của view hoặc layout so với layout cha.                       |
| `android:baselineAligned` | Gán giá trị Boolean, thuộc tính này ngăn layout căn chỉnh đường cơ sở (baseline) của các view con.                  |

```xml
<LinearLayout
    xmlns:android1="http://schemas.android.com/apk/res/android"
    android1:layout_width="match_parent"
    android1:layout_height="match_parent"
    android1:orientation="horizontal" >

    <TextView
        android1:layout_width="80dp"
        android1:layout_height="80dp"
        android1:text="1"
        android1:textColor="#fff"
        android1:textSize="15pt"
        android1:textAlignment="center"
        android1:textStyle="bold"
        android1:background="@color/black" />

    <TextView
        android1:layout_width="80dp"
        android1:layout_height="80dp"
        android1:text="2"
        android1:textColor="#000"
        android1:textSize="15pt"
        android1:textAlignment="center"
        android1:textStyle="bold"
        android1:background="@color/white" />

    <TextView
        android1:layout_width="80dp"
        android1:layout_height="80dp"
        android1:text="3"
        android1:textColor="#fff"
        android1:textSize="15pt"
        android1:textAlignment="center"
        android1:textStyle="bold"
        android1:background="#8c0520" />

    <TextView
        android1:layout_width="80dp"
        android1:layout_height="80dp"
        android1:background="#efcd21"
        android1:text="4"
        android1:textAlignment="center"
        android1:textColor="#fff"
        android1:textSize="15pt"
        android1:textStyle="bold" />
</LinearLayout>
```

## II. ViewBinding:
- Các layout cần viết thường, không cách, nếu có dùng `_`. Vdu: `activity_main.xml`. Khi ấy view binding sẽ generate ra 1 class với tên kiểu pascal: `ActivityMain` bao gồm 1 property cho mọi view với Id trong layout.
- Phần tử gốc của layout luôn được lưu trữ trong 1 property được gọi là **root** (được tạo tự động). Trong một phương thức `onCreate` của **Activity**, pass `root` đến `setContentView` để báo cho **Activity** sử dụng layout từ **binding object**.
- Thiết lập:  Vào `build.gradle.kts (Module :App)`. Tạo mới `buildFeatures` và set `viewBinding = true`. Sau đó sync now
- Cách sử dụng:
    - Trong MainActivity class, tạo `private lateinit var binding: <Tên fileXML viết liền, viết hoa chữ cái đầu>Binding>`
    - Sử dụng `inflate(layoutInflater)` để khởi tạo binding
    - Sử dụng `binding.<Tên view>` để truy cập tới view đó, kết hợp với `setOnclickListener()` để thực hiện thao tác với view đó
- Ví dụ

MainActivity.kt
```kotlin
package com.example.testapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.testapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.edtMessage.setOnClickListener {
            val message = binding.edtMessage.text.toString()
            binding.tvMessage.text = message
        }
    }
}
```

activity_main.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:fitsSystemWindows="true">

    <EditText
        android:id="@+id/edt_message"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:background="#D5BFE4"
        android:hint="Name"
        android:inputType="textPersonName"
        android:minHeight="48dp"
        android:textColor="#000000"
        android:textSize="32sp"
        android:textStyle="bold"
        android:padding="12dp"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"/>

    <TextView
        android:id="@+id/tv_message"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_marginTop="10dp"
        android:background="#D5BFE4"
        android:minHeight="48dp"
        android:textColor="#000000"
        android:textSize="32sp"
        android:textStyle="bold"
        android:padding="12dp"
        app:layout_constraintTop_toBottomOf="@id/edt_message"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        />

</androidx.constraintlayout.widget.ConstraintLayout>
```