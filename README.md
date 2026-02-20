### Strings are immutable
- means String objects are immutable.
- Literals go to the pool: `"Hello"`.
- `new String("Hello")` creates a new object on the heap (not in the pool).
- On creating a string using literal, literal object presence is first checked in String pool. If found, then the variable will be assigned with the found reference, else new object gets created in pool. `String name = "John";`
- Security -> Many core Java APIs use strings (e.g., file paths, class names, network addresses). If strings were mutable, malicious code could alter them after being validated.
- Thread-safety -> Immutable objects are naturally thread-safe—multiple threads can share the same string without synchronization.
- Performance -> String pool storage and fetch is fast, compared to regular object storage.
- Memory -> Since same object can be reference by many variable, object creation is reduced.
- Because strings are immutable, repeated concatenation creates lots of temporary objects. `StringBuilder` to be used.
- Refer src/d_strings/Immutability.java

### `== vs equals()` for Reference types (objects, arrays, Strings, wrappers, custom classes)
- `==` is for comparing reference or objects.
- `equals()` is for comparing value or content.
- For primitives, `==` compares value or content. `equals()` method cannot be used.
- Use `==`:
  - For primitives. 
  - For checking identity (e.g., singleton, enum).
- Use `.equals()`:
  - For content equality of objects (String, wrappers, collections, records, your own classes). 
  - Prefer Objects.equals(a, b) when either side can be null.
  - For Arrays use - `Arrays.equals(ar1, ar2)`, instead Object's equals() method.
- Refer src/d_strings/Immutability.java

### String vs StringBuilder vs StringBuffer:
| Use Case                                      | Recommendation        | Thread-safe | Concatenation   |
|-----------------------------------------------|------------------------|-------------|-----------------|
| Fixed text, configs, method params, map keys  | String                | yes         | not recommended |
| Building text dynamically in a single thread  | StringBuilder         | no          | faster          |
| Building text across multiple threads         | StringBuffer          | yes         | slower          |

### HashCode() function:
- Used to convert/reducing a String or any object to a number called as Hash.
- `myString.hashcode()` -> 567442
- Hash is created by summing of ASCII value of each char multiplied by its position.
- So, for Strings Hash value is calculated by content/value irrespective of memory location it is stored in.
- Refer src/d_strings/HashCodePractise.java

### Equals() and Hashcode() contract:
1. Rules for equals():
   - `Reflexive`: x.equals(x) is always true. 
   - `Symmetric`: If x.equals(y) is true, then y.equals(x) must also be true. 
   - `Transitive`: If x.equals(y) and y.equals(z) are true, then x.equals(z) must be true. 
   - `Consistent`: Repeated calls return the same result as long as the objects don’t change. 
   - `Null comparison`: x.equals(null) must return false.
2. Rules for hashCode():
    - `Consistent`: Multiple calls should return the same value if the object state doesn’t change. 
    - `Equal objects must have equal hash codes`: If x.equals(y) == true, then x.hashCode() == y.hashCode(). 
    - Unequal objects can share a hash (but should minimize collisions): If x.equals(y) == false, then x.hashCode() may be equal, but try to avoid it for performance

