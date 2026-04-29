# Tickets APIs

## TicketController (base: /tickets)

- GET /tickets/my-tickets
    - Description: Xem danh sach ve da mua
    - Response: ApiResponse<List<TicketResponse>>

- POST /tickets/check-in?ticketCode={ticketCode}
    - Description: Quet ma ve de check-in (ban to chuc/admin)
    - Query: ticketCode
    - Response: ApiResponse<TicketResponse>

## Example: my tickets

```json
{
    "code": 1000,
    "message": "success",
    "result": [
        {
            "id": 1,
            "eventName": "Music Show",
            "ticketTypeName": "VIP",
            "ticketCode": "TKT-ABC123",
            "qrCode": "https://...",
            "status": "VALID",
            "usedAt": null
        }
    ]
}
```
