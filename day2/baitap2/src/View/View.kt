package View

import Controller.add
import Controller.delete
import Controller.edit
import Controller.view

fun showMenu() {
    println(
        """
        === QUẢN LÝ DANH BẠ ===
        1. Xem danh sách danh bạ
        2. Thêm danh bạ
        3. Sửa danh bạ
        4. Xoá danh bạ
        5. Thoát
        -----------------------
        Nhập lựa chọn:
        """.trimIndent()
    )
}

fun startView() {
    while (true) {
        showMenu()
        when (readlnOrNull()?.trim()) {
            "1" -> view()
            "2" -> add()
            "3" -> edit()
            "4" -> delete()
            "5" -> {
                println("Đã thoát chương trình.")
                break
            }
            else -> println("Lựa chọn không hợp lệ. Vui lòng thử lại.")
        }
    }
}
