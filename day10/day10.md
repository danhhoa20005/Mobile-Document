# RecyclerView trong android

---
## RECYCLER

---

### Định nghĩa
**RecyclerView** là một **thành phần ViewGroup cực kỳ linh hoạt**, được giới thiệu như **phiên bản nâng cấp hiện đại** thay thế cho **ListView** và **GridView** trong Android. Thành phần này cho phép **hiển thị danh sách hoặc lưới dữ liệu rất lớn** một cách **tối ưu và hiệu quả** nhờ **cơ chế tái sử dụng view (recycle)**.

chỉ cần **cung cấp dữ liệu** và **thiết kế giao diện cho từng mục (item)**, còn **RecyclerView** sẽ **tự động tạo item khi cần** và **tận dụng lại các view đã cuộn khỏi màn hình**.

**cơ chế “recycle”** này giúp:

* **Tiết kiệm đáng kể thời gian xử lý, cuộn danh sách mượt mà hơn**: Khi một phần tử cuộn khỏi màn hình, `RecyclerView` sẽ **tái sử dụng** view đó cho phần tử mới sắp xuất hiện.
* **Hiệu quả với danh sách lớn**: Cho phép **tái chế** các view đã ra khỏi màn hình, giảm tạo mới liên tục, tiết kiệm bộ nhớ.
* **Linh hoạt bố cục**: Hỗ trợ sắp xếp item theo nhiều kiểu — **ngang**, **dọc**, **grid**, hoặc **staggered grid** — tối ưu hơn so với ListView.
* **Hỗ trợ Animation**: Dễ dàng thêm hiệu ứng khi **thêm, xóa, cập nhật** phần tử, giúp trải nghiệm mượt và sinh động.
* **Lazy binding**: Chỉ binding dữ liệu khi item thực sự xuất hiện trong vùng hiển thị, tránh lãng phí tài nguyên.


RecyclerView thuộc thư viện **AndroidX (Jetpack)**, cần thêm dependency `androidx.recyclerview:recyclerview` vào dự án (Android Studio thường sẽ tự thêm khi tạo dự án mới). Thành phần này được thiết kế **cực kỳ linh hoạt**, cho phép **tùy biến cao** về cách sắp xếp item, hiệu ứng chuyển động (animation) và nhiều tính năng khác, khắc phục các hạn chế của **ListView** trước đây.

Ví dụ:

* **ListView** chỉ hỗ trợ danh sách dọc và khó khăn khi muốn thêm hiệu ứng khi thêm/xóa phần tử.
* **RecyclerView** hỗ trợ nhiều kiểu layout: **ngang**, **lưới**, **so le**.
* Tích hợp sẵn **ItemAnimator** để tự động tạo animation khi mục thay đổi, giúp giao diện trực quan và sinh động hơn.

---
## Nguyên lý hoạt động

**RecyclerView** quản lý và tái sử dụng các `View` thông qua **ViewHolder** và **Adapter**.

* Khi cần hiển thị một item mới, nó sẽ gọi các phương thức của **Adapter** tại thời điểm thích hợp để **tạo mới** hoặc **tái sử dụng** `ViewHolder`, đồng thời cập nhật dữ liệu tương ứng.
* **LayoutManager** quyết định cách sắp xếp các item (ngang, dọc, lưới,…) và khi nào cần tải thêm dữ liệu (ví dụ: khi cuộn đến cuối danh sách).
* Mô hình này **bắt buộc** sử dụng **ViewHolder pattern** (trong khi ở **ListView** chỉ là khuyến nghị) để tối ưu hiệu năng, giảm số lần gọi `findViewById` và hạn chế việc tạo `View` mới không cần thiết.


`ViewHolder → Adapter → LayoutManager → RecyclerView`.

## **Các Thành phần Chính của RecyclerView**




1. **RecyclerView**

    * Là một `ViewGroup` chứa các item view.
    * Quản lý danh sách tổng thể, lắng nghe thao tác cuộn, quản lý **pool ViewHolder** để tái chế view.
    * Được thêm vào layout XML giống như bất kỳ ViewGroup nào khác.

2. **Adapter** (`RecyclerView.Adapter`)

    * Giữ dữ liệu và cung cấp các **ViewHolder** cho RecyclerView.
    * **Các phương thức chính**:

        * `onCreateViewHolder(parent, viewType)`: Inflate layout item và tạo mới ViewHolder.
        * `onBindViewHolder(holder, position)`: Gán dữ liệu tại vị trí `position` vào các view trong ViewHolder.
        * `getItemCount()`: Trả về số lượng phần tử trong danh sách dữ liệu.

3. **ViewHolder** (`RecyclerView.ViewHolder`)

    * Giữ tham chiếu đến các view con trong layout của item.
    * Giúp tránh gọi `findViewById` nhiều lần → tăng hiệu suất.
    * Bắt buộc khi dùng RecyclerView (khác với ListView chỉ khuyến khích).

4. **LayoutManager** (`RecyclerView.LayoutManager`)

    * Quyết định cách sắp xếp và tái chế các item.
    * Các loại có sẵn:

        * `LinearLayoutManager`: Danh sách dọc hoặc ngang.
        * `GridLayoutManager`: Bố cục dạng lưới với số cột/hàng cố định.
        * `StaggeredGridLayoutManager`: Lưới so le, kích thước item linh hoạt.
    * Có thể tự viết LayoutManager tùy chỉnh.

5. **ItemAnimator** (`RecyclerView.ItemAnimator`)

    * Quản lý hiệu ứng khi thêm, xóa, thay đổi item.
    * Mặc định dùng `DefaultItemAnimator`. Có thể tùy chỉnh hoặc tắt.

6. **ItemDecoration** (`RecyclerView.ItemDecoration`)

    * Thêm phần trang trí hoặc khoảng cách cho item (ví dụ divider).
    * RecyclerView không có sẵn divider như ListView, cần tự cài hoặc dùng `DividerItemDecoration`.

---

**Tóm lại:**
`RecyclerView` là khung chứa, `Adapter` và `ViewHolder` đảm nhận tạo + gán dữ liệu cho item, `LayoutManager` lo bố trí, còn `ItemAnimator` và `ItemDecoration` giúp hoàn thiện hiệu ứng và trang trí.

## **Ưu điểm của RecyclerView **

1. **Hiệu năng & bộ nhớ tối ưu hơn**

   * Tự động tái sử dụng view cho item ngoài màn hình, không giữ toàn bộ item trong bộ nhớ.
   * Bắt buộc dùng **ViewHolder**, tránh tạo view dư thừa và hạn chế gọi `findViewById` nhiều lần → cuộn mượt hơn.

2. **Bắt buộc áp dụng ViewHolder Pattern**

   * Adapter luôn tách biệt việc **tạo view** (`onCreateViewHolder`) và **bind dữ liệu** (`onBindViewHolder`).
   * Đảm bảo mọi item được tái chế hiệu quả, loại bỏ lỗi do quên tối ưu như ở ListView.

3. **Bố cục linh hoạt**

   * Hỗ trợ nhiều dạng bố cục: danh sách dọc/ngang (`LinearLayoutManager`), lưới (`GridLayoutManager`), lưới so le (`StaggeredGridLayoutManager`).
   * Không cần tạo widget riêng như khi dùng ListView + GridView.

4. **Hiệu ứng & hoạt ảnh mượt mà**

   * Có sẵn **ItemAnimator** để animate khi thêm, xóa, thay đổi item.
   * Dễ dàng gọi `notifyItemInserted`, `notifyItemRemoved`… để tạo hiệu ứng mượt.

5. **Thông báo thay đổi dữ liệu linh hoạt**

   * Hỗ trợ cập nhật cục bộ (`notifyItemChanged`, `notifyItemRangeInserted`…), không phải load lại toàn bộ danh sách như `notifyDataSetChanged` của ListView.

6. **Trang trí & tương tác tùy biến**

   * Có **ItemDecoration** để tùy chỉnh phân cách, viền, khoảng cách giữa item.
   * Tùy biến xử lý sự kiện click ngay trong ViewHolder, linh hoạt hơn OnItemClickListener của ListView.

---

**Kết luận:**
RecyclerView khắc phục hầu hết hạn chế của ListView, mang lại hiệu suất cao, bố cục linh hoạt, hiệu ứng mượt và khả năng tùy biến mạnh. Nhược điểm duy nhất là code ban đầu phức tạp hơn (cần viết Adapter + ViewHolder).


## **Cơ chế tái sử dụng View trong RecyclerView**

### 1. **ViewHolder Pattern**

* Mỗi item được quản lý bởi một **ViewHolder**, giữ tham chiếu tới các view con (TextView, ImageView…).
* Giúp tránh gọi `findViewById` nhiều lần khi cuộn → giảm chi phí tìm view trong cây giao diện.
* Luôn được **RecyclerView** bắt buộc dùng, đảm bảo việc cache view hiệu quả.

### 2. **RecycledViewPool**

* Là “kho” chứa các ViewHolder không còn hiển thị.
* Khi item cuộn ra ngoài màn hình → ViewHolder được đưa vào pool.
* Khi cần hiển thị item mới → lấy ViewHolder phù hợp từ pool, cập nhật dữ liệu qua `onBindViewHolder`.
* Chỉ tạo ViewHolder mới nếu pool không có sẵn loại phù hợp.

### 3. **Chu trình khi cuộn**

1. Item cuộn ra ngoài → ViewHolder vào pool.
2. Item mới cuộn vào → lấy từ pool nếu có.
3. Gọi `onBindViewHolder` để bind dữ liệu mới.
4. Nếu không có → gọi `onCreateViewHolder` để tạo mới.

> Số View tạo ra thường chỉ bằng số item hiển thị + vài view đệm, kể cả danh sách có hàng ngàn mục.

---

## **Vì sao RecyclerView hiệu năng cao hơn?**

* **Giảm số View tạo mới** → chỉ tạo khi thật sự cần.
* **Ít inflate layout & findViewById**(đã thay thế bởi binding) → tiết kiệm CPU, giảm lag khi cuộn nhanh.
* **Tiết kiệm bộ nhớ** → ít View hơn, giảm áp lực cho hệ thống.
* **Cập nhật cục bộ** → chỉ bind lại item thay đổi, không refresh toàn bộ.
* **Cuộn mượt, tiết kiệm pin** → ít xử lý UI mỗi khung hình, duy trì 60fps ổn định.

## **Adapter trong RecyclerView là gì?**

* **RecyclerView\.Adapter** là **cầu nối** giữa **dữ liệu** và **RecyclerView**.
* Chịu trách nhiệm:

   1. **Tạo** ViewHolder cho item.
   2. **Gán dữ liệu** vào các View trong ViewHolder.
* RecyclerView sẽ gọi các phương thức của Adapter khi cần hiển thị hoặc tái sử dụng item view.

---

## **Ba phương thức quan trọng cần override**

1. **`onCreateViewHolder(parent, viewType)`**

   * Tạo mới ViewHolder (inflate layout của item).
   * View tạo ra **chưa có dữ liệu** cho đến khi bind.

2. **`onBindViewHolder(holder, position)`**

   * Gắn dữ liệu của vị trí `position` vào các View trong ViewHolder.

3. **`getItemCount()`**

   * Trả về số lượng phần tử trong danh sách dữ liệu.

---

### **Ví dụ**

```kotlin
class CustomAdapter(private val dataSet: List<String>) 
    : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                     .inflate(R.layout.text_row_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = dataSet[position]
    }

    override fun getItemCount(): Int = dataSet.size
}
```

**Cách sử dụng:**

```kotlin
recyclerView.layoutManager = LinearLayoutManager(this)
recyclerView.adapter = CustomAdapter(myDataList)
```

## **ListAdapter **

### 1. **Vấn đề khi dùng Adapter thường**

* Khi dữ liệu thay đổi, thường phải gọi:

   * `notifyDataSetChanged()` → vẽ lại toàn bộ danh sách, tốn tài nguyên, mất animation mượt.
   * Hoặc `notifyItemInserted()` / `notifyItemRemoved()` → phải tự tính toán vị trí, dễ sai.

---

### 2. **ListAdapter là gì?**

* Thuộc `androidx.recyclerview.widget.ListAdapter`.
* Kế thừa `RecyclerView.Adapter` và **tích hợp sẵn** cơ chế so sánh danh sách bằng **DiffUtil** + **AsyncListDiffer**.
* Tự động:

   * So sánh danh sách cũ & mới.
   * Chỉ cập nhật item thay đổi → hiệu năng tốt, animation mượt.
* Không cần tự viết `getItemCount()` (ListAdapter tự quản lý).

---

### 3. **Cơ chế DiffUtil**

**Cơ chế DiffUtil** là một tiện ích trong Android (nằm trong gói `androidx.recyclerview.widget.DiffUtil`) dùng để **so sánh danh sách cũ và mới** rồi **tính toán các thay đổi tối ưu** (thêm, xóa, sửa item) để cập nhật **RecyclerView** một cách hiệu quả nhất.

---

#### **1. Mục đích**

* Tránh phải dùng `notifyDataSetChanged()` (làm mới toàn bộ danh sách → tốn tài nguyên, mất animation).
* Chỉ cập nhật **những item thực sự thay đổi** → tiết kiệm CPU, GPU, mượt hơn.
* Tự động sinh animation thêm/xóa/sửa item nếu dùng với `RecyclerView.Adapter` hoặc `ListAdapter`.

---

#### **2. Nguyên lý hoạt động**

* DiffUtil so sánh **danh sách cũ** và **danh sách mới** bằng một lớp `DiffUtil.Callback` hoặc `DiffUtil.ItemCallback`.
* Quá trình so sánh diễn ra trên **background thread** (nếu dùng `AsyncListDiffer` hoặc `ListAdapter`).
* Kết quả so sánh là **một danh sách các thao tác cập nhật** (insert, remove, move, change).
* RecyclerView áp dụng các thao tác này để cập nhật UI **mượt mà**.

---

#### **3. Hai phương thức so sánh quan trọng**

Khi dùng `DiffUtil.ItemCallback<T>`, cần override:

1. **`areItemsTheSame(oldItem, newItem)`**

   * Kiểm tra xem hai item có **cùng danh tính** không.
   * Thường so sánh **ID duy nhất** của item.

2. **`areContentsTheSame(oldItem, newItem)`**

   * Kiểm tra xem nội dung hai item có giống nhau không.
   * Chỉ được gọi nếu `areItemsTheSame` trả về `true`.

---

#### **4. Ví dụ**

```kotlin
class WordDiffCallback : DiffUtil.ItemCallback<Word>() {
    override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
        return oldItem.id == newItem.id // Cùng ID => cùng item
    }

    override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
        return oldItem.word == newItem.word // Nội dung giống => không cần update
    }
}
```

---

#### **5. Cách dùng với ListAdapter**

```kotlin
class WordListAdapter : ListAdapter<Word, WordViewHolder>(WordDiffCallback()) {
    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
```

* Khi dữ liệu mới → chỉ cần gọi `submitList(newList)`.
* DiffUtil sẽ tự tính toán và chỉ update item thay đổi.

---

#### **6. Ưu điểm**

* **Hiệu năng tốt**: Chỉ cập nhật phần tử cần thiết.
* **Animation mượt**: Tự động tạo hiệu ứng thêm/xóa.
* **Code gọn hơn**: Không cần tự tính toán vị trí thay đổi.


---

### 4. **Tạo ListAdapter**

```kotlin
class WordListAdapter :
    ListAdapter<Word, WordListAdapter.WordViewHolder>(WordDiffCallback()) {

    inner class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val wordItemView: TextView = itemView.findViewById(R.id.textView)
        fun bind(text: String) { wordItemView.text = text }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item, parent, false)
        return WordViewHolder(view)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val currentWord = getItem(position)
        holder.bind(currentWord.word)
    }
}
```

---

### 5. **Cập nhật dữ liệu**

* Gọi **`submitList(newList)`**:

   * Tự động chạy DiffUtil so sánh với danh sách cũ.
   * Chỉ update item thay đổi → tiết kiệm tài nguyên, có animation mượt.

🔹 **Ưu điểm:** Không cần tự tính toán vị trí thay đổi, code gọn hơn, hiệu suất cao, animation có sẵn.


### 6. **So sánh nhanh: Adapter thường vs ListAdapter**

| Tiêu chí                   | **Adapter thường**                                                                                   | **ListAdapter**                                                 |
| -------------------------- | ---------------------------------------------------------------------------------------------------- | --------------------------------------------------------------- |
| Quản lý dữ liệu            | Lập trình viên tự quản lý danh sách và gọi `notify...` thủ công                                      | Tự quản lý danh sách nội bộ                                     |
| Cập nhật UI                | `notifyDataSetChanged()` làm mới toàn bộ, dễ gây giật; `notifyItem...` cần tính toán vị trí thủ công | Chỉ cần gọi `submitList(newList)`, tự tính toán vị trí thay đổi |
| Hiệu năng                  | Dễ lãng phí tài nguyên nếu không tối ưu                                                              | Chỉ vẽ lại item thay đổi, có animation mượt                     |
| Độ phức tạp code           | Cần nhiều code xử lý cập nhật                                                                        | Gọn hơn, chỉ viết `submitList`                                  |
| Kết hợp LiveData/ViewModel | Thực hiện thủ công việc cập nhật                                                                     | Tích hợp tốt, chỉ cần observer gọi `submitList`                 |

---

## **RecyclerView – Multiple View Types**
**RecyclerView – Multiple View Types** nghĩa là trong **một RecyclerView** có thể hiển thị **nhiều loại item** với **bố cục (layout) khác nhau** thay vì tất cả item đều giống nhau.

Ví dụ:

* Ứng dụng mạng xã hội: một danh sách bài đăng có thể gồm **bài text**, **bài ảnh**, **bài video**, **quảng cáo**… mỗi loại cần layout riêng.
* Ứng dụng chat: danh sách tin nhắn gồm **tin nhắn của mình** và **tin nhắn của người khác**, mỗi loại hiển thị khác nhau.

---

**Cơ chế hoạt động:**

* Mỗi item trong danh sách được gán **một mã loại view** (`viewType`).
* Adapter sẽ dựa vào `viewType` để **inflate layout phù hợp** và **tạo ViewHolder** tương ứng.
* Khi bind dữ liệu, Adapter sẽ **xử lý riêng** cho từng loại ViewHolder.

---

**Ưu điểm:**

* Cho phép hiển thị danh sách phong phú và phức tạp.
* Tái sử dụng view hiệu quả, vẫn tận dụng cơ chế **ViewHolder** và **RecyclerView pool**.


### 1. **Khi nào dùng**

* Khi danh sách chứa **nhiều loại item** với bố cục khác nhau.
* Ví dụ: feed mạng xã hội gồm **status text**, **hình ảnh**, **video**, **quảng cáo**…

---

### 2. **Cách triển khai**

#### Bước 1: Định nghĩa mã loại view

```kotlin
companion object {
    const val TYPE_USER = 0
    const val TYPE_IMAGE = 1
}
```

#### Bước 2: Xác định loại view cho từng vị trí

```kotlin
override fun getItemViewType(position: Int): Int {
    return if (items[position] is User) TYPE_USER else TYPE_IMAGE
}
```

#### Bước 3: Tạo ViewHolder theo loại view

```kotlin
override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    return when (viewType) {
        TYPE_USER -> UserViewHolder(inflater.inflate(R.layout.item_user, parent, false))
        else -> ImageViewHolder(inflater.inflate(R.layout.item_image, parent, false))
    }
}
```

#### Bước 4: Bind dữ liệu tương ứng

```kotlin
override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    when (holder) {
        is UserViewHolder -> holder.bind(items[position] as User)
        is ImageViewHolder -> holder.bind(items[position] as Image)
    }
}
```

---







