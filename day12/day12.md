# Cơ sở dữ liệu
## 1.1 Cơ sở dữ liệu (CSDL)

### Khái niệm

Cơ sở dữ liệu (**Database – DB**) là một tập hợp dữ liệu có liên quan với nhau, được tổ chức, lưu trữ và quản lý một cách **có hệ thống** để phục vụ cho việc truy xuất, cập nhật, và xử lý dữ liệu hiệu quả.

Khác với việc lưu dữ liệu rời rạc (ví dụ: file Word, Excel, sổ tay), cơ sở dữ liệu được xây dựng trên nền tảng phần mềm quản trị (DBMS – Database Management System) để đảm bảo dữ liệu có cấu trúc, toàn vẹn và dễ dàng thao tác trong các ứng dụng thực tế như quản lý nhân sự, thương mại điện tử, ngân hàng, y tế, v.v.

---

### Đặc điểm của CSDL

1. **Tính tổ chức và cấu trúc**

    * Dữ liệu được tổ chức dưới dạng bảng, đối tượng, hoặc tập hồ sơ.
    * Mỗi bảng có các cột (thuộc tính) và hàng (bản ghi), giúp quản lý thông tin khoa học và rõ ràng.

2. **Khả năng truy xuất và tìm kiếm**

    * Người dùng có thể nhanh chóng truy vấn dữ liệu theo điều kiện (ví dụ: tìm sinh viên theo mã, tìm sản phẩm theo tên).
    * DBMS tối ưu hóa việc tìm kiếm, giúp truy xuất dữ liệu nhanh chóng ngay cả khi dữ liệu rất lớn.

3. **Tính nhất quán và toàn vẹn**

    * Đảm bảo dữ liệu chính xác và không mâu thuẫn.
    * Các ràng buộc toàn vẹn (như khóa chính, khóa ngoại) giúp duy trì mối quan hệ chặt chẽ giữa các bảng.

4. **Khả năng mở rộng**

    * CSDL có thể lưu trữ và xử lý hàng triệu đến hàng tỷ bản ghi khi ứng dụng phát triển.
    * Hệ thống có thể nâng cấp phần cứng và tối ưu phần mềm để đáp ứng nhu cầu ngày càng lớn.

5. **Bảo mật và quyền truy cập**

    * CSDL hỗ trợ phân quyền (role, user): ai được phép đọc, ghi, xóa hoặc quản lý dữ liệu.
    * Bảo vệ dữ liệu khỏi các truy cập trái phép, giảm rủi ro mất mát thông tin.

6. **Khả năng sao lưu và phục hồi**

    * DBMS cho phép **sao lưu định kỳ** dữ liệu để phòng ngừa rủi ro.
    * Khi có sự cố (mất điện, hỏng ổ cứng, tấn công mạng), dữ liệu có thể được **phục hồi** từ bản sao lưu.

7. **Quản lý giao dịch (Transaction Management)**

    * Một giao dịch gồm nhiều thao tác dữ liệu (INSERT, UPDATE, DELETE).
    * DBMS đảm bảo các giao dịch tuân thủ nguyên tắc **ACID**:

        * **A**tomicity (Nguyên tử): hoặc thực hiện toàn bộ, hoặc không gì cả.
        * **C**onsistency (Nhất quán): dữ liệu trước và sau giao dịch luôn hợp lệ.
        * **I**solation (Cô lập): nhiều giao dịch song song không ảnh hưởng nhau.
        * **D**urability (Bền vững): khi giao dịch thành công, dữ liệu được lưu vĩnh viễn.

8. **Khả năng hỗ trợ đồng thời**

    * Cho phép nhiều người dùng truy cập và thao tác cùng lúc trên cùng một dữ liệu mà không gây xung đột.
    * Ví dụ: nhiều nhân viên cùng lúc cập nhật đơn hàng trên hệ thống thương mại điện tử.

9. **Tính tương thích**

    * CSDL có thể kết nối và làm việc với nhiều loại ứng dụng, ngôn ngữ lập trình khác nhau (Java, Python, PHP...).
    * Đảm bảo khả năng tích hợp hệ thống dễ dàng.

---

### Minh họa

Ví dụ về cơ sở dữ liệu **Quản lý Sinh viên**:

* Bảng **SinhVien**: chứa thông tin sinh viên (Mã SV, Họ tên, Ngày sinh, Mã lớp).
* Bảng **LopHoc**: chứa thông tin lớp học (Mã lớp, Tên lớp, Giáo viên chủ nhiệm).

CSDL giúp bạn dễ dàng **tìm kiếm sinh viên theo lớp**, **cập nhật thông tin cá nhân**, hay **thống kê số lượng sinh viên theo khoa**.

---

## 1.2 Cơ sở dữ liệu quan hệ (CSDLQH)

### Khái niệm

* **CSDL quan hệ (Relational Database – RDB)** là mô hình lưu trữ dữ liệu phổ biến nhất hiện nay.
* Dữ liệu được tổ chức thành **các bảng (table)**. Mỗi bảng gồm nhiều **hàng (row/record)** và **cột (field/column)**.
* Các bảng có thể **liên kết với nhau thông qua khóa (keys)**, giúp đảm bảo dữ liệu có mối quan hệ logic và không bị trùng lặp.

Ví dụ:

* Bảng **KhachHang** lưu thông tin khách hàng (ID, Họ tên, SĐT).
* Bảng **DonHang** lưu thông tin đơn hàng (Mã đơn, Ngày đặt, ID khách hàng).
* Cột **ID khách hàng** trong DonHang chính là **khóa ngoại** tham chiếu đến **ID** trong bảng KhachHang → tạo mối quan hệ **một khách hàng có thể có nhiều đơn hàng**.

---

### Các thành phần cơ bản

1. **Field (Cột/Trường)**

    * Mỗi cột biểu diễn một **thuộc tính** của thực thể.
    * Ví dụ: trong bảng SinhVien, các field có thể là: MaSV, HoTen, NgaySinh, MaLop.

2. **Row (Dòng/Bản ghi – Record)**

    * Mỗi dòng chứa một **bản ghi dữ liệu** đầy đủ.
    * Ví dụ: `101 | Nguyễn Văn A | 2001 | CNTT01`.

3. **Cell (Ô dữ liệu)**

    * Giao điểm giữa một dòng và một cột.
    * Ví dụ: ô tại hàng "Nguyễn Văn A" và cột "NamSinh" chứa giá trị "2001".

4. **Primary Key (Khóa chính)**

    * Là một hoặc nhiều cột dùng để **định danh duy nhất** một bản ghi.
    * Giá trị **không được trùng lặp** và **không được để trống**.
    * Ví dụ: cột **MaSV** trong bảng SinhVien.

5. **Foreign Key (Khóa ngoại)**

    * Là cột dùng để **liên kết với khóa chính** của bảng khác.
    * Đảm bảo tính toàn vẹn tham chiếu giữa các bảng.
    * Ví dụ: cột **MaLop** trong bảng SinhVien là khóa ngoại tham chiếu đến **MaLop** của bảng LopHoc.

---

### Ưu điểm của CSDL quan hệ

* **Tính linh hoạt cao**: dễ mở rộng thêm bảng, thêm cột, liên kết dữ liệu.
* **Hỗ trợ truy vấn mạnh mẽ**: nhờ ngôn ngữ SQL, có thể thực hiện các truy vấn phức tạp (tìm kiếm, lọc, thống kê, nhóm...).
* **Toàn vẹn và nhất quán dữ liệu**: nhờ ràng buộc khóa chính, khóa ngoại, các dữ liệu luôn đồng bộ.
* **Tương thích rộng rãi**: hầu hết các hệ quản trị CSDL (MySQL, SQL Server, Oracle, PostgreSQL, SQLite) đều dùng mô hình quan hệ.

---

### Nhược điểm

* **Hiệu suất có thể giảm** khi dữ liệu cực kỳ lớn hoặc khi có nhiều quan hệ phức tạp.
* **Quản trị phức tạp**: cần thiết kế chuẩn hóa dữ liệu và quản lý quan hệ chặt chẽ.
* **Tốn tài nguyên**: khi chạy nhiều truy vấn JOIN lớn có thể tiêu tốn bộ nhớ và CPU.

---

### Minh họa

Trong **mô hình quan hệ**, có thể hình dung:

* **KhachHang (Customer)**

    * ID\_KhachHang (PK)
    * HoTen
    * DiaChi

* **DonHang (Order)**

    * ID\_DonHang (PK)
    * NgayDat
    * ID\_KhachHang (FK)

→ Một khách hàng có thể có nhiều đơn hàng. Khi truy vấn, chúng ta dùng **JOIN** để kết nối 2 bảng và lấy đầy đủ thông tin khách hàng cùng đơn hàng của họ.

---

## 2. SQL

### 2.1 SQL là gì?

* **SQL (Structured Query Language)** – Ngôn ngữ truy vấn có cấu trúc – là ngôn ngữ tiêu chuẩn dùng để **làm việc với cơ sở dữ liệu quan hệ**.
* SQL không phải ngôn ngữ lập trình như Java, C++ mà là **ngôn ngữ khai báo**, cho phép người dùng **mô tả dữ liệu muốn lấy hoặc muốn xử lý**, còn việc thực hiện chi tiết do hệ quản trị CSDL đảm nhiệm.

**Chức năng chính của SQL**:

1. **Truy vấn dữ liệu**: lấy dữ liệu từ bảng (`SELECT`).
2. **Thao tác dữ liệu**: thêm (`INSERT`), sửa (`UPDATE`), xóa (`DELETE`).
3. **Định nghĩa dữ liệu**: tạo, chỉnh sửa, hoặc xóa bảng, cơ sở dữ liệu (`CREATE`, `ALTER`, `DROP`).
4. **Kiểm soát quyền truy cập**: phân quyền người dùng (`GRANT`, `REVOKE`).
5. **Quản lý giao dịch**: đảm bảo các thao tác dữ liệu an toàn và nhất quán (`COMMIT`, `ROLLBACK`).

Nhờ SQL, ta có thể **lưu trữ, cập nhật, loại bỏ, tìm kiếm và phân tích dữ liệu** trong cơ sở dữ liệu một cách hiệu quả. Ngoài ra, SQL còn được dùng để **tối ưu hóa hiệu suất** truy vấn thông qua việc tạo chỉ mục (index), thống kê dữ liệu, v.v.

---

### 2.2 Cài đặt MySQL

**MySQL** là một hệ quản trị cơ sở dữ liệu quan hệ (RDBMS) phổ biến, miễn phí và mã nguồn mở, hỗ trợ SQL. Đây là lựa chọn thường gặp cho ứng dụng web, mobile, và phần mềm quản lý.

#### Cài đặt trên Windows (máy tính)

1. Truy cập trang chủ MySQL: [Tải MySQL Community Server](https://dev.mysql.com/downloads/installer/).

---

## 3. Database và Table

### 3.1 Database

* Trong **MySQL**, **Database (Cơ sở dữ liệu)** là một tập hợp có tổ chức của **các bảng (tables)**, trong đó mỗi bảng chứa dữ liệu có liên quan.
* Một **database** có thể bao gồm nhiều bảng khác nhau. Ví dụ: database **Student** có thể chứa bảng **SinhVien**, **LopHoc**, **MonHoc**.
* Mỗi bảng lại chứa các **hàng (rows)** và **cột (columns)** để lưu trữ dữ liệu chi tiết.

#### Cách tạo Database trong MySQL

Có 2 cách phổ biến: dùng **MySQL Workbench** (giao diện trực quan) hoặc dùng **câu lệnh SQL**.

1. **Tạo bằng MySQL Workbench (GUI)**

    * Mở MySQL Workbench.
    * Ấn vào dấu **+** để tạo kết nối mới.
    * Chọn **File → New Query Tab** để mở cửa sổ nhập lệnh SQL.
    * Nhập lệnh:

      ```sql
      CREATE DATABASE Student;
      ```
    * Sau khi chạy lệnh, bên cột **Schemas** sẽ xuất hiện database mới tên **Student**.

2. **Xóa database**

   ```sql
   DROP DATABASE Student;
   ```

    Lệnh này sẽ **xóa toàn bộ bảng và dữ liệu** trong database, nên cần thận trọng.

---

### 3.2 Table

* **Table (Bảng)** là cấu trúc dữ liệu chính trong database, dùng để lưu thông tin theo dạng **hàng (row)** và **cột (column)**.
* Một bảng thường đại diện cho một thực thể. Ví dụ: bảng **SinhVien** lưu thông tin sinh viên, bảng **MonHoc** lưu thông tin môn học.

#### Thành phần cơ bản của Table

1. **Tên bảng**: tên duy nhất trong database (vd: `SinhVien`).
2. **Cột (Column/Field)**: biểu diễn thuộc tính dữ liệu. Mỗi cột có tên và kiểu dữ liệu (INT, VARCHAR, DATE...).
3. **Hàng (Row/Record)**: mỗi bản ghi dữ liệu (vd: 1 sinh viên).
4. **Khóa chính (Primary Key)**: xác định duy nhất mỗi bản ghi (vd: `MaSV`).
5. **Khóa ngoại (Foreign Key)**: liên kết bảng hiện tại với bảng khác.
6. **Chỉ mục (Index)**: giúp tăng tốc độ tìm kiếm dữ liệu.

---

#### Tạo bảng trong MySQL

* **Cú pháp**:

```sql
CREATE TABLE table_name (
    column1 datatype,
    column2 datatype,
    ...
    PRIMARY KEY (column1)
);
```

* **Ví dụ tạo bảng SinhVien**:

```sql
CREATE TABLE SinhVien (
    MaSV INT PRIMARY KEY,
    HoTen VARCHAR(100),
    SDT VARCHAR(15),
    NamSinh YEAR
);
```

---

#### Chèn dữ liệu vào bảng

```sql
INSERT INTO SinhVien (MaSV, HoTen, SDT, NamSinh)
VALUES 
    (1, 'Nguyen Van A', '0123456789', 2001),
    (2, 'Tran Thi B', '0987654321', 2002),
    (3, 'Le Van C', '0112233445', 2000);
```

---

#### Các thao tác quản lý bảng

* **Xóa toàn bộ dữ liệu trong bảng (không xóa bảng)**:

```sql
TRUNCATE TABLE SinhVien;
```

* **Thêm cột vào bảng**:

```sql
ALTER TABLE SinhVien
ADD DiaChi VARCHAR(200);
```

* **Xóa cột khỏi bảng**:

```sql
ALTER TABLE SinhVien
DROP COLUMN DiaChi;
```

* **Sửa kiểu dữ liệu của cột**:

```sql
ALTER TABLE SinhVien
MODIFY SDT CHAR(10);
```

---

 Tóm lại:

* **Database** là “kho chứa” các bảng.
* **Table** là nơi tổ chức và lưu dữ liệu chi tiết.
* Trong MySQL, chúng ta có thể dễ dàng tạo, chỉnh sửa, thêm cột, chèn dữ liệu bằng các câu lệnh SQL.

---
## 4. Thao tác với dữ liệu bằng INSERT, UPDATE, DELETE

### 4.1 INSERT

Lệnh `INSERT INTO` dùng để thêm các bản ghi mới vào một bảng.

**Cú pháp:**

```sql
INSERT INTO ten_bang (cot1, cot2, cot3, ...)
VALUES 
    (giatri1, giatri2, giatri3, ...),
    (giatri1, giatri2, giatri3, ...),
    ...;
```

**Ví dụ:**

```sql
INSERT INTO SinhVien (MaSV, HoTen, NamSinh, MaLop)
VALUES
    (101, 'Nguyen Van A', 2001, 1),
    (102, 'Tran Thi B', 2002, 1),
    (103, 'Le Van C', 2000, 2);
```

---

### 4.2 UPDATE

Lệnh `UPDATE` dùng để thay đổi dữ liệu của một hoặc nhiều cột trong bảng.

**Cú pháp:**

```sql
UPDATE ten_bang
SET cot1 = giatri1, cot2 = giatri2, ...
WHERE dieu_kien;
```

**Ví dụ:**

```sql
UPDATE SinhVien
SET NamSinh = 2000
WHERE MaSV = 101;
```

---

### 4.3 DELETE

Lệnh `DELETE` dùng để xóa bản ghi trong bảng.

**Cú pháp:**

```sql
DELETE FROM ten_bang
WHERE dieu_kien;
```

**Ví dụ:**

```sql
DELETE FROM SinhVien
WHERE MaSV = 103;
```

## 5. Truy vấn dữ liệu

### 5.1 SELECT

* `SELECT` là câu lệnh được dùng nhiều nhất trong SQL, cho phép **lấy dữ liệu** từ một hoặc nhiều bảng.
* Kết quả trả về dưới dạng **bảng dữ liệu tạm thời** với các hàng và cột.

**Cú pháp cơ bản:**

```sql
SELECT cot1, cot2, ...
FROM ten_bang;
```

* `SELECT *` để lấy tất cả cột.

**Ví dụ:**

```sql
SELECT HoTen, NamSinh FROM SinhVien;
SELECT * FROM LopHoc;
```

---

### 5.2 Các câu điều kiện

#### 5.2.1 WHERE

* Dùng để lọc dữ liệu theo điều kiện.
* Điều kiện thường gồm **cột + toán tử + giá trị**.

**Cú pháp:**

```sql
SELECT cot1, cot2
FROM ten_bang
WHERE dieu_kien;
```

**Toán tử thường dùng:**

| Toán tử    | Ý nghĩa                     | Ví dụ                                 |
| ---------- | --------------------------- | ------------------------------------- |
| =          | Bằng                        | `WHERE NamSinh = 2001`                |
| >          | Lớn hơn                     | `WHERE NamSinh > 2000`                |
| <          | Nhỏ hơn                     | `WHERE NamSinh < 2000`                |
| >=         | Lớn hơn hoặc bằng           | `WHERE NamSinh >= 2001`               |
| <=         | Nhỏ hơn hoặc bằng           | `WHERE NamSinh <= 2001`               |
| <> hoặc != | Khác                        | `WHERE MaLop <> 1`                    |
| BETWEEN    | Trong một khoảng            | `WHERE NamSinh BETWEEN 2000 AND 2002` |
| LIKE       | So khớp chuỗi (%, \_)       | `WHERE HoTen LIKE 'Nguyen%'`          |
| IN         | Nằm trong danh sách giá trị | `WHERE MaSV IN (101, 103, 105)`       |

---

#### 5.2.2 LIMIT

* Dùng để giới hạn số lượng bản ghi trả về.
* Thường dùng để lấy **một phần dữ liệu** trong bảng lớn.

**Cú pháp:**

```sql
SELECT cot1, cot2
FROM ten_bang
LIMIT so_luong;
```

**Ví dụ:**

```sql
SELECT * FROM SinhVien
LIMIT 5;
```

Trả về 5 sinh viên đầu tiên trong bảng.

---

#### 5.2.3 ORDER BY

* Dùng để sắp xếp kết quả theo một hoặc nhiều cột.
* Có 2 chiều: **ASC** (tăng dần – mặc định) và **DESC** (giảm dần).

**Cú pháp:**

```sql
SELECT cot1, cot2
FROM ten_bang
ORDER BY cot1 ASC, cot2 DESC;
```

**Ví dụ:**

```sql
SELECT HoTen, NamSinh
FROM SinhVien
ORDER BY NamSinh DESC;
```

Danh sách sinh viên sẽ sắp xếp theo năm sinh từ mới đến cũ.

---

#### 5.2.4 GROUP BY

* Dùng để **nhóm dữ liệu** theo một hay nhiều cột.
* Kết hợp với **hàm tổng hợp**:

    * `COUNT()` – đếm số bản ghi
    * `SUM()` – tính tổng
    * `AVG()` – tính trung bình
    * `MAX()` – giá trị lớn nhất
    * `MIN()` – giá trị nhỏ nhất

**Cú pháp:**

```sql
SELECT cot, ham_tong_hop(cot2)
FROM ten_bang
GROUP BY cot;
```

**Ví dụ:** Đếm số sinh viên trong mỗi lớp

```sql
SELECT MaLop, COUNT(*) AS SoSV
FROM SinhVien
GROUP BY MaLop;
```

---

#### 5.2.5 HAVING

* Dùng để **lọc dữ liệu sau khi GROUP BY**.
* Nếu bỏ GROUP BY, `HAVING` hoạt động gần giống `WHERE`.

**Cú pháp:**

```sql
SELECT cot1, COUNT(*)
FROM ten_bang
GROUP BY cot1
HAVING COUNT(*) > gia_tri;
```

**Ví dụ:** Tìm các lớp có trên 5 sinh viên

```sql
SELECT MaLop, COUNT(*) AS SoSV
FROM SinhVien
GROUP BY MaLop
HAVING COUNT(*) > 5;
```

---

### 5.3 Các loại JOIN

JOIN dùng để kết hợp dữ liệu từ nhiều bảng dựa trên **cột chung**.

#### 5.3.1 INNER JOIN

* Trả về các bản ghi **khớp ở cả hai bảng**.

```sql
SELECT SinhVien.HoTen, LopHoc.TenLop
FROM SinhVien
INNER JOIN LopHoc ON SinhVien.MaLop = LopHoc.MaLop;
```

#### 5.3.2 LEFT JOIN

* Trả về **tất cả bản ghi bảng bên trái**, và bản ghi khớp từ bảng bên phải.
* Nếu không khớp, bên phải = NULL.

```sql
SELECT SinhVien.HoTen, LopHoc.TenLop
FROM SinhVien
LEFT JOIN LopHoc ON SinhVien.MaLop = LopHoc.MaLop;
```

#### 5.3.3 RIGHT JOIN

* Ngược lại với LEFT JOIN.
* Trả về **tất cả bản ghi bảng bên phải**, và bản ghi khớp từ bảng bên trái.
* Nếu không khớp, bên trái = NULL.

```sql
SELECT SinhVien.HoTen, LopHoc.TenLop
FROM SinhVien
RIGHT JOIN LopHoc ON SinhVien.MaLop = LopHoc.MaLop;
```

#### 5.3.4 FULL JOIN

* Trả về tất cả bản ghi ở cả hai bảng, dù có khớp hay không.
* MySQL không hỗ trợ trực tiếp, có thể giả lập bằng `UNION` của LEFT và RIGHT JOIN.

```sql
SELECT SinhVien.HoTen, LopHoc.TenLop
FROM SinhVien
LEFT JOIN LopHoc ON SinhVien.MaLop = LopHoc.MaLop
UNION
SELECT SinhVien.HoTen, LopHoc.TenLop
FROM SinhVien
RIGHT JOIN LopHoc ON SinhVien.MaLop = LopHoc.MaLop;
```

---

 `SELECT` kết hợp với các mệnh đề `WHERE, LIMIT, ORDER BY, GROUP BY, HAVING` và các loại `JOIN` cho phép bạn truy vấn dữ liệu một cách linh hoạt, từ đơn giản đến phức tạp, phù hợp cho báo cáo và phân tích.

---



