
# Creating Immutable Classes in Java — Complete Guide

An **immutable** class is one whose instances **cannot change state** after construction. Any operation that seems to "modify" instead returns a **new instance**. Immutability improves thread-safety, testability, and reasoning about code.

---

## ✅ Core Rules for Immutability

1. **Make the class `final`** (or make all constructors `private` and expose factory methods) so it can’t be subclassed to add mutability.
2. **Make all fields `private` and `final`.**
3. **Provide no setters** (and no mutating methods).
4. **Defensively copy** any **mutable inputs** in constructors and **mutable state** when returning from accessors:
   - Mutable examples: `Date`, arrays, `List`, `Map`, custom mutable objects.
5. **Functional updates**: For changes, provide methods that return **new instances** (e.g., `withX(...)`).
6. **Implement `equals`, `hashCode`, `toString`** (recommended for value objects).

> **Why immutable?**
> - Naturally **thread-safe** (no internal state changes)
> - **Safe to share** across threads and components
> - **Cacheable**; can be used as **Map keys** reliably
> - **Fewer bugs** from unintended aliasing

---

## Example 1 — Simple Immutable Class (Primitives & String)

```java
public final class Money {
    private final String currency;            // String is immutable
    private final long amountInMinorUnits;    // e.g., cents or paise

    public Money(String currency, long amountInMinorUnits) {
        if (currency == null || currency.isBlank()) {
            throw new IllegalArgumentException("currency required");
        }
        this.currency = currency;
        this.amountInMinorUnits = amountInMinorUnits;
    }

    public String currency() {
        return currency; // safe: String is immutable
    }

    public long amountInMinorUnits() {
        return amountInMinorUnits;
    }

    // Functional update returns a NEW instance
    public Money add(long deltaMinorUnits) {
        return new Money(this.currency, this.amountInMinorUnits + deltaMinorUnits);
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Money)) return false;
        Money that = (Money) o;
        return amountInMinorUnits == that.amountInMinorUnits &&
               java.util.Objects.equals(currency, that.currency);
    }

    @Override public int hashCode() {
        return java.util.Objects.hash(currency, amountInMinorUnits);
    }

    @Override public String toString() {
        return currency + " " + amountInMinorUnits;
    }
}
```

**Why immutable?** Fields are `final`, no setters, and all state is either primitive or an immutable class (`String`).

---

## Example 2 — Immutable Class with Mutable Fields (`Date`, `List`)

When your class holds **mutable** types, you must **defensively copy** them **on the way in** and **on the way out**.

```java
import java.util.*;

public final class Person {
    private final String name;      // immutable
    private final Date birthDate;   // mutable -> must copy
    private final List<String> tags; // mutable -> must copy

    public Person(String name, Date birthDate, List<String> tags) {
        if (name == null || birthDate == null || tags == null) {
            throw new IllegalArgumentException("args must not be null");
        }
        this.name = name;
        this.birthDate = new Date(birthDate.getTime()); // defensive copy
        this.tags = List.copyOf(new ArrayList<>(tags));  // unmodifiable copy
    }

    public String getName() {
        return name;
    }

    public Date getBirthDate() {
        return new Date(birthDate.getTime()); // defensive copy on accessor
    }

    public List<String> getTags() {
        return tags; // already unmodifiable via List.copyOf
    }

    // Functional 'with' methods return NEW instances
    public Person withName(String newName) {
        return new Person(newName, this.getBirthDate(), this.getTags());
    }

    public Person withAddedTag(String tag) {
        List<String> newTags = new ArrayList<>(this.tags);
        newTags.add(tag);
        return new Person(this.name, this.getBirthDate(), newTags);
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person that = (Person) o;
        return Objects.equals(name, that.name) &&
               Objects.equals(birthDate, that.birthDate) &&
               Objects.equals(tags, that.tags);
    }

    @Override public int hashCode() {
        return Objects.hash(name, birthDate, tags);
    }

    @Override public String toString() {
        return "Person{" +
               "name='" + name + '\'' +
               ", birthDate=" + birthDate +
               ", tags=" + tags +
               '}';
    }
}
```

**Key points:**
- `Date` and `List` are **mutable**, so we copy in constructor and when exposing via getters.
- `List.copyOf(...)` returns an **unmodifiable** list.
- We avoid leaking internal mutable state.

---

## Example 3 — Immutable Class with Arrays

Arrays are mutable. Clone them in constructors and accessors.

```java
public final class ImmutableVector {
    private final int[] coords; // mutable type

    public ImmutableVector(int[] coords) {
        if (coords == null) throw new IllegalArgumentException("coords required");
        this.coords = coords.clone(); // defensive copy in
    }

    public int dimension() {
        return coords.length;
    }

    public int get(int i) {
        return coords[i];
    }

    public int[] toArray() {
        return coords.clone(); // defensive copy out
    }

    public ImmutableVector withUpdated(int i, int value) {
        int[] copy = coords.clone();
        copy[i] = value;
        return new ImmutableVector(copy);
    }
}
```

---

## Example 4 — Immutable with a Builder (Fluent Construction)

Builders make construction flexible while keeping the **built** object immutable.

```java
import java.util.*;

public final class UserProfile {
    private final String username;
    private final String email;
    private final List<String> roles;

    private UserProfile(Builder b) {
        this.username = b.username;
        this.email = b.email;
        this.roles = List.copyOf(b.roles); // defensive copy at build time
    }

    public static class Builder {
        private String username;
        private String email;
        private List<String> roles = new ArrayList<>();

        public Builder username(String username) { this.username = username; return this; }
        public Builder email(String email) { this.email = email; return this; }
        public Builder addRole(String role) { this.roles.add(role); return this; }

        public UserProfile build() {
            Objects.requireNonNull(username, "username");
            Objects.requireNonNull(email, "email");
            return new UserProfile(this);
        }
    }

    public String username() { return username; }
    public String email() { return email; }
    public List<String> roles() { return roles; } // already unmodifiable
}
```

> The **builder is mutable**; the **built object** is immutable.

---

## Example 5 — Java Records (Java 16+) for Concise Immutability

**Records** are concise, immutable data carriers by default. Fields are implicitly `private final`, and the compiler generates `equals`, `hashCode`, and accessors.

```java
public record Point(int x, int y) {
    public Point {
        if (x < 0 || y < 0) throw new IllegalArgumentException("non-negative required");
    }
}
```

> ⚠️ If a record contains **mutable components** (e.g., `List`, `Date`, arrays), you must still **defensively copy** in the canonical constructor to protect the record’s logical immutability.

---

## "Unmodifiable" vs "Immutable"

- **Unmodifiable view**: e.g., `Collections.unmodifiableList(list)` prevents mutation **through that reference**, but if someone else holds the original mutable list, they can still change the underlying data.
- **Immutable object**: No one can change state from anywhere after construction. We achieve this by **defensive copies** and not exposing mutators.

---

## Thread‑Safety Considerations

Immutable objects are **naturally thread‑safe**:
- No writable state eliminates data races.
- They can be freely cached and shared.
- Beware of embedding references to **external mutable state** (avoid or copy it defensively).

---

## Quick Checklist

- [x] `final` class (or private constructor + factories)
- [x] All fields `private final`
- [x] No setters / mutators
- [x] Defensive copies for mutable inputs and outputs
- [x] Functional `withX(...)` methods return new instances
- [x] Validate inputs in constructor/builder
- [x] Consider `equals`, `hashCode`, `toString`

---

## Mini Test to Validate Immutability (Person example)

```java
public static void main(String[] args) {
    var d = new java.util.Date();
    var tags = new java.util.ArrayList<>(java.util.List.of("java", "immutability"));
    Person p = new Person("Pratheev", d, tags);

    // Try to mutate sources after construction
    d.setTime(0);
    tags.add("oops");

    // Try to mutate through accessors
    p.getBirthDate().setTime(1234567);
    try { p.getTags().add("should-fail"); } catch (UnsupportedOperationException expected) {}

    System.out.println(p); // logical state unchanged
}
```

---

## Notes & Best Practices

- Prefer **composition of immutable types**. If you must hold mutable state, **own it** and protect it with copies.
- For performance-sensitive code, defensive copies have a cost—measure and consider **persistent data structures** or **copy-on-write** patterns where appropriate.
- Keep invariants in constructors/builders (e.g., validate non-null, ranges).

---

*This guide shows how to design immutable classes with primitives, Strings, mutable fields (`Date`, `List`, arrays), builders, and Java Records, plus defensive-copy patterns and practical tests.*
