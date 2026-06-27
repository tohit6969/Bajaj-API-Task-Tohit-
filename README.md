# Bajaj-API-Task-Tohit

Structure ->

 bfhl/
 
├── pom.xml

├── Dockerfile

├── render.yaml

├── README.md

└── src/

    ├── main/java/com/bfhl/
    │   ├── BfhlApplication.java
    │   ├── controller/BfhlController.java
    │   ├── dto/
    │   │   ├── BfhlRequest.java       ← Request DTO with @Valid
    │   │   ├── BfhlResponse.java      ← Response DTO with @JsonProperty
    │   │   └── ErrorResponse.java
    │   ├── exception/
    │   │   └── GlobalExceptionHandler.java
    │   └── service/
    │       ├── BfhlService.java       ← Interface
    │       └── BfhlServiceImpl.java   ← Implementation
    └── test/java/com/bfhl/
        ├── BfhlServiceTest.java          ← 11 unit tests
        └── BfhlControllerIntegrationTest.java ← 5 integration tests
