# API Documentation

Tổng hợp các API của project `BE-event-mng-v2`.
Response tiêu chuẩn: `ApiResponse<T>` (nhiều endpoint trả về kiểu generic này).
Một số endpoint yêu cầu bearer token (OpenAPI `bearerAuth`) hoặc quyền role (`@PreAuthorize`).

---

## AuthenticationController (base: /auth)

- POST /auth/login
    - Mô tả: Đăng nhập
    - Request: `AuthenticationRequest`
    - Response: `AuthenticationResponse` (trong `ApiResponse`)

- POST /auth/register
    - Mô tả: Đăng ký tài khoản mới
    - Request: `UserCreateRequest`
    - Response: `String`

- GET /auth/verify?token={token}
    - Mô tả: Xác thực Email
    - Request: query `token`
    - Response: `String` (HTML: text/html)

- POST /auth/forgot-password
    - Mô tả: Yêu cầu mã OTP lấy lại mật khẩu
    - Request: `ForgotPasswordRequest`
    - Response: `String`

- POST /auth/reset-password
    - Mô tả: Đặt lại mật khẩu với mã OTP
    - Request: `ResetPasswordRequest`
    - Response: `String`

- POST /auth/introspect
    - Mô tả: Kiểm tra token
    - Request: `IntrospectRequest`
    - Response: `IntrospectResponse`

- POST /auth/refresh
    - Mô tả: Làm mới token
    - Request: `RefreshRequest`
    - Response: `AuthenticationResponse`

- POST /auth/logout
    - Mô tả: Đăng xuất
    - Request: `LogoutRequest`
    - Response: (Void)

---

## CartController (base: /cart)

- POST /cart/add
    - Mô tả: Thêm vé vào giỏ hàng
    - Request: `CartItemRequest`
    - Response: `CartResponse`

- GET /cart
    - Mô tả: Xem giỏ hàng của tôi
    - Response: `CartResponse`

- PUT /cart/items/{itemId}?quantity={quantity}
    - Mô tả: Cập nhật số lượng vé trong giỏ
    - Path: `itemId`
    - Query: `quantity`
    - Response: `CartResponse`

- DELETE /cart/items/{itemId}
    - Mô tả: Xóa vé khỏi giỏ hàng
    - Path: `itemId`
    - Response: `CartResponse`

- DELETE /cart/clear
    - Mô tả: Xóa toàn bộ giỏ hàng
    - Response: (Void)

### Ví dụ: Thêm vé vào giỏ hàng

- Endpoint: `POST /cart/add`
- Request body:

```json
{
    "ticketTypeId": 9007199254740991,
    "quantity": 1073741824
}
```

- Response example:

```json
{
    "code": 1073741824,
    "message": "string",
    "result": {
        "id": 9007199254740991,
        "items": [
            {
                "id": 9007199254740991,
                "ticketTypeId": 9007199254740991,
                "ticketTypeName": "string",
                "eventName": "string",
                "quantity": 1073741824,
                "unitPrice": 0,
                "subtotal": 0
            }
        ],
        "totalAmount": 0
    }
}
```

---

## CategoryController (base: /categories)

- POST /categories
    - Mô tả: Tạo danh mục mới (ADMIN)
    - Request: `CategoryRequest`
    - Response: `CategoryResponse`

- GET /categories
    - Mô tả: Lấy danh sách danh mục
    - Response: `List<CategoryResponse>`

- GET /categories/{id}
    - Mô tả: Lấy danh mục theo ID
    - Path: `id`
    - Response: `CategoryResponse`

- PUT /categories/{id}
    - Mô tả: Cập nhật danh mục (ADMIN)
    - Request: `CategoryRequest`
    - Response: `CategoryResponse`

- DELETE /categories/{id}
    - Mô tả: Xóa danh mục (ADMIN)
    - Response: (Void)

---

## EventController (base: /events) — yêu cầu `bearerAuth`

- POST /events (consumes: multipart/form-data)
    - Mô tả: Tạo sự kiện mới (ORGANIZER)
    - Request: `EventRequest` (form-data / `@ModelAttribute`)
    - Response: `EventResponse`

- GET /events?page={page}&size={size}
    - Mô tả: Lấy danh sách sự kiện đã đăng
    - Response: `Page<EventResponse>`

- GET /events/{id}
    - Mô tả: Xem thông tin chi tiết sự kiện
    - Path: `id`
    - Response: `EventResponse`

### Ví dụ: Lấy chi tiết sự kiện (phân trang trả về mẫu `Page` trong `result`)

- Endpoint: `GET /events/{id}`
- Parameters:
    - `id` (integer) path
- Response example (mẫu `ApiResponse` với `Page` trong `result`):

```json
{
    "code": 1073741824,
    "message": "string",
    "result": {
        "totalPages": 1073741824,
        "totalElements": 9007199254740991,
        "first": true,
        "last": true,
        "pageable": {
            "pageSize": 1073741824,
            "paged": true,
            "pageNumber": 1073741824,
            "offset": 9007199254740991,
            "sort": { "sorted": true, "empty": true, "unsorted": true },
            "unpaged": true
        },
        "size": 1073741824,
        "content": [
            {
                "id": 9007199254740991,
                "name": "string",
                "categoryName": "string",
                "organizerName": "string",
                "location": "string",
                "startTime": "2026-03-19T18:03:46.426Z",
                "endTime": "2026-03-19T18:03:46.426Z",
                "saleStartDate": "2026-03-19T18:03:46.426Z",
                "saleEndDate": "2026-03-19T18:03:46.426Z",
                "description": "string",
                "status": "PENDING",
                "imageUrls": ["string"],
                "ticketTypes": [
                    {
                        "id": 9007199254740991,
                        "name": "string",
                        "price": 0,
                        "totalQuantity": 1073741824,
                        "remainingQuantity": 1073741824,
                        "description": "string"
                    }
                ],
                "createdAt": "2026-03-19T18:03:46.426Z",
                "updatedAt": "2026-03-19T18:03:46.426Z"
            }
        ],
        "number": 1073741824,
        "sort": { "sorted": true, "empty": true, "unsorted": true },
        "numberOfElements": 1073741824,
        "empty": true
    }
}
```

- PUT /events/{id} (consumes: multipart/form-data)
    - Mô tả: Cập nhật sự kiện (Chủ sự kiện/ADMIN)
    - Request: `EventRequest` (form-data)
    - Response: `EventResponse`

- GET /events/admin/all?page={page}&size={size}&search={search}&status={status}
    - Mô tả: Lấy tất cả sự kiện (ADMIN)
    - Roles: ADMIN (`@PreAuthorize`)
    - Response: `Page<EventResponse>`

- PATCH /events/{id}/status?status={status}
    - Mô tả: Cập nhật trạng thái sự kiện (ADMIN)
    - Query: `status` (`EventStatus` enum)
    - Roles: ADMIN
    - Response: `EventResponse`

- GET /events/organizer/my-events?page={page}&size={size}
    - Mô tả: Lấy danh sách sự kiện cá nhân (ORGANIZER)
    - Roles: ORGANIZER
    - Response: `Page<EventResponse>`

- GET /events/organizer/stats
    - Mô tả: Lấy thống kê doanh thu (ORGANIZER)
    - Roles: ORGANIZER
    - Response: `OrganizerStatsResponse`

---

## OrderController (base: /orders)

- POST /orders/checkout?paymentMethod={paymentMethod}
    - Mô tả: Thanh toán toàn bộ giỏ hàng
    - Query: `paymentMethod` (`PaymentMethod` enum)
    - Response: `OrderResponse`

- POST /orders/checkout-selected
    - Mô tả: Thanh toán các mục được chọn trong giỏ hàng
    - Request: `List<Long>` (body: itemIds)
    - Query: `paymentMethod`
    - Response: `OrderResponse`

- GET /orders?page={page}&size={size}
    - Mô tả: Xem lịch sử đơn hàng của tôi
    - Response: `Page<OrderResponse>`

### Ví dụ: Thanh toán giỏ hàng

- Endpoint: `POST /orders/checkout?paymentMethod={paymentMethod}`
- Parameters:
    - `paymentMethod` (query) — `MOMO`, `VNPAY`, `BANKING`
- Response example:

```json
{
    "code": 1073741824,
    "message": "string",
    "result": {
        "id": 9007199254740991,
        "totalAmount": 0,
        "paymentMethod": "MOMO",
        "paymentStatus": "PENDING",
        "orderStatus": "PENDING",
        "orderDate": "2026-03-19T18:26:50.944Z"
    }
}
```

### Ví dụ: Thanh toán các mục được chọn

- Endpoint: `POST /orders/checkout-selected?paymentMethod={paymentMethod}`
- Request body example:

```json
[9007199254740991]
```

- Response example:

```json
{
    "code": 1073741824,
    "message": "string",
    "result": {
        "id": 9007199254740991,
        "organizerAmount": 0,
        "platformFeeRate": 0.1,
        "serviceFee": 0,
        "totalAmount": 0,
        "paymentMethod": "MOMO",
        "paymentStatus": "PENDING",
        "orderStatus": "PENDING",
        "orderDate": "2026-04-03T12:03:28.469Z"
    }
}
```

---

## StatisticsController

- GET /statistics-event/by-status/{quarter}/{year}
    - Mô tả: Tỉ lệ phân bổ trạng thái từng quý (Admin)
    - Path: `quarter`, `year`
    - Response: `EventStatusStatsResponse`

- GET /statistics-event/by-temporal/{dayOfWeek}
    - Mô tả: Thống kê số lượng sự kiện bắt đầu theo ngày trong tuần/giờ (Admin)
    - Path: `dayOfWeek` (1..7)
    - Response: `EventTemporalStatsResponse`

- GET /statistics-revenue/{id_organizer}
    - Mô tả: Thống kê doanh thu (chủ sự kiện)
    - Path: `id_organizer`
    - Response: `List<EventRevenueStatsOrganizerResponse>`

- GET /statistics-revenue/admin
    - Mô tả: Thống kê doanh thu (admin)
    - Response: `EventRevenueStatsAdminResponse`

---

## TicketController (base: /tickets)

- GET /tickets/my-tickets
    - Mô tả: Xem danh sách vé đã mua
    - Response: `List<TicketResponse>`

- POST /tickets/check-in?ticketCode={ticketCode}
    - Mô tả: Quét mã vé để check-in (Chỉ Ban tổ chức/Admin)
    - Query: `ticketCode`
    - Response: `TicketResponse`

### Ví dụ: Xem vé đã mua / Check-in

- `GET /tickets/my-tickets` Response example:

```json
{
    "code": 1073741824,
    "message": "string",
    "result": [
        {
            "id": 9007199254740991,
            "eventName": "string",
            "ticketTypeName": "string",
            "ticketCode": "string",
            "qrCode": "string",
            "status": "VALID",
            "usedAt": "2026-03-19T21:16:21.005Z"
        }
    ]
}
```

`POST /tickets/check-in?ticketCode={ticketCode}` Response example:

```json
{
    "code": 1073741824,
    "message": "string",
    "result": {
        "id": 9007199254740991,
        "eventName": "string",
        "ticketTypeName": "string",
        "ticketCode": "string",
        "qrCode": "string",
        "status": "VALID",
        "usedAt": "2026-03-20T10:04:38.841Z"
    }
}
```

---

## TicketTypeController (base: /ticket-types)

- POST /ticket-types
    - Mô tả: Tạo loại vé mới cho sự kiện (ORGANIZER/ADMIN)
    - Request: `TicketTypeRequest`
    - Response: `TicketTypeResponse`

- GET /ticket-types/event/{eventId}
    - Mô tả: Lấy danh sách loại vé theo sự kiện
    - Path: `eventId`
    - Response: `List<TicketTypeResponse>`

---

## UserController (base: /users) — yêu cầu `bearerAuth`

- GET /users/my-info
    - Mô tả: Lấy thông tin của chính mình
    - Response: `UserResponse`

- GET /users?page={page}&size={size}&search={search}
    - Mô tả: Lấy danh sách người dùng (ADMIN)
    - Response: `Page<UserResponse>`

- GET /users/{username}
    - Mô tả: Lấy người dùng theo Username
    - Path: `username`
    - Response: `UserResponse`

- PUT /users/{username}
    - Mô tả: Cập nhật người dùng
    - Request: `UserUpdateRequest`
    - Response: `UserResponse`

- DELETE /users/{username}
    - Mô tả: Xóa người dùng (vô hiệu hóa) (ADMIN)
    - Response: `String`

- PATCH /users/{username}/unlock
    - Mô tả: Mở khóa người dùng (ADMIN)
    - Response: `String`

### Ví dụ: Cập nhật người dùng / Lấy thông tin của chính mình

- `PUT /users/{username}` Request example:

```json
{
    "password": "string",
    "email": "string",
    "fullName": "string",
    "phone": "9103137245",
    "address": "string"
}
```

- Response example:

```json
{
    "code": 1073741824,
    "message": "string",
    "result": {
        "id": 9007199254740991,
        "username": "string",
        "email": "string",
        "fullName": "string",
        "phone": "string",
        "address": "string",
        "role": "ADMIN"
    }
}
```

`GET /users/my-info` Response example (tương tự):

```json
{
    "code": 1073741824,
    "message": "string",
    "result": {
        "id": 9007199254740991,
        "username": "string",
        "email": "string",
        "fullName": "string",
        "phone": "string",
        "address": "string",
        "role": "ADMIN"
    }
}
```

---

## Ghi chú

- Các kiểu request/response chi tiết nằm trong `src/main/java/com/sa/event_mng/dto/`.
- Nhiều endpoint trả về dữ liệu phân trang (`Page<T>`), hoặc danh sách (`List<T>`).
- Các endpoint có `@SecurityRequirement(name = "bearerAuth")` hoặc `@PreAuthorize` yêu cầu token/role.

(Generated automatically from controller sources.)

---

## DTO Schemas (request / response)

Below are the DTO fields extracted from `src/main/java/com/sa/event_mng/dto/`.

### Request DTOs

- `AuthenticationRequest`
    - `username`: String (required)
    - `password`: String (required)

- `UserCreateRequest`
    - `username`: String (required, 3-50)
    - `password`: String (required, 6-100)
    - `email`: String (required, email)
    - `fullName`: String (required, max 100)
    - `phone`: String (10-11 digits)
    - `address`: String (max 255)
    - `role`: String

- `UserUpdateRequest`
    - `password`: String (min 6)
    - `email`: String (email)
    - `fullName`: String (max 100)
    - `phone`: String (10-11 digits)
    - `address`: String (max 255)

- `CartItemRequest`
    - `ticketTypeId`: Long (required)
    - `quantity`: Integer (min 1)

- `CategoryRequest`
    - `name`: String (required, min 5)
    - `description`: String

- `EventRequest` (multipart/form-data)
    - `name`: String (required)
    - `categoryId`: Long (required)
    - `location`: String
    - `startTime`: LocalDateTime (required, ISO)
    - `endTime`: LocalDateTime (required, ISO)
    - `saleStartDate`: LocalDateTime (ISO)
    - `saleEndDate`: LocalDateTime (ISO)
    - `description`: String
    - `status`: `EventStatus` enum
    - `files`: List<MultipartFile>
    - `ticketTypes`: List<`TicketTypeRequest`>

- `TicketTypeRequest`
    - `name`: String (required)
    - `description`: String
    - `price`: BigDecimal (min 0)
    - `totalQuantity`: Integer (min 1)
    - `eventId`: Long

- `ForgotPasswordRequest`
    - `email`: String (required, email)

- `ResetPasswordRequest`
    - `email`: String (required, email)
    - `otp`: String (required)
    - `newPassword`: String (required, min 6)

- `IntrospectRequest`
    - `token`: String

- `RefreshRequest`
    - `token`: String

- `LogoutRequest`
    - `token`: String

### Response DTOs

- `ApiResponse<T>`
    - `code`: int (default 1000)
    - `message`: String
    - `result`: T

- `AuthenticationResponse`
    - `token`: String

- `UserResponse`
    - `id`: Long
    - `username`: String
    - `email`: String
    - `fullName`: String
    - `phone`: String
    - `address`: String
    - `enabled`: boolean
    - `role`: `Role` enum
    - `createdAt`: LocalDateTime (formatted)

- `CartItemResponse`
    - `id`: Long
    - `ticketTypeId`: Long
    - `ticketTypeName`: String
    - `eventName`: String
    - `quantity`: Integer
    - `unitPrice`: BigDecimal
    - `subtotal`: BigDecimal

- `CartResponse`
    - `id`: Long
    - `items`: List<`CartItemResponse`>
    - `totalAmount`: BigDecimal

- `CategoryResponse`
    - `id`: Long
    - `name`: String
    - `description`: String
    - `createdAt`: LocalDateTime
    - `updatedAt`: LocalDateTime

- `EventResponse`
    - `id`: Long
    - `name`: String
    - `categoryName`: String
    - `organizerName`: String
    - `location`: String
    - `startTime`: LocalDateTime
    - `endTime`: LocalDateTime
    - `saleStartDate`: LocalDateTime
    - `saleEndDate`: LocalDateTime
    - `description`: String
    - `status`: `EventStatus` enum
    - `imageUrls`: List<String>
    - `ticketTypes`: List<`TicketTypeResponse`>
    - `createdAt`: LocalDateTime
    - `updatedAt`: LocalDateTime

- `TicketTypeResponse`
    - `id`: Long
    - `name`: String
    - `price`: BigDecimal
    - `totalQuantity`: Integer
    - `remainingQuantity`: Integer
    - `description`: String

- `TicketResponse`
    - `id`: Long
    - `eventName`: String
    - `ticketTypeName`: String
    - `ticketCode`: String
    - `qrCode`: String
    - `status`: `TicketStatus` enum
    - `usedAt`: LocalDateTime

- `OrderResponse`
    - `id`: Long
    - `organizerAmount`: BigDecimal
    - `platformFeeRate`: Float
    - `serviceFee`: BigDecimal
    - `totalAmount`: BigDecimal
    - `paymentMethod`: `PaymentMethod` enum
    - `paymentStatus`: `PaymentStatus` enum
    - `orderStatus`: `OrderStatus` enum
    - `orderDate`: LocalDateTime

- `EventRevenueStatsOrganizerResponse`
    - `eventName`: String
    - `totalRevenue`: BigDecimal
    - `ticketsSold`: Integer
    - `percentageOfTicketsSold`: Double

- `EventRevenueStatsAdminResponse`
    - `totalRevenue`: BigDecimal
    - `monthlyRevenues`: List<`MonthlyRevenueResponse`>

- `MonthlyRevenueResponse`
    - `year`: int
    - `month`: int
    - `revenue`: BigDecimal

- `EventStatusStatsResponse`
    - `quarter`: Long
    - `year`: Long
    - `total`: Long
    - `eventStatusStatsDetail`: List<{ `status`: String, `percentage`: Double, `countEvents`: Long }>

- `EventTemporalStatsResponse`
    - `day`: String
    - `eventTemporalStatsDetail`: List<{ `hourOfDay`: Integer, `countEvents`: Long, `totalTickets`: Long, `ticketsSold`: Long, `percentageOfTicketsSold`: Double }>

- `IntrospectResponse`
    - `valid`: boolean

- `OrganizerStatsResponse`
    - `totalEvents`: long
    - `totalTicketsSold`: long
    - `totalRevenue`: double
    - `eventStats`: List<{ `eventId`, `eventName`, `totalTickets`, `ticketsSold`, `revenue`, `sellThroughRate`, `status`, `imageUrl` }>
    - `monthlyRevenues`: List<{ `year`, `month`, `revenue` }>
