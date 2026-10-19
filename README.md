# Quản Lý Chi Tiêu Cá Nhân (Java Web - Servlet/JSP)

## Công nghệ sử dụng
- Java Servlet 4.0 + JSP + JSTL
- Maven
- MySQL 8
- Tomcat 9
- Chart.js (CDN) cho biểu đồ thống kê
- Gson (chuyển dữ liệu Java sang JSON cho Chart.js)

## Chức năng
1. Đăng ký / Đăng nhập / Đăng xuất (quản lý phiên bằng Session)
2. Dashboard: tổng thu, tổng chi, số dư tháng hiện tại + giao dịch gần đây
3. Quản lý danh mục thu/chi (thêm, xóa)
4. Quản lý giao dịch: thêm, **sửa (modal popup)**, xóa, lọc theo loại và danh mục
5. Thống kê: biểu đồ cột Chart.js thể hiện thu/chi theo từng ngày (30 ngày gần nhất)
6. Trang "Tôi": tổng quan tài chính toàn thời gian + thông tin cá nhân bổ sung lưu bằng `localStorage`

## Cách chạy trên IntelliJ IDEA

### 1. Tạo database
Mở MySQL Workbench / DBeaver / terminal, chạy file `database.sql` đi kèm:
```sql
source database.sql;
```
Tài khoản demo có sẵn: `demo` / `123456`

### 2. Mở project trong IntelliJ
- File → Open → chọn thư mục `QuanLyChiTieu` (nơi có `pom.xml`)
- Đợi Maven tự tải dependencies (cần Internet để tải từ Maven Central)

### 3. Chỉnh thông tin kết nối DB
Mở `src/main/java/com/quanlychitieu/dao/JDBCUtil.java`, sửa `USER`/`PASSWORD` cho khớp MySQL trên máy bạn (mặc định đang để `root`/`root`).

### 4. Cấu hình Tomcat 9 (Smart Tomcat plugin hoặc cấu hình Tomcat gốc)
- Nếu dùng **Smart Tomcat**: Run → Edit Configurations → + → Smart Tomcat
  - Tomcat Server: trỏ tới thư mục cài Tomcat 9 trên máy
  - Deployment Directory: `src/main/webapp`
  - Context Path: `/QuanLyChiTieu` (hoặc `/` tùy ý)
- Nếu dùng cấu hình Tomcat Server gốc của IntelliJ: chọn artifact `QuanLyChiTieu:war exploded`

### 5. Chạy và truy cập
`http://localhost:8080/QuanLyChiTieu/`

## Cấu trúc thư mục
```
QuanLyChiTieu/
├── pom.xml
├── database.sql
├── src/main/java/com/quanlychitieu/
│   ├── model/       (NguoiDung, DanhMuc, GiaoDich, ThongKe)
│   ├── dao/          (JDBCUtil, NguoiDungDAO, DanhMucDAO, GiaoDichDAO)
│   ├── filter/       (LoginFilter - chặn truy cập khi chưa đăng nhập)
│   └── servlet/      (các servlet xử lý từng chức năng)
└── src/main/webapp/
    ├── WEB-INF/web.xml
    ├── css/style.css
    ├── js/main.js
    ├── includes/sidebar.jsp
    └── *.jsp
```

## Ghi chú các lỗi thường gặp đã được xử lý sẵn trong code này
- CSS không load: dùng `${pageContext.request.contextPath}` cho mọi đường dẫn CSS/JS thay vì đường dẫn tuyệt đối cứng
- Layout sidebar/nội dung bị đẩy lệch: dùng flexbox `.layout { display:flex }` với `.main-content { flex:1 }`
- Lỗi HTTP 500 ở trang thống kê do thiếu getter: model `ThongKe` đã có đủ getter/setter public
- Thiếu `welcome-file-list` khiến truy cập context root không hiện gì: đã khai báo `index.jsp` làm welcome file
