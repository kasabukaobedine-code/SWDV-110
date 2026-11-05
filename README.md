# Week 4 Coding Exercise: Listening History Analyzer Reflection

### 1. Pick one method in MusicStats and rewrite it with a for loop in your README. Which version is easier to read? Which is easier to change?
If we rewrote `totalMinutes` using a traditional loop, it would look like this:
```java
public static long totalMinutes(List<Play> plays, Predicate<Play> filter) {
    long totalSeconds = 0;
    for (Play play : plays) {
        if (filter.test(play)) {
            totalSeconds += play.seconds();
        }
    }
    return totalSeconds / 60;
}
```
The stream version is much easier to read because it states *what* to do conceptually (filter, map, sum) rather than managing the mechanics of iterating over the collection. The stream pipeline is also much easier to change; if we needed to exclude skips, we could simply snap in another `.filter()` link without changing any structural loop syntax.

### 2. PlayFormatter and Function<Play, String> have the same shape. Why might a team still create its own functional interface?
A team creates its own functional interface like `PlayFormatter` to provide explicit domain meaning, clarity, and stronger type safety. While `Function<Play, String>` is structurally identical, it is purely generic. Having a named interface tells other developers exactly what the component is intended for and makes the code self-documenting.

### 3. Why must the list passed to PlayCleaner.clean be an ArrayList and not the result of List.of(...) or stream().toList()?
The list must be a mutable structure like `ArrayList` because `PlayCleaner.clean` invokes modifications directly in place using `removeIf()` and `replaceAll()`. Methods like `List.of()` and `stream().toList()` return completely immutable collections, which will immediately throw an `UnsupportedOperationException` if any attempt is made to delete or rewrite elements.

### 4. A stream does nothing until a terminal operation runs. Name three terminal operations you used.
Three terminal operations used across this assignment are:
1. `.sum()` - Combines elements into a single numerical primitive value.
2. `.toList()` - Gathers the final processed stream elements into a standard immutable collection.
3. `.collect()` - Processes stream records into complex groupings like maps or strings using collectors like `groupingBy` or `joining`.
