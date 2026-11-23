# ADR-0003: Spring JDBC over JPA

- **Status:** Accepted
- **Date:** 2026-11-23

## Context
This records a decision made when bootstrapping the project (PR #34).

The persistence layer needs to:
- append events with JSONB payloads, conditional on the stream version
- insert an outbox row in the same transaction
- upsert projection tables

Nothing needs a rich object graph or entity lifecycle management.

## Decision
- Use Spring JDBC with `JdbcClient` (from Spring 6.1 onwards), with explicit SQL.
- Use `@Transactional` to manage transactions.
- Flyway owns the schema. No JPA or Hibernate.

## Alternatives considered
- **JPA / Hibernate:** A lot of its key features are not needed for an append-only event store e.g. lazy-loading, caching and dirty-checking.
  Features also make it easy to accidentally break the append-only constraint. JSONB needs extra mapping.
- **Spring Data JDBC:** lighter than JPA, but aggregate-oriented: it decides
  whether a save is an insert or an update, which gets in the way of an
  explicit "insert only if at version N".
- **jOOQ:** type-safe SQL and a good fit, but it needs code generation from
  the schema in the build. Not worth the setup at this size.

## Consequences
- **Positive:** every query is visible and reviewable. Exact control over constraints, and a unique-constraint violation maps 
  cleanly to a "version conflict" error.
- **Negative:** more hand-written mapping code (`RowMapper`), and SQL isn't checked at compile time. Integration tests against real Postgres 
  through Testcontainers catch SQL errors instead.
- **Revisit if:** the amount of hand-written SQL and mapping grows enough to slow down development. jOOQ would be the next step.