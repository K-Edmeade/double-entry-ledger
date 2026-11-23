# ADR-0001: Record architecture decisions
- **Status:** Accepted
- **Date:** 2026-11-23

## Context
Decisions and their rationale are easily lost when they live only in someone’s head or a chat thread. Without a record, future readers 
(reviewers, contributors, or the author months later) either repeat the research or undo a good decision without knowing why it was made.

## Decision
- Format: Nygard with the additional sections for alternatives considered and revisit if.
- Location: docs/adr/, numbered NNNN-title.md, in the repo next to the code.
- When: any decision with a real alternative, an ADR is written before or alongside the change it describes: ahead of the code when the 
  decision shapes upcoming work, otherwise in the same PR.
- Immutability: Accepted ADRs are never edited. A changed decision gets a new ADR that supersedes the old one, and the old one’s status becomes “Superseded by 
  ADR-NNNN”.

## Alternatives considered
- **No records:** Rationale gets lost.
- **A wiki or Confluence:** It drifts away from the code and doesn’t go through review.
- **PR descriptions only:** Hard to find later, and decisions spread across many PRs.

## Consequences
- **Positive:** decisions are reviewable, searchable and versioned with the code.
- **Negative:** a small writing overhead on each significant decision.
- **Revisit if:** the overhead starts to outweigh the value.
- **Note:** the first ADRs record decisions made before this log existed.