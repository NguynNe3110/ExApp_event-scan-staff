# Auth & Users APIs

## AuthenticationController (base: /auth)

- POST /auth/login
    - Request: AuthenticationRequest
    - Response: ApiResponse<AuthenticationResponse>

- POST /auth/register
    - Request: UserCreateRequest
    - Response: ApiResponse<String>

- POST /auth/register-staff
    - Request: StaffCreateRequest
    - Response: ApiResponse<String>

- GET /auth/verify?token={token}
    - Request: query `token`
    - Response: String (text/html)

- POST /auth/forgot-password
    - Request: ForgotPasswordRequest
    - Response: ApiResponse<String>

- POST /auth/reset-password
    - Request: ResetPasswordRequest
    - Response: ApiResponse<String>

- POST /auth/introspect
    - Request: IntrospectRequest
    - Response: ApiResponse<IntrospectResponse>

- POST /auth/refresh
    - Request: RefreshRequest
    - Response: ApiResponse<AuthenticationResponse>

- POST /auth/logout
    - Request: LogoutRequest
    - Response: ApiResponse<Void>

## UserController (base: /users, bearerAuth)

- GET /users/my-info
    - Response: ApiResponse<UserResponse>

- PUT /users/{username}
    - Request: UserUpdateRequest
    - Response: ApiResponse<UserResponse>

### Admin APIs

- GET /users/admin?page={page}&size={size}&search={search}&role={role}
    - Query `role`: CUSTOMER | ORGANIZER (optional)
    - Response: ApiResponse<Page<UserResponse>>

- GET /users/admin/stats
    - Response: ApiResponse<Map<String, Long>>

- DELETE /users/admin/{username}
    - Response: ApiResponse<String>

- PATCH /users/admin/{username}/unlock
    - Response: ApiResponse<String>

### Organizer APIs

- GET /users/organizer/my-staff?page={page}&size={size}&search={search}
    - Response: ApiResponse<Page<UserResponse>>

- DELETE /users/organizer/staff/{username}
    - Response: ApiResponse<String>

- PATCH /users/organizer/staff/{username}/enable
    - Response: ApiResponse<String>

## Example: update user profile

Endpoint: PUT /users/{username}

```json
{
    "password": "string",
    "email": "string",
    "fullName": "string",
    "phone": "0910313724",
    "address": "string"
}
```
