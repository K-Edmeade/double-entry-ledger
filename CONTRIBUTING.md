# Engineering principles

How work is done in this repo. These apply to every change, including solo work.

## How changes land

1. **Small, green commits.** Every commit builds, passes tests and does one thing. Messages follow [Conventional Commits](https://www.conventionalcommits.org/) (`feat:`, `fix:`, `test:`, `refactor:`, `docs:`, `chore:`).
2. **A pull request for every issue.** Short-lived branches off `main`. Each PR says what changed, why, how it was tested and what trade-offs were made, and is self-reviewed before merging.
3. **`main` is always releasable.** CI (build, tests, formatting, static analysis) is the merge gate. Nothing merges on red.

## How decisions are made

4. **An ADR for any decision with a real alternative.** Recorded in `docs/adr/` as context, decision and consequences.
5. **Design before code for anything non-trivial.** The issue holds a short note on approach, edge cases and how correctness will be shown.
6. **Non-goals are explicit.** What is deliberately not built, and why, lives in the README.

## How correctness is proven

7. **Test-first for domain logic.** Invariants are property-based tests (jqwik). Integration tests run against real Postgres and Kafka via Testcontainers, never in-memory substitutes.
8. **Money is never floating point.** A `Money` type holds minor units and a currency. Ledger entries are immutable; mistakes are corrected with reversals, not deletes.

## How it runs

9. **Observable from day one.** Structured logs carry a correlation ID and the idempotency key. Metrics cover postings, retries and duplicates caught.
10. **Failure is explicit.** Every external call has a documented timeout and retry policy. API errors use [Problem Details (RFC 9457)](https://www.rfc-editor.org/rfc/rfc9457).
11. **One command to run.** `docker compose up` starts everything; a script posts sample transactions.

## Hygiene

12. **No secrets in the repo.** Dependabot keeps dependencies current.
