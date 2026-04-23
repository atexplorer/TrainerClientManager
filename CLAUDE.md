# TrainerClientManager — Claude Instructions

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 23 |
| Framework | Spring Boot 4.0.5 |
| Web | Spring WebMVC (`spring-boot-starter-webmvc`) |
| Security | Spring Security (`spring-boot-starter-security`) |
| Persistence | Spring Data JPA + Hibernate (`spring-boot-starter-data-jpa`) |
| Database | H2 (in-memory, dev/test) |
| Validation | Hibernate Validator 9.x (`jakarta.validation` annotations) |
| JSON | Jackson 3 (`tools.jackson` package — NOT `com.fasterxml.jackson`) |
| Build | Maven 3.9 |

## Running Maven

Maven was installed via winget and should be on the PATH in any new Claude Code session started after installation. If `mvn` is not found, fall back to the full path:

```
/c/Program\ Files/apache-maven-3.9.15/bin/mvn <goal>
```

Run all tests:
```
mvn test --no-transfer-progress
```

## Testing Requirements

- **All new code must have tests.** Controllers, services, and non-trivial factory/utility logic each need coverage.
- **All tests must pass before work is considered done.** Run `mvn test` to verify before reporting completion.
- **When tests fail after a code change, stop and report to the user before modifying any test.** The report must include:
  - Which tests failed and what the failure message says
  - A clear diagnosis: is this a **bug** (the code change broke something it shouldn't have) or an **expected consequence** (the test no longer reflects the new intended behavior)?
  - Wait for the user to confirm whether the test should be updated before proceeding.
- **Do not remove or weaken test assertions** unless the user has approved it.
- **If new fields are added to DTOs or entities**, update related tests to include those fields in setup fixtures and assertions where relevant.

## Jackson 3 — Important Package Change

Spring Boot 4.x uses **Jackson 3**, which moved to the `tools.jackson` namespace. The old `com.fasterxml.jackson` packages no longer apply.

| Old (Jackson 2) | New (Jackson 3) |
|---|---|
| `com.fasterxml.jackson.databind.ObjectMapper` | `tools.jackson.databind.json.JsonMapper` |
| `import com.fasterxml.jackson.databind.*` | `import tools.jackson.databind.*` |

In tests, autowire `JsonMapper` (not `ObjectMapper`):
```java
@Autowired
private JsonMapper jsonMapper;
```

## Controller Testing Approach

Spring Boot 4.x reorganized test slices into dedicated starters. `@WebMvcTest` has moved and its package path is different from Spring Boot 3.x. **Use `@SpringBootTest` + `MockMvcBuilders` instead:**

```java
@SpringBootTest
class MyControllerTest {

    @Autowired
    private WebApplicationContext context;

    @MockitoBean
    private MyService myService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
}
```

Use `@MockitoBean` (not `@MockBean` — that was deprecated in Spring Boot 3.4).

## Spring Data JPA — Derived Query Naming

When writing derived query methods on repositories that involve a relationship, traverse the full property path. `UserProfile` links to `AppUser` via the `appUser` field, so:

```java
// WRONG — 'username' is not a direct field on UserProfile
Optional<ClientProfile> findClientProfileByUsername(String username);

// CORRECT — traverses appUser.username
Optional<ClientProfile> findClientProfileByAppUserUsername(String username);
```

## Architecture Overview

### Entities

```
AppUser              — authentication credentials (username, password, authority)
UserProfile          — abstract base, single-table inheritance, joined to AppUser 1:1
  ClientProfile      — discriminator "CLIENT"; adds goals[], trainer (ManyToOne)
  TrainerProfile     — discriminator "TRAINER"; adds certifications[], clients (OneToMany)
```

### Registration Flow

Two separate public endpoints, each with a typed DTO:

| Endpoint | DTO | Profile created |
|---|---|---|
| `POST /api/accounts/client` | `CreateClientAccountDto` | `ClientProfile` |
| `POST /api/accounts/trainer` | `CreateTrainerAccountDto` | `TrainerProfile` |

Both DTOs extend `CreateAccountDto` (username, password, email, name fields with validation).

### DTO Hierarchy

```
CreateAccountDto          — base for registration (username ≥6, password ≥6, email, name)
  CreateClientAccountDto  — adds goals[]
  CreateTrainerAccountDto — adds certifications[]

AppUserDto                — base for responses (no password/authority)
  ClientProfileDto        — adds goals[], trainer
  TrainerProfileDto       — adds clientProfiles[]
```

### Key Classes

| Class | Role |
|---|---|
| `ProfileFactory` | Static methods to create `ClientProfile` / `TrainerProfile` from registration DTOs |
| `AppUserServiceImpl` | Orchestrates `AppUser` creation + profile creation; validates username uniqueness |
| `SecurityConfig` | Permits `POST /api/accounts/client` and `POST /api/accounts/trainer` without auth |
| `UserProfileRepository` | JPA repository with derived queries for finding profiles by `appUser.username` |

## Security

- Registration endpoints (`/api/accounts/client`, `/api/accounts/trainer`) are public — no auth required.
- All other endpoints require authentication (Basic Auth by default via Spring Security).
- Passwords are encoded with `BCryptPasswordEncoder`.
- Sessions are stateless.
