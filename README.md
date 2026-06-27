# BFHL API

REST API built with **Java 17 + Spring Boot 3.2**.

## POST /bfhl

**Request**
```json
{ "data": ["a", "1", "334", "4", "R", "$"] }
```

**Response (200 OK)**
```json
{
  "is_success": true,
  "user_id": "john_doe_17091999",
  "email": "john@xyz.com",
  "roll_number": "ABCD123",
  "odd_numbers": ["1"],
  "even_numbers": ["334", "4"],
  "alphabets": ["A", "R"],
  "special_characters": ["$"],
  "sum": "339",
  "concat_string": "Ra"
}
```

---

## Run Locally

```bash
mvn spring-boot:run
# API available at http://localhost:8080/bfhl
```

## Run Tests

```bash
mvn test
```

---

## Deploy to Render (free tier)

1. Push this repo to GitHub.
2. Go to [render.com](https://render.com) → **New Web Service** → connect your repo.
3. Render auto-detects `render.yaml` / Dockerfile.
4. Once deployed your endpoint will be: `https://<app-name>.onrender.com/bfhl`

## Deploy to Railway

1. Push this repo to GitHub.
2. Go to [railway.app](https://railway.app) → **New Project** → **Deploy from GitHub repo**.
3. Railway auto-detects Dockerfile and builds.
4. Set `PORT=8080` in Environment Variables if needed.
5. Endpoint: `https://<app-name>.railway.app/bfhl`

---

## Project Structure

```
src/
├── main/java/com/bfhl/
│   ├── BfhlApplication.java          # Entry point
│   ├── controller/BfhlController.java # POST /bfhl
│   ├── dto/
│   │   ├── BfhlRequest.java          # Request DTO
│   │   ├── BfhlResponse.java         # Response DTO
│   │   └── ErrorResponse.java        # Error DTO
│   ├── exception/
│   │   └── GlobalExceptionHandler.java
│   └── service/
│       ├── BfhlService.java          # Interface
│       └── BfhlServiceImpl.java      # Implementation
└── test/java/com/bfhl/
    ├── BfhlServiceTest.java          # Unit tests (11 cases)
    └── BfhlControllerIntegrationTest.java # Integration tests (5 cases)
```
