# Changelog

## [2.6.2] - 2026-02-20
### Patch
- Fix tweet interactions count.

## [2.6.1] - 2026-02-20
### Patch
- Fix password encode error when update user.

## [2.6.0] - 2026-02-20
### Feat
- Implement cache to paged responses (Users & Tweets).
- Add cache invalidation on any User/Tweet update.

## [2.5.0] - 2026-02-19
### Patch
- Create healthcheck endpoint.

## [2.4.4] - 2026-02-18
### Patch
- Fix tweet interaction endpoints (wrong path).

## [2.4.3] - 2026-02-18
### Patch
- Add PORT var env.

## [2.4.2] - 2026-02-18
### Patch
- Change cache serialization type to JSON.

## [2.4.1] - 2026-02-18
### Patch
- Fix var envs on docker-compose.yaml and CHANGELOG version.

## [2.4.0] - 2026-02-18
### Feature
- Implement cache with Redis.

## [2.3.3] - 2026-02-14
### Patch
- Disable CSRF

## [2.3.2] - 2026-02-14
### Patch
- Update `README`. Includes: 
  - Architecture
  - Design
  - UML diagrams
  - Entity Relation diagram

## [2.3.1] - 2026-02-13
### Patch
- Update home page title to `Twitter API`

## [2.3.0] - 2026-02-13
### Feature
- Add static home page

## [2.2.1] - 2026-02-13
### Patch
- Fix Open API environment variables 

## [2.2.0] - 2026-02-13
### Feature
- Deploy on Railway (Core + Database)
- Implement API Documentation with Open API (Swagger)
- Optimize docker build
- Improve README

## [2.1.0] - 2026-02-10
### Feature
- Add unit tests to basic user operations (create/delete).

## [2.0.1] - 2026-02-05
### Patch
- Fix list users endpoints to improve performance.

## [2.0.0] - 2026-01-23
### Release
- Start version control and deprecate v1.0.0
