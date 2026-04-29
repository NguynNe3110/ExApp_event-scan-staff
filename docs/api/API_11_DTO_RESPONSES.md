# DTO Responses

## Wrapper

### ApiResponse<T>

- `code`: int, default 1000
- `message`: String
- `result`: T

## Identity

### AuthenticationResponse

- `token`: String

### IntrospectResponse

- `valid`: boolean

### UserResponse

- `id`: Long
- `username`: String
- `email`: String
- `fullName`: String
- `phone`: String
- `address`: String
- `enabled`: boolean
- `roles`: Set<Role>
- `createdAt`: LocalDateTime

### OrganizerResponse

- `id`: Long
- `fullName`: String
- `email`: String

## Cart / Order / Ticket

### CartItemResponse

- `id`: Long
- `ticketTypeId`: Long
- `ticketTypeName`: String
- `eventName`: String
- `quantity`: Integer
- `unitPrice`: BigDecimal
- `subtotal`: BigDecimal

### CartResponse

- `id`: Long
- `items`: List<CartItemResponse>
- `totalAmount`: BigDecimal

### OrderResponse

- `id`: Long
- `organizerAmount`: BigDecimal
- `platformFeeRate`: Float
- `serviceFee`: BigDecimal
- `totalAmount`: BigDecimal
- `paymentMethod`: PaymentMethod
- `paymentStatus`: PaymentStatus
- `orderStatus`: OrderStatus
- `orderDate`: LocalDateTime

### TicketResponse

- `id`: Long
- `eventName`: String
- `ticketTypeName`: String
- `ticketCode`: String
- `qrCode`: String
- `status`: TicketStatus
- `usedAt`: LocalDateTime

## Event

### CategoryResponse

- `id`: Long
- `name`: String
- `description`: String

### TicketTypeResponse

- `id`: Long
- `name`: String
- `price`: BigDecimal
- `totalQuantity`: Integer
- `remainingQuantity`: Integer
- `description`: String

### EventResponse

- `id`: Long
- `name`: String
- `category`: CategoryResponse
- `organizer`: OrganizerResponse
- `location`: String
- `province`: String
- `provinceName`: String
- `address`: String
- `startTime`: LocalDateTime
- `endTime`: LocalDateTime
- `saleStartDate`: LocalDateTime
- `saleEndDate`: LocalDateTime
- `description`: String
- `status`: EventStatus
- `imageUrls`: List<String>
- `ticketTypes`: List<TicketTypeResponse>
- `createdAt`: LocalDateTime
- `updatedAt`: LocalDateTime

### BlogEventResponse

- `id`: Long
- `name`: String
- `location`: String
- `province`: String
- `startTime`: LocalDateTime
- `endTime`: LocalDateTime
- `saleStartDate`: LocalDateTime
- `saleEndDate`: LocalDateTime
- `descriptionStatus`: String
- `imageUrl`: String

## Statistics

### EventRevenueStatsOrganizerResponse

- `eventName`: String
- `totalRevenue`: BigDecimal
- `ticketsSold`: Integer
- `percentageOfTicketsSold`: Double

### EventRevenueStatsAdminResponse

- `totalRevenue`: BigDecimal
- `monthlyRevenues`: List<MonthlyRevenueResponse>

### MonthlyRevenueResponse

- `year`: int
- `month`: int
- `revenue`: BigDecimal

### EventStatusStatsResponse

- `quarter`: Long
- `year`: Long
- `total`: Long
- `eventStatusStatsDetail`: List<EventStatusStatsDetail>

### EventTemporalStatsResponse

- `day`: String
- `eventTemporalStatsDetail`: List<EventTemporalStatsDetail>

### OrganizerStatsResponse

- `totalEvents`: long
- `totalTicketsSold`: long
- `totalRevenue`: double
- `eventStats`: List<EventStat>
- `monthlyRevenues`: List<MonthlyRevenue>

### Map<String, Long> (users/admin/stats)

- `customers`: Long
- `organizers`: Long
- `total`: Long

## Marketing

### VoucherResponse

- `id`: Long
- `code`: String
- `discountType`: String
- `amount`: BigDecimal
- `maxDiscount`: BigDecimal
- `minOrderAmount`: BigDecimal
- `quantity`: Integer
- `startDate`: LocalDateTime
- `endDate`: LocalDateTime
- `eventId`: Long
- `eventName`: String
- `creatorName`: String
