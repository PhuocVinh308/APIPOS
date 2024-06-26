# API Hệ Thống Quản Lý Bán Hàng Quán Cà Phê

## Mô Tả

API này cung cấp các endpoint để quản lí bán hàng trong quán cà phê, bao gồm quản lí sản phẩm, đơn hàng, sản phẩm trong hoá đơn, và các hoạt động liên quan.

## Cách Sử Dụng

### Cài Đặt

1. Clone repository từ GitHub:

```
git clone https://github.com/PhuocVinh308/APIPOS.git
```

2. Cài đặt:

```
Intelji: https://www.jetbrains.com/idea/download
MySQL Workbench https://www.mysql.com/downloads
```

3. Cấu hình:
```
Spring boot 4.0
```
```
JDK: ver 17
```
```
Khởi tạo database: webnl
```
```
create database webnl
```
#### CHÚ Ý: PORT 8080 có đang sử dụng không? Cấu hình tài khoản database tại application.properties. Run build.gradle trước khi chạy dự án


### API
#### Sản Phẩm
- `GET /products`: Lấy danh sách sản phẩm.
- `GET /products/{id}`: Lấy thông tin chi tiết của một sản phẩm theo ID.
- `POST /products`: Tạo một sản phẩm mới.
- `PUT /products/{id}`: Cập nhật thông tin của một sản phẩm.
- `DELETE /products/{id}`: Xóa một sản phẩm.
#### Đơn Hàng
- `GET /orders`: Lấy danh sách đơn hàng.
- `GET /orders/{id}`: Lấy thông tin của một đơn hàng theo ID.
- `POST /orders`: Tạo một đơn hàng mới.
- `PUT /orders/{id}`: Cập nhật thông tin của một đơn hàng.
- `DELETE /orders/{id}`: Xóa một đơn hàng.
#### Thống kê và báo cáo 
- `GET /api/orders/max`: Lấy ID đơn hàng tối đa. Để thêm order-items
- `GET /api/orders/xuatExcel`: Xuất chi tiết đơn hàng ra tệp Excel.
- `GET /api/orders/doanhthungay`: Lấy tổng doanh thu trong ngày.
- `GET /api/orders/doanhthuthang`: Lấy tổng doanh thu cho mỗi tháng.
- `GET /api/orders/chitiethoadon`: Lấy thông tin chi tiết về các đơn hàng.
- GET /api/orders/thucuongyeuthich`: Lấy danh sách đồ uống phổ biến dựa trên lịch sử đơn hàng.
#### Thông tin sản phẩm trong hoá đơn
- `GET /order-items`: Lấy danh sách sản phẩm trong hoá đơn.
- `GET /order-items/{id}`: Lấy thông tin chi tiết của một sản phẩm trong hoá đơn theo ID.
- `POST /order-items`: Tạo một sản phẩm trong hoá đơn mới.
- `PUT /order-items/{id}`: Cập nhật thông tin của một sản phẩm trong hoá đơn.
- `DELETE /order-items/{id}`: Xóa một sản phẩm trong hoá đơn.

#### Bàn
- `GET /api/table`: Lấy tất cả các bàn.
- `GET /api/table/{tableId}`: Lấy một bàn cụ thể bằng ID.
- `GET /api/table/status`: Lấy một bàn bằng trạng thái của nó.
- `POST /api/table`: Tạo một bàn mới.
- `DELETE /api/table/{tableId}`: Xóa một bàn bằng ID.
- `PUT /api/table/{tableId}`: Cập nhật một bàn bằng ID.
#### Khách hàng 
- `GET /api/customers`: Lấy tất cả các khách hàng.
- `POST /api/customers`: Tạo một khách hàng mới.
- `PUT /api/customers/{id}`: Cập nhật thông tin của một khách hàng bằng ID.
- `DELETE /api/customers/{id}`: Xóa một khách hàng bằng ID.

## Bảo Mật

- Tất cả các điểm kết nối đều yêu cầu xác thực, với các cấp độ ủy quyền khác nhau:
  - `ROLE_USER`: Người dùng có vai trò này có thể truy cập tất cả các điểm cuối.
  - `ROLE_ADMIN`: Admin có vai trò này có thể truy cập tất cả các điểm cuối và thực hiện các hành động quản trị.

## Cách Sử Dụng

- Để lấy tất cả các khách hàng, thực hiện yêu cầu GET đến `/api/customers`.
- Để tạo một khách hàng mới, thực hiện yêu cầu POST đến `/api/customers` với một dữ liệu JSON chứa thông tin của khách hàng.
- Để cập nhật thông tin của một khách hàng bằng ID, thực hiện yêu cầu PUT đến `/api/customers/{id}` với một dữ liệu JSON chứa thông tin cập nhật của khách hàng.
- Để xóa một khách hàng bằng ID, thực hiện yêu cầu DELETE đến `/api/customers/{id}`.

## Yêu Cầu Tiên Quyết

- Đảm bảo bạn có quyền truy cập vào ứng dụng với các vai trò phù hợp (ROLE_USER, ROLE_ADMIN)
### Đóng Góp

Nếu bạn muốn đóng góp vào API hoặc báo cáo vấn đề, vui lòng tạo pull request hoặc issue trên GitHub của dự án.

### Tác Giả

NGUYỄN HỒ PHƯỚC VINH - B2003936


