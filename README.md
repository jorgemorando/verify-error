# Spring Data JDBC VerifyError - Minimal Reproduction

Minimal project that reproduces a `VerifyError` when persisting an entity with a `@MappedCollection` of **record** entities that have **no `@Id`** (composite key).

## Error

```
java.lang.VerifyError: Bad type on operand stack in putfield
Exception Details:
  Location:
    example/domain/SearchPost__Accessor_*.setProperty(Lorg/springframework/data/mapping/PersistentProperty;Ljava/lang/Object;)V @55: putfield
  Reason:
    Type 'example/domain/SearchPost' (current frame, stack[1]) is not assignable to 'example/domain/SearchPost__Accessor_*' (constant pool 32)
```

## Root Cause

`ClassGeneratingPropertyAccessorFactory` generates invalid bytecode for the `SearchPost` record when Spring Data JDBC needs to persist it. The generated accessor class attempts a `putfield` with an incompatible type.

## Trigger Conditions

- Parent entity (`Search`) has `@MappedCollection(idColumn = "search_id")` of child entities
- Child entity (`SearchPost`) is a **Java record** with **no `@Id`** (identified by composite key `search_id` + `created_at`)
- Persisting the aggregate via `CrudRepository.save()` triggers the error when Spring Data processes the child collection

## How to Reproduce

```bash
mvn test -Dtest=SearchRepositoryIT
```

## Environment

- Spring Boot 4.0.1
- Spring Data JDBC
- Java 21+ (tested with Java 25)
- H2 (in-memory)

## Project Structure

```
src/main/java/example/
├── MinimalVerifyErrorApplication.java
├── domain/
│   ├── Search.java          # Parent with @MappedCollection of SearchPost
│   └── SearchPost.java      # Record without @Id (composite key)
└── persistence/
    └── SearchRepository.java

src/test/java/example/
└── SearchRepositoryIT.java  # Triggers VerifyError on save()
```

## For Spring Framework Issue Tracker

**Suggested title:** `VerifyError when persisting @MappedCollection of record`

**Suggested labels:** `spring-data-jdbc`, `type: bug`, `status: waiting-for-triage`

**Steps to reproduce:**

1. Clone this project
2. Run `mvn test -Dtest=SearchRepositoryIT`
3. Observe `VerifyError` in `SearchPost__Accessor_*.setProperty`
