# Book Collection Manager — README

## Part 6 reflection

### 1. What is SQL injection? Show the SQL `search` would have built from `' OR '1'='1` if you had used `+` instead of `?`.

SQL injection happens when user input is glued straight into a SQL string, so the
database treats part of the input as SQL code instead of data. An attacker can
then read, change or destroy rows they were never meant to touch.

With string concatenation the `search` method would have built:

    SELECT id, isbn, title, author, year, rating, is_read
    FROM books
    WHERE title LIKE '%' OR '1'='1%' OR author LIKE '%' OR '1'='1%'
    ORDER BY title ASC

Because `'1'='1'` is always true, the WHERE clause matches every row — the search
returns the whole library (and worse patterns like `'; DROP TABLE books; --` can
delete the table). With `PreparedStatement` the value `' OR '1'='1` is bound as a
plain string, so it is searched for literally and matches nothing.

### 2. Why does every DAO method open and close its own `Connection` with try-with-resources? What could go wrong if you forgot to close them?

A `Connection` (and every `PreparedStatement` / `ResultSet` opened from it) holds
an OS-level file handle and, for SQLite, a lock on the database file. try-with-resources
guarantees they are closed even if an exception is thrown, so the next call can
open the file, and a crash does not leak handles. If we forgot to close them the
app would slowly leak file handles and SQLite locks; after enough calls `connect`
would start failing and the database file could be left locked so DB Browser
cannot open it.

### 3. `App` contains no SQL at all. Name two benefits of keeping SQL inside the DAO.

- **Swap the UI without touching SQL.** Next week we are adding a JavaFX window
  on top of the same DAO; the console `App` and the JavaFX `App` both call
  `dao.getAll()` etc., and nothing in `BookDAO` changes.
- **One place to fix bugs and change the schema.** If the schema changes
  (say we add a `publisher` column) we edit `BookDAO` and `schema.sql` only,
  not every menu handler. It also makes the DAO easy to unit-test against an
  in-memory or temp-file database, as the provided `BookDAOTest` does.

### 4. `queries.sql` results summary

| # | Query | Rows returned (or affected) |
|---|---|---|
| 1 | Every book, newest first | 10 |
| 2 | Books by Andy Weir, oldest first | 3 |
| 3 | Unread books, A–Z by title | 3 |
| 4 | Titles containing 'the' (any case) | 4 |
| 5 | Book count per author, most first | 9 |
| 6 | Average rating of rated books | 1 (value 4.125) |
| 7 | Mark Sapiens read with rating 4 | 1 row updated |
| 8 | Delete unread + unrated + pre-2000 | 1 row deleted (The Pragmatic Programmer) |