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

- GET /blog/news
    - Response: ApiResponse<List<BlogEventResponse>>
