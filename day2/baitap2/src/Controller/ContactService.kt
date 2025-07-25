package Controller

import Data.Contact

// Danh sách liên hệ toàn cục, dùng để lưu trữ các đối tượng Contact
val contactList = mutableListOf<Contact>()

// Hàm thêm một liên hệ mới vào danh bạ
fun add() {
    print("Nhập tên: ")
    val name = readln().trim()  // Đọc và loại bỏ khoảng trắng thừa

    print("Nhập SĐT: ")
    val phoneNumber = readln().trim()

    // Tạo đối tượng Contact mới và thêm vào danh sách
    contactList.add(Contact(name, phoneNumber))
    println("✅ Đã thêm danh bạ thành công.")
}

// Hàm hiển thị danh sách danh bạ
fun view() {
    if (contactList.isEmpty()) {
        println("📭 Danh bạ trống.")
    } else {
        println("📒 Danh sách danh bạ:")
        // Duyệt danh sách và in ra từng liên hệ
        contactList.forEachIndexed { index, contact ->
            println("${index + 1}. ${contact.name} | ${contact.phoneNumber}")
        }
    }
}

// Hàm chỉnh sửa một liên hệ trong danh bạ
fun edit() {
    view()  // Hiển thị danh bạ hiện tại
    if (contactList.isEmpty()) return

    print("Nhập số thứ tự liên hệ muốn sửa: ")
    val index = readln().toIntOrNull()?.minus(1)  // Trừ 1 vì danh sách đánh chỉ số từ 0

    // Kiểm tra chỉ số có hợp lệ không
    if (index == null || index !in contactList.indices) {
        println("❌ Số thứ tự không hợp lệ.")
        return
    }

    // Lấy liên hệ cần sửa
    val contact = contactList[index]

    // Nhập thông tin mới
    print("Nhập tên mới: ")
    val newName = readln().trim()

    print("Nhập SĐT mới: ")
    val newPhoneNumber = readln().trim()

    // Cập nhật thông tin liên hệ
    contact.name = newName
    contact.phoneNumber = newPhoneNumber

    println("✅ Danh bạ đã được cập nhật.")
    view()  // Hiển thị danh bạ sau khi sửa
}

// Hàm xoá một liên hệ trong danh bạ
fun delete() {
    view()  // Hiển thị danh sách trước khi xoá
    if (contactList.isEmpty()) return

    print("Nhập số thứ tự liên hệ muốn xoá: ")
    val index = readln().toIntOrNull()?.minus(1)  // Trừ 1 vì chỉ số bắt đầu từ 0

    // Kiểm tra tính hợp lệ của chỉ số
    if (index == null || index !in contactList.indices) {
        println("❌ Số thứ tự không hợp lệ.")
        return
    }

    // Xoá và hiển thị thông tin liên hệ vừa xoá
    val removed = contactList.removeAt(index)
    println("🗑️ Đã xoá liên hệ: ${removed.name}")
    view()  // Hiển thị danh bạ sau khi xoá
}
