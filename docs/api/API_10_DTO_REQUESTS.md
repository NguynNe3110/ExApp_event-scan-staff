# DTO Requests

## Identity

### AuthenticationRequest

- `username`: String, required
- `password`: String, required

### UserCreateRequest

- `username`: String, required, 3-50
- `password`: String, required, 6-100
- `email`: String, required, email
- `fullName`: String, required, max 100
- `phone`: String, 10-11 digits
- `address`: String, max 255
- `role`: String

### StaffCreateRequest

- `username`: String, required
- `email`: String, required, email
- `password`: String, required, min 8
- `fullName`: String, required

### UserUpdateRequest

- `password`: String, min 6
- `email`: String, email
- `fullName`: String, max 100
- `phone`: String, 10-11 digits
- `address`: String, max 255

### ForgotPasswordRequest

- `email`: String, required, email

### ResetPasswordRequest

- `otp`: String, required
- `newPassword`: String, required, min 8

### IntrospectRequest

- `token`: String

### RefreshRequest

- `token`: String

### LogoutRequest

- `token`: String

## Ordering / Cart

### CartItemRequest

- `ticketTypeId`: Long, required
- `quantity`: Integer, min 1

## Event

### CategoryRequest

- `name`: String, required, min 5
- `description`: String

### TicketTypeRequest

- `name`: String, required
- `description`: String
- `price`: BigDecimal, min 0
- `totalQuantity`: Integer, min 1
- `eventId`: Long

### EventRequest (multipart/form-data)

- `name`: String, required
- `categoryId`: Long, required
- `location`: String
- `startTime`: LocalDateTime, required
- `endTime`: LocalDateTime, required
- `saleStartDate`: LocalDateTime
- `saleEndDate`: LocalDateTime
- `description`: String
- `status`: EventStatus
- `files`: List<MultipartFile>
- `ticketTypes`: List<TicketTypeRequest>

## Marketing

### VoucherRequest

- `code`: String, required
- `discountType`: String, required
- `amount`: BigDecimal, required
- `maxDiscount`: BigDecimal
- `minOrderAmount`: BigDecimal
- `quantity`: Integer
- `startDate`: LocalDateTime, required
- `endDate`: LocalDateTime, required
- `eventId`: Long

## Blog

### BlogPostRequest

- `title`: String, required
- `summary`: String
- `content`: String
- `thumbnail`: String
- `eventIds`: Set<Long> (IDs of related events)
- `metaTitle`: String
- `metaDescription`: String
- `tagIds`: Set<Long>
