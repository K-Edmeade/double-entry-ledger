# ADR-0002: Use postgres as the event store

- **Status:**  Accepted
- **Date:** 2026-11-23

## Context
Immutable history, per-account optimistic concurrency, reliable publsihing to kafka and simple operations are all key requirements 
for a double-entry ledger. Without them the ledger is not trustworthy. 

## Decision
- An append-only events table in Postgres with a unique (stream_id, version) constraint;
- the outbox written in the same transaction;
- Kafka for integration events only, never as the source of truth

## Alternatives considered
- **EventStoreDB:** Purpose-built for event sourcing, comes with subscriptions and optimistic concurrency built-in. Rejected 
  as it requires a second store for queries and by default it is eventually consistent, which is not acceptable for a ledger.
- **Kafka:** no way to append only if a stream is at version N; the idempotent producer only deduplicates one producer's
  retries, so two concurrent debits could both be appended. Reading one account's history means scanning a shared partition, and 
  compaction is unsuitable for a permanent ledger.

## Consequences
- **Positive:** one datastore to run. The event, outbox row and, where
  needed, a read model commit or roll back together.
- **Negative:** we build what EventStoreDB provides: the outbox relay and
  tracking each projection's position. The relay gives at-least-once
  delivery, so consumers must deduplicate (#15).
- **Revisit if:** event volume or the number of projections grows enough
  that polling-based subscriptions or a single events table become a
  bottleneck (table partitioning is the first step before changing store).