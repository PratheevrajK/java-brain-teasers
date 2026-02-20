
# Java Method Parameter Passing (Pass‑by‑Value) — Complete Guide

> **Java is always *pass‑by‑value***. When you pass an object (arrays, collections, custom types), the *value* that gets copied is the **reference** to that object. Mutating the object through that reference is visible to the caller; reassigning the local parameter is not.

---

## 🧠 Core Idea
- **Primitives** (e.g., `int`, `double`) are stored as values. A method receives a **copy of the value** → changes inside the method **don’t** affect the caller.
- **Objects** (arrays, collections, user‑defined classes, `StringBuilder`, etc.) are referenced by variables. A method receives a **copy of the reference** → both caller and callee point to the **same object**.
  - **Mutation** (changing object state) is **visible** to the caller.
  - **Reassignment** (making the parameter reference a new object) is **not** visible to the caller.
- **Immutables** (`String`, wrapper classes like `Integer`) can’t be mutated in place, so you only ever see differences if you **reassign** (which doesn’t affect the caller).

Small mental model:
```text
main():                 method():
  ref ─────┐              param (copy of ref) ───┐
           ▼                                      ▼
        [ OBJECT in heap ]  <── both point to the same object
```

---

## 1) Primitives (int, double, boolean, ...)
**Passes a copy of the actual value.**

```java
static void change(int x) {
    x = 100; // only modifies local copy
}

public static void main(String[] args) {
    int a = 5;
    change(a);
    System.out.println(a); // 5 (unchanged)
}
```

---

## 2) String (immutable object)
**Reference is copied, but String can’t be mutated. Reassignment only changes the local reference.**

```java
static void change(String s) {
    s = "changed"; // local reassignment; caller not affected
}

public static void main(String[] args) {
    String str = "hello";
    change(str);
    System.out.println(str); // "hello"
}
```

---

## 3) Wrapper Classes (Integer, Double, ...)
**Also immutable; behaves like String.**

```java
static void change(Integer x) {
    x = 999; // local reassignment; caller not affected
}

public static void main(String[] args) {
    Integer a = 10;
    change(a);
    System.out.println(a); // 10
}
```

---

## 4) Arrays (mutable objects)
**Copy of the reference is passed; mutations affect original. Reassignment does not.**

### Mutation (visible to caller)
```java
static void mutate(int[] arr) {
    arr[0] = 50; // modifies shared array
}

public static void main(String[] args) {
    int[] nums = {1, 2, 3};
    mutate(nums);
    System.out.println(java.util.Arrays.toString(nums)); // [50, 2, 3]
}
```

### Reassignment (not visible to caller)
```java
static void reassign(int[] arr) {
    arr = new int[]{9, 9, 9}; // only the local parameter now points here
}

public static void main(String[] args) {
    int[] nums = {1, 2, 3};
    reassign(nums);
    System.out.println(java.util.Arrays.toString(nums)); // [1, 2, 3]
}
```

> ✅ If you don’t want to mutate the caller’s array, create a **defensive copy** inside the method.
```java
static int[] reversedCopy(int[] arr) {
    int[] copy = java.util.Arrays.copyOf(arr, arr.length);
    for (int i = 0, j = copy.length - 1; i < j; i++, j--) {
        int tmp = copy[i];
        copy[i] = copy[j];
        copy[j] = tmp;
    }
    return copy; // original remains unchanged
}
```

---

## 5) Collections (ArrayList, HashMap, ...)
**Mutable objects. Mutation is visible; reassignment is not.**

### Mutation (visible)
```java
static void addItem(java.util.List<Integer> list) {
    list.add(100);
}

public static void main(String[] args) {
    java.util.List<Integer> list = new java.util.ArrayList<>();
    list.add(1);
    addItem(list);
    System.out.println(list); // [1, 100]
}
```

### Reassignment (not visible)
```java
static void replaceList(java.util.List<Integer> list) {
    list = new java.util.ArrayList<>();
    list.add(999);
}

public static void main(String[] args) {
    java.util.List<Integer> list = new java.util.ArrayList<>();
    list.add(1);
    replaceList(list);
    System.out.println(list); // [1]
}
```

---

## 6) User‑Defined Classes
**Objects are mutable by default (unless you design them otherwise).**

```java
class Person {
    String name;
    Person(String name) { this.name = name; }
}

static void modify(Person p) {
    p.name = "Raj"; // mutation -> visible to caller
}

static void reassign(Person p) {
    p = new Person("New Object"); // reassignment -> NOT visible
}

public static void main(String[] args) {
    Person person = new Person("Pratheev");

    modify(person);
    System.out.println(person.name); // Raj

    reassign(person);
    System.out.println(person.name); // Still Raj
}
```

---

## 7) StringBuilder (mutable object)
**Great example of a mutable object: appending inside a method changes caller’s content.**

### Mutation (visible)
```java
static void appendWorld(StringBuilder sb) {
    sb.append(" World");
}

public static void main(String[] args) {
    StringBuilder sb = new StringBuilder("Hello");
    appendWorld(sb);
    System.out.println(sb.toString()); // Hello World
}
```

### Reassignment (not visible)
```java
static void reassignBuilder(StringBuilder sb) {
    sb = new StringBuilder("New"); // local reassignment
    sb.append(" Content");
}

public static void main(String[] args) {
    StringBuilder sb = new StringBuilder("Hello");
    reassignBuilder(sb);
    System.out.println(sb.toString()); // Hello
}
```

---

## 🔍 Pass‑by‑Value vs Reference — What Exactly Is Copied?
- Java never passes the actual object; it passes a **copy of the variable**.
  - For primitives, that variable **holds a value** → the value is copied.
  - For objects, that variable **holds a reference** → the reference is copied.
- Because the **same object** is reachable from both caller and callee, **mutations** are shared.
- **Reassigning** the parameter only changes the local copy of the reference.

---

## 📝 Cheat Sheet

| Type                         | Mutable? | What gets passed?          | Mutations visible to caller? | Reassigning parameter visible? |
|-----------------------------|---------:|-----------------------------|:-----------------------------:|:-------------------------------:|
| `int`, `double`, `boolean`  |    No     | Copy of the **value**       |              ❌               |                ❌               |
| `String`                    |    No     | Copy of the **reference**   |     ❌ (immutable object)     |                ❌               |
| Wrapper (`Integer`, etc.)   |    No     | Copy of the **reference**   |     ❌ (immutable object)     |                ❌               |
| Array (`int[]`, `T[]`, …)   |   Yes     | Copy of the **reference**   |              ✅               |                ❌               |
| Collections (`List`, …)     |   Yes     | Copy of the **reference**   |              ✅               |                ❌               |
| User‑defined class          |   Yes*    | Copy of the **reference**   |              ✅               |                ❌               |

> *User‑defined classes are mutable unless you intentionally make them immutable (e.g., all fields `final`, no setters, defensive copies).

---

## ✅ Key Takeaways
- Java is **always pass‑by‑value**.
- For objects, the **reference value** is passed by value.
- **Mutate** to affect the caller; **reassign** only affects the local parameter.
- Immutability (`String`, wrappers) means you cannot observe in‑place changes, only new object assignments.
- Use **defensive copies** when you don’t want side effects.

---

## Bonus: Defensive Copy Pattern (Collections)
```java
static java.util.List<String> safeAdd(java.util.List<String> input, String item) {
    java.util.List<String> copy = new java.util.ArrayList<>(input); // shallow copy of references
    copy.add(item);
    return copy; // original list unchanged
}
```

> For deep immutability, you must also **deep‑copy** nested mutable objects.

---

*Prepared for: Pass‑by‑Value clarification across primitives, Strings, wrappers, arrays, collections, user‑defined classes, and StringBuilder.*
