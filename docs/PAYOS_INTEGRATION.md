# PayOS Integration Setup

## App Configuration

### Android Deep Link Setup (Đã hoàn tất)

App đã hỗ trợ nhận callback từ PayOS thông qua deep link:

- **Scheme**: `customer`
- **Host**: `payment-status`
- **Format**: `customer://payment-status?orderCode=<orderCode>&status=<status>`

#### File cấu hình:

- `app/src/main/AndroidManifest.xml` - khai báo intent-filter
- `app/src/main/java/com/uzuu/customer/feature/MainActivity.kt` - xử lý deep link
- `app/src/main/java/com/uzuu/customer/core/constants/PaymentConstants.kt` - constants cấu hình

### Backend cần cấu hình ở PayOS Dashboard

#### 1. Return URL (thanh toán thành công)

```
https://<backend-public-domain>/api/v1/payments/redirect?orderCode=<orderCode>&status=success
```

Backend sẽ redirect URL này về app deep link:

```
customer://payment-status?orderCode=<orderCode>&status=success
```

#### 2. Cancel URL (người dùng hủy)

```
https://<backend-public-domain>/api/v1/payments/redirect?orderCode=<orderCode>&status=cancel
```

Backend sẽ redirect URL này về app deep link:

```
customer://payment-status?orderCode=<orderCode>&status=cancel
```

### Backend Endpoint Implementation

Backend cần tạo endpoint `/api/v1/payments/redirect` để:

1. Nhận `orderCode` và `status` từ PayOS redirect
2. Cập nhật trạng thái đơn hàng trong DB
3. Redirect browser quay lại app thông qua deep link:

```
Location: customer://payment-status?orderCode=<orderCode>&status=<status>
```

#### Ví dụ flow:

```
1. App mở PayOS URL:
   https://pay.payos.vn/web/abc123...

2. Người dùng thanh toán xong, PayOS redirect:
   https://<backend-public-domain>/api/v1/payments/redirect?orderCode=ORDER123&status=success

3. Backend xử lý và redirect về app:
   Location: customer://payment-status?orderCode=ORDER123&status=success

4. Android mở deep link trên device (nếu app được cài đặt)
   App nhận intent và hiển thị toast thông báo
```

### Query Parameters

| Parameter   | Giá trị             | Mô tả                 |
| ----------- | ------------------- | --------------------- |
| `orderCode` | string              | Mã đơn hàng từ PayOS  |
| `status`    | `success`, `cancel` | Trạng thái thanh toán |

### App Response Handler

Khi app nhận deep link:

- **Status = success/paid**: Hiển thị "Thanh toán thành công: #<orderCode>"
- **Status = cancel/canceled**: Hiển thị "Đã hủy thanh toán: #<orderCode>"
- **Khác**: Hiển thị "Đã quay lại ứng dụng"

### Nếu cần thay đổi Deep Link Scheme/Host

Tôi sửa 2 chỗ:

1. **PaymentConstants.kt**:

```kotlin
const val PAYMENT_DEEP_LINK_SCHEME = "customer"  // thay đổi nếu cần
const val PAYMENT_DEEP_LINK_HOST = "payment-status"  // thay đổi nếu cần
```

2. **AndroidManifest.xml**:

```xml
<data
    android:scheme="customer"
    android:host="payment-status" />
```

3. **Backend returnUrl/cancelUrl** - thay đổi scheme+host tương ứng.

### Notes

- ✅ App đã hỗ trợ deep link callback
- ✅ App đã chuẩn hóa payment method (VietQR → PAYOS)
- ⏳ Backend cần cấu hình returnUrl/cancelUrl ở PayOS dashboard
- ⏳ Backend cần implement `/api/v1/payments/redirect` endpoint
- ⚠️ Backend URL phải là public HTTPS (không localhost)
