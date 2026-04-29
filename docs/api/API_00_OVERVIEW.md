# API Overview

Tai lieu API da duoc tach theo domain de de tra cuu. Moi file deu ghi ro request/response cho tung endpoint.

## Danh sach file

- [DTO Requests](API_10_DTO_REQUESTS.md)
- [DTO Responses](API_11_DTO_RESPONSES.md)
- [Auth & Users APIs](API_20_AUTH_USERS.md)
- [Events, Categories, Ticket Types APIs](API_30_EVENTS_CATEGORIES_TICKET_TYPES.md)
- [Tickets APIs](API_31_TICKETS_CHECKIN.md)
- [Cart, Bookings, Vouchers APIs](API_40_CART_BOOKINGS_VOUCHERS.md)
- [Statistics & Blog APIs](API_50_STATISTICS_BLOG.md)

## Quy uoc chung

- Phan lon endpoint tra ve `ApiResponse<T>`:
    - `code`: int (mac dinh 1000)
    - `message`: String
    - `result`: T
- Endpoint co `bearerAuth` hoac `@PreAuthorize` thi can token/quyen tuong ung.
- Query paging thong nhat: `page` bat dau tu 1, `size` > 0.

## Tu dien nhanh

- Cart request dung ten class `CartItemRequest` (khong phai `CartRequest`).
- Order route hien tai: `/bookings/*` (khong phai `/orders/*`).
- Event search co 2 duong dan: `/events` va `/events/search` (alias).
