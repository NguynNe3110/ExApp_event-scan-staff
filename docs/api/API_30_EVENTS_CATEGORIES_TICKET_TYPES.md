# Events, Categories, Ticket Types APIs

## EventController (base: /events, bearerAuth)

- POST /events (multipart/form-data)
    - Request: EventRequest (@ModelAttribute)
    - Response: ApiResponse<EventResponse>

- GET /events/search?page={page}&size={size}
    - Query filters: keyword/search, categoryId, province/provinceCode, minPrice, maxPrice, startDate, endDate
    - Response: ApiResponse<Page<EventResponse>>

    - Lấy sự kiện theo danh mục: truyền `categoryId={id}` vào `/events/search`
    - Ví dụ: `/events/search?page=1&size=10&categoryId=3`

- GET /events/{id}
    - Response: ApiResponse<EventResponse>

- PUT /events/{id} (multipart/form-data)
    - Request: EventRequest
    - Response: ApiResponse<EventResponse>

### Admin

- GET /events/admin/all?page={page}&size={size}&search={search}&status={status}
    - Response: ApiResponse<Page<EventResponse>>

- PATCH /events/{id}/status?status={status}
    - Response: ApiResponse<EventResponse>

### Organizer

- GET /events/organizer/my-events?page={page}&size={size}
    - Response: ApiResponse<Page<EventResponse>>

Note:

- `/events/{id}` expects a numeric `id` (Long).
- Use `/events/search` with `categoryId` to filter events by category.
- Blog posts are available in `/blog/*` endpoints (see [Statistics & Blog APIs](API_50_STATISTICS_BLOG.md)).

Examples:

- Search events: `/events/search?page=1&size=10&keyword=music`
- Filter by category: `/events/search?page=1&size=10&categoryId=3`
- Get event by id: `/events/123` (where `123` is numeric event id)
- Get blog post by slug: `/blog/posts/my-event-announcement`

## CategoryController (base: /categories)

- POST /categories
    - Request: CategoryRequest
    - Response: ApiResponse<CategoryResponse>

- GET /categories
    - Response: ApiResponse<List<CategoryResponse>>

- GET /categories/{id}
    - Response: ApiResponse<CategoryResponse>

- PUT /categories/{id}
    - Request: CategoryRequest
    - Response: ApiResponse<CategoryResponse>

- DELETE /categories/{id}
    - Response: ApiResponse<Void>

## TicketTypeController (base: /ticket-types)

- POST /ticket-types
    - Request: TicketTypeRequest
    - Response: ApiResponse<TicketTypeResponse>

- GET /ticket-types/event/{eventId}
    - Response: ApiResponse<List<TicketTypeResponse>>
