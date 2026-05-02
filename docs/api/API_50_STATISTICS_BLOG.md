# Statistics & Blog APIs

## StatisticsController

- GET /statistics-event/by-status/{quarter}/{year}
    - Response: ApiResponse<EventStatusStatsResponse>

- GET /statistics-event/by-temporal/{dayOfWeek}
    - Response: ApiResponse<EventTemporalStatsResponse>

- GET /statistics-revenue/{id_organizer}
    - Response: ApiResponse<List<EventRevenueStatsOrganizerResponse>>

- GET /statistics-revenue/admin
    - Response: ApiResponse<EventRevenueStatsAdminResponse>

## BlogController (base: /blog)

- GET /blog/posts?page={page}&size={size}
    - Query params: `page` (default 0), `size` (default 10)
    - Response: ApiResponse<Page<BlogPostResponse>>

- GET /blog/posts/{slug}
    - Response: ApiResponse<BlogPostResponse>

- GET /blog/tags
    - Response: ApiResponse<List<BlogTagResponse>>

- GET /blog/organizer/posts?page={page}&size={size}
    - Response: ApiResponse<Page<BlogPostResponse>>

- POST /blog/organizer/posts
    - Request: BlogPostRequest
    - Response: ApiResponse<BlogPostResponse>

- GET /blog/admin/posts?page={page}&size={size}
    - Response: ApiResponse<Page<BlogPostResponse>>

- POST /blog/admin/posts
    - Request: BlogPostRequest
    - Response: ApiResponse<BlogPostResponse>

- PUT /blog/admin/posts/{id}
    - Request: BlogPostRequest
    - Response: ApiResponse<BlogPostResponse>

- PATCH /blog/admin/posts/{id}/publish
    - Response: ApiResponse<BlogPostResponse>

- PATCH /blog/admin/posts/{id}/reject
    - Response: ApiResponse<BlogPostResponse>

- PATCH /blog/admin/posts/{id}/archive
    - Response: ApiResponse<BlogPostResponse>

- DELETE /blog/admin/posts/{id}
    - Response: ApiResponse<Void>

- POST /blog/admin/tags
    - Request: BlogTagRequest
    - Response: ApiResponse<BlogTagResponse>

Notes:

- Path parameter types:
    - `/blog/posts/{slug}` — `slug` is a String (use for blog post lookup by slug).
    - `/blog/organizer/posts`, `/blog/admin/posts`, `/blog/admin/posts/{id}` — use `id` (numeric Long) for admin operations.

Example responses (abbreviated):

`ApiResponse<BlogPostResponse>` (single post):

```
{
    "code": 1000,
    "message": "OK",
    "result": {
        "id": 12,
        "title": "Event Announcement",
        "slug": "event-announcement",
        "summary": "Short summary...",
        "content": "Full HTML or markdown content...",
        "thumbnail": "https://.../thumb.jpg",
        "eventIds": [1, 2],
        "authorName": "Organizer A",
        "status": "PUBLISHED",
        "publishedAt": "2026-05-01T10:00:00",
        "createdAt": "2026-04-28T08:00:00",
        "tags": [{ "id": 1, "name": "music", "slug": "music" }]
    }
}
```

`ApiResponse<Page<BlogPostResponse>>` (paged list):

```
{
    "code": 1000,
    "message": "OK",
    "result": {
        "content": [ { /* BlogPostResponse objects */ } ],
        "pageable": { /* Spring Page metadata */ },
        "totalPages": 5,
        "totalElements": 50,
        "number": 0,
        "size": 10
    }
}
```
