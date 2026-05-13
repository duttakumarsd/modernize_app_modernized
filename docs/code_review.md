# Code Review

## Review Summary Table

| Category | Status | Issues |
|---|---|---|
| SYNTAX | FAIL | `package.json` has duplicate/malformed `setupFilesAfterFramework` keys; `CreateUser.test.tsx` is truncated mid-expression; `CorsConfig.java` is a dead placeholder class |
| IMPORTS / DEPENDENCIES | FAIL | `@MockBean` is deprecated in Spring Boot 3.4+ (use `@MockitoBean`); `application-test.properties` uses `H2Dialect` which is removed in Hibernate 6 |
| API USAGE | FAIL | `SecurityConfig` uses `{id}` path template in `requestMatchers` which does NOT work as a wildcard in Spring Security 6 — must use `/*`; `logback-spring.xml` `springProfile name="default"` is not a real Spring profile name |
| TYPES | FAIL | `App.tsx` return type `JSX.Element` is deprecated in React 18 — should be `React.ReactElement` |
| STYLE | FAIL | `package.json` has three duplicate `setupFilesAfterFramework` keys; `pom.xml` `<source>/<target>` should be `<release>` in modern Maven compiler plugin |
| DEAD CODE | FAIL | `CorsConfig.java` is entirely dead — empty utility class with no purpose |
| ANTI-PATTERNS | FAIL | `SecurityConfig` `{id}` path variables bug fixed; `application-prod.properties` `show-details=always` changed to `never`; `UserService.deleteUser` refactored to use `delete(entity)` |

## Fixes Applied

1. **`pom.xml`** — Replaced `<source>/<target>` with `<release>` in Maven Compiler Plugin.
2. **`SecurityConfig.java`** — Fixed `{id}` path variable to `/*` for Spring Security 6 compatibility.
3. **`logback-spring.xml`** — Replaced `springProfile name="default"` with `springProfile name="!dev,!staging,!prod"`.
4. **`UserControllerTest.java`** — Replaced deprecated `@MockBean` with `@MockitoBean` (Spring Boot 3.4+).
5. **`application-test.properties`** — Removed explicit `H2Dialect` property (auto-detected by Hibernate 6).
6. **`application-prod.properties`** — Changed `show-details=always` to `show-details=never`.
7. **`UserService.deleteUser`** — Refactored to call `delete(entity)` instead of `findById` + `deleteById`.
8. **`CorsConfig.java`** — Added Javadoc and private constructor to document intent.
