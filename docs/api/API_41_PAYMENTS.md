# Payment APIs

Tai lieu nay mo ta luong thanh toan hien tai de team app co the tich hop dung cach.

## Tong quan luong thanh toan

### PAYOS flow

1. Frontend goi API checkout trong nhom bookings voi `paymentMethod = PAYOS`.
2. Backend tao don hang o trang thai `PENDING` va tra ve `paymentUrl`.
3. Frontend redirect nguoi dung sang `paymentUrl` cua PayOS.
4. PayOS goi webhook ve backend sau khi nguoi dung thanh toan xong.
5. Backend cap nhat don sang `PAID` va `CONFIRMED`.

## Checkout co tra paymentUrl

- POST /bookings/checkout?paymentMethod={paymentMethod}&voucherCode={voucherCode}
    - Query `paymentMethod`: MOMO | VNPAY | PAYOS
    - Query `voucherCode`: optional
    - Response: ApiResponse<OrderResponse>
    - Ghi chu: chi khi `paymentMethod = PAYOS` thi `result.paymentUrl` moi co gia tri.
        - PAYOS: paymentUrl la URL cua trang thanh toan PayOS

- POST /bookings/checkout-selected?paymentMethod={paymentMethod}&voucherCode={voucherCode}
    - Body: List<Long> itemIds
    - Query `paymentMethod`: MOMO | VNPAY | PAYOS
    - Query `voucherCode`: optional
    - Response: ApiResponse<OrderResponse>
    - Ghi chu: chi khi `paymentMethod = PAYOS` thi `result.paymentUrl` moi co gia tri.

## PayOS webhook

- POST /api/v1/payments/payos-webhook
    - Auth: public
    - Body: webhook payload tu PayOS
    - Response: `200 OK`
    - Ghi chu: endpoint nay duoc backend dung de nhan callback thong bao giao dich thanh cong.

## Example: OrderResponse khi thanh toan PAYOS

```json
{
    "code": 1000,
    "message": null,
    "result": {
        "id": "0192837465",
        "organizerAmount": 187500,
        "platformFeeRate": 0.25,
        "serviceFee": 62500,
        "totalAmount": 250000,
        "paymentMethod": "PAYOS",
        "paymentStatus": "PENDING",
        "orderStatus": "PENDING",
        "orderDate": "2026-05-02T10:00:00",
        "paymentUrl": "https://pay.payos.vn/web/abc123"
    }
}
```

## Gia tri can luu y cho frontend

- `paymentUrl`:
    - PAYOS: URL de mo trang thanh toan PayOS.
- `paymentStatus`: luc moi tao don la `PENDING`.
- `orderStatus`: luc moi tao don la `PENDING`.
- Sau webhook thanh cong, backend se cap nhat don sang `PAID` va `CONFIRMED`.
