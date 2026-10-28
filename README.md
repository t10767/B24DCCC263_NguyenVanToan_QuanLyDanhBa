# 📱 Ứng dụng Quản lý Danh bạ Liên hệ (Android)

> **Bài tập thực hành Lập trình Android**  
> **Sinh viên thực hiện:** Nguyễn Văn Toàn  
> **Mã sinh viên:** B24DCCC263  
> **Khoa:** Công nghệ Thông tin  

---

## 📖 Giới thiệu dự án
Ứng dụng Android giúp người dùng quản lý danh bạ liên hệ cá nhân với giao diện trực quan, hiện đại theo phong cách **Material 3**. Ứng dụng được xây dựng hoàn toàn bằng **Java và XML**, sử dụng `Intent` để chuyển màn hình và trao đổi dữ liệu, quản lý danh sách qua `ArrayList<Contact>`.

---

## 🌟 Các chức năng chính (4 Màn hình)

### 1. Màn hình Đăng nhập (`LoginActivity`)
- Tài khoản cố định:
  - **Tên đăng nhập:** `admin`
  - **Mật khẩu:** `123456`
- Kiểm tra tính hợp lệ dữ liệu (báo lỗi khi bỏ trống hoặc nhập sai).
- Đăng nhập thành công chuyển sang danh sách liên hệ và kết thúc màn hình đăng nhập.

### 2. Màn hình Danh sách liên hệ (`MainActivity`)
- **Khối thống kê:** Hiển thị tổng số liên hệ và số liên hệ yêu thích, cập nhật tự động khi thêm, sửa hoặc xóa liên hệ.
- **Danh sách liên hệ (`RecyclerView`):**
  - Avatar đại diện tròn mang chữ cái đầu của tên và màu sắc sinh động.
  - Họ tên (in đậm), số điện thoại.
  - Phân loại nhóm dạng nhãn màu sắc riêng biệt: *Bạn bè*, *Gia đình*, *Công việc*, *Khác*.
  - Dấu sao vàng (★) đánh dấu liên hệ yêu thích.
- Chạm vào một liên hệ để mở màn hình chi tiết.
- Nút **"+ Thêm liên hệ"** để mở màn hình tạo mới.

### 3. Màn hình Thêm liên hệ (`AddContactActivity`)
- Nhập họ và tên (bắt buộc).
- Nhập số điện thoại (`phone` input, kiểm tra đúng 10 số và bắt đầu bằng số 0: `^0[0-9]{9}$`).
- Kiểm tra chống trùng số điện thoại đã tồn tại trong danh bạ.
- Lựa chọn nhóm (`Spinner`): *Bạn bè*, *Gia đình*, *Công việc*, *Khác*.
- Chọn giới tính (`RadioGroup`): *Nam*, *Nữ*, *Khác*.
- Tùy chọn đánh dấu yêu thích (`CheckBox`).
- Nhập ghi chú nhiều dòng (`textMultiLine`).
- Nút **"Lưu liên hệ"** kiểm tra và trả dữ liệu về `MainActivity`.

### 4. Màn hình Chi tiết liên hệ (`ContactDetailActivity`)
- Hiển thị đầy đủ thông tin: Avatar tròn lớn, Họ tên, Số điện thoại (màu xanh Teal nổi bật), Nhóm, Giới tính, Ghi chú.
- CheckBox **"Yêu thích"**: Cho phép bật/tắt yêu thích trực tiếp; khi quay lại màn hình chính thì danh sách và thống kê được đồng bộ.
- **Nút Gọi điện:** Mở màn hình quay số điện thoại hệ thống qua `Intent.ACTION_DIAL`.
- **Nút Quay lại:** Đóng màn hình và cập nhật dữ liệu.
- **Nút Xóa liên hệ:** Hiển thị hộp thoại `AlertDialog` xác nhận trước khi xóa, cập nhật ngay vào danh sách và khối thống kê.

---

## 🛠️ Công nghệ sử dụng
- **Ngôn ngữ:** Java
- **Giao diện:** XML Layouts, Material Components (`MaterialCardView`, `AppCompatButton`)
- **Kiến trúc:** Multi-Activity, Activity Result API (`ActivityResultLauncher`)
- **Quản lý dữ liệu:** In-memory `ArrayList<Contact>`, `Serializable`
- **Gradle:** Gradle Wrapper 9.5, Android Gradle Plugin (AGP) 9.3.3
- **SDK Target:** Android SDK 35 (hỗ trợ từ Android 7.0 - API 24 trở lên)

---

## 🚀 Hướng dẫn cài đặt & chạy ứng dụng
1. Clone repository về máy:
   ```bash
   git clone https://github.com/t10767/B24DCCC263_NguyenVanToan_QuanLyDanhBa.git
   ```
2. Mở thư mục dự án bằng **Android Studio**.
3. Chờ Android Studio đồng bộ Gradle (`Sync Project with Gradle Files`).
4. Nhấn nút **Run** (biểu tượng ▶️) để chạy ứng dụng trên máy ảo hoặc thiết bị thật.
