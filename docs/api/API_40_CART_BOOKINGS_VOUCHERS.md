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

## Example: add item to cart

```json
{
    "ticketTypeId": 9007199254740991,
    "quantity": 2
}
```
