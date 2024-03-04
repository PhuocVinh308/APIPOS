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
#### CHÚ Ý: PORT 8080 có đang sử dụng không?


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

#### Thông tin sản phẩm trong hoá đơn
- `GET /order-items`: Lấy danh sách sản phẩm trong hoá đơn.
- `GET /order-items/{id}`: Lấy thông tin chi tiết của một sản phẩm trong hoá đơn theo ID.
- `POST /order-items`: Tạo một sản phẩm trong hoá đơn mới.
- `PUT /order-items/{id}`: Cập nhật thông tin của một sản phẩm trong hoá đơn.
- `DELETE /order-items/{id}`: Xóa một sản phẩm trong hoá đơn.

#### Bàn

- `GET /table`: Lấy thông tin bàn.
- 
### Đóng Góp

Nếu bạn muốn đóng góp vào API hoặc báo cáo vấn đề, vui lòng tạo pull request hoặc issue trên GitHub của dự án.

### Tác Giả

NGUYỄN HỒ PHƯỚC VINH - B2003936


