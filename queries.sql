-- ============================================================
-- Part 1b: practice SQL by hand in DB Browser for SQLite
-- Run against a throwaway practice.db, not books.db
-- ============================================================

-- 1. Every book, newest first  (10 rows)
SELECT * FROM books ORDER BY year DESC;

-- 2. Books by Andy Weir, oldest first  (3 rows)
SELECT * FROM books WHERE author = 'Andy Weir' ORDER BY year ASC;

-- 3. Unread books, A–Z by title  (3 rows)
SELECT * FROM books WHERE is_read = 0 ORDER BY title ASC;

-- 4. Titles containing 'the' (any case)
--    SQLite LIKE is case-insensitive for ASCII by default  (3 rows: The Pragmatic Programmer,
--    The Martian, The Hobbit)
SELECT * FROM books WHERE title LIKE '%the%';

-- 5. How many books each author has, most first  (9 rows, Andy Weir=3 first)
SELECT author, COUNT(*) AS book_count
FROM books
GROUP BY author
ORDER BY book_count DESC, author ASC;

-- 6. Average rating of rated books (rating > 0)  (1 row: 4.143)
SELECT AVG(rating) AS avg_rating FROM books WHERE rating > 0;

-- 7. Mark 'Sapiens' as read with a rating of 4  (1 row updated)
UPDATE books SET is_read = 1, rating = 4 WHERE title = 'Sapiens';

-- 8. Delete every unrated, unread book published before 2000
--    SELECT first, then DELETE the same WHERE:
--    SELECT * FROM books WHERE rating = 0 AND is_read = 0 AND year < 2000;
--    returns The Pragmatic Programmer only (after Sapiens was rated in step 7).
DELETE FROM books WHERE rating = 0 AND is_read = 0 AND year < 2000;