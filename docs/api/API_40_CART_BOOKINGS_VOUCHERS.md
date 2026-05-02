# Cart, Bookings, Vouchers APIs

## CartController (base: /cart)

- POST /cart/add
    - Request: CartItemRequest
    - Response: ApiResponse<CartResponse>

- GET /cart
    - Response: ApiResponse<CartResponse>

- PUT /cart/items/{itemId}?quantity={quantity}
    - Query: quantity
    - Response: ApiResponse<CartResponse>

- DELETE /cart/items/{itemId}
    - Response: ApiResponse<CartResponse>

- DELETE /cart/clear
    - Response: ApiResponse<Void>

## OrderController (base: /bookings)

- POST /bookings/checkout?paymentMethod={paymentMethod}&voucherCode={voucherCode}
    - Query `paymentMethod`: MOMO | VNPAY | BANKING
    - Query `voucherCode`: optional
    - Response: ApiResponse<OrderResponse>

- POST /bookings/checkout-selected?paymentMethod={paymentMethod}&voucherCode={voucherCode}
    - Body: List<Long> itemIds
    - Query `paymentMethod`: MOMO | VNPAY | BANKING
    - Query `voucherCode`: optional
    - Response: ApiResponse<OrderResponse>

- GET /bookings?page={page}&size={size}
    - Response: ApiResponse<Page<OrderResponse>>

## VoucherController (base: /vouchers)

- POST /vouchers
    - Request: VoucherRequest
    - Response: ApiResponse<VoucherResponse>

- GET /vouchers?page={page}&size={size}
    - Response: ApiResponse<Page<VoucherResponse>>

- POST /vouchers/validate
    - Request: VoucherValidationRequest - Response: ApiResponse<Double>

- GET /vouchers/event/{eventId}
    - Response: ApiResponse<List<VoucherResponse>>

- DELETE /vouchers/{id} - Response: ApiResponse<Void>

### VoucherRequest

```json
{
    "code": "SUMMER2026",
    "discountType": "PERCENTAGE",
    "amount": 20,
    "maxDiscount": 50000,
    "minOrderAmount": 200000,
    "quantity": 100,
    "startDate": "2026-05-02T10:00:00",
    "endDate": "2026-05-10T23:59:59",
    "eventId": 123
}
```

### VoucherValidationRequest

```json
{
    "code": "SUMMER2026",
    "eventAmounts": {
        "123": 250000,
        "124": 150000
    }
}
```

### VoucherResponse

```json
{
    "id": 1,
    "code": "SUMMER2026",
    "discountType": "PERCENTAGE",
    "amount": 20,
    "maxDiscount": 50000,
    "minOrderAmount": 200000,
    "quantity": 100,
    "startDate": "2026-05-02T10:00:00",
    "endDate": "2026-05-10T23:59:59",
    "eventId": 123,
    "eventName": "Summer Music Fest",
    "creatorName": "Nguyen Van A"
}
```

### ApiResponse

Tất cả API voucher hiện trả về wrapper chung:

```json
{
    "code": 1000,
    "message": null,
    "result": {}
}
```

### Pagination note

`GET /vouchers` dùng chỉ số trang bắt đầu từ `1`, ví dụ `page=1&size=10`. Nếu frontend gửi `page=0` hoặc số âm, backend sẽ tự chặn về trang hợp lệ.

### Gợi ý luồng chọn voucher kiểu "tick chọn"

- Dùng `GET /vouchers/event/{eventId}` để lấy danh sách voucher đang áp dụng cho sự kiện.
- Hiển thị danh sách voucher dưới dạng radio/checkbox/card.
- Khi người dùng chọn 1 mã, lưu `code` của voucher đó.
- Khi thanh toán, truyền `voucherCode` vào `POST /bookings/checkout` hoặc `POST /bookings/checkout-selected`.
- Nếu muốn kiểm tra mức giảm trước khi thanh toán, gọi `POST /vouchers/validate`.

### Checkout APIs

- POST /bookings/checkout?paymentMethod={paymentMethod}&voucherCode={voucherCode} - Query `paymentMethod`: MOMO | VNPAY | BANKING - Query `voucherCode`: optional, truyền code đã chọn từ danh sách voucher - Response: ApiResponse<OrderResponse>

- POST /bookings/checkout-selected?paymentMethod={paymentMethod}&voucherCode={voucherCode} - Body: List<Long> itemIds - Query `paymentMethod`: MOMO | VNPAY | BANKING - Query `voucherCode`: optional, truyền code đã chọn từ danh sách voucher - Response: ApiResponse<OrderResponse>

## Example: add item to cart

```json
{
    "ticketTypeId": 9007199254740991,
    "quantity": 2
}
```
