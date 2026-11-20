package edu.cwi.swdv110.books;
import java.sql.*;
import java.util.*;
public class BookDAO {
    private final String url;
    public BookDAO(String url) { this.url = url; }
    private Book toBook(ResultSet rs) throws SQLException {
        return new Book(rs.getInt("id"), rs.getString("isbn"), rs.getString("title"), rs.getString("author"), rs.getInt("year"), rs.getInt("rating"), rs.getInt("is_read") == 1);
    }
    private List<Book> query(String sql, String... params) throws SQLException {
        List<Book> result = new ArrayList<>();
        try (Connection c = Database.connect(url); PreparedStatement ps = c.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) ps.setString(i + 1, params[i]);
            try (ResultSet rs = ps.executeQuery()) { while (rs.next()) result.add(toBook(rs)); }
        }
        return result;
    }
    public List<Book> getAll() throws SQLException { return query("SELECT * FROM books ORDER BY title ASC"); }
    public Optional<Book> findByIsbn(String isbn) throws SQLException {
        String sql = "SELECT * FROM books WHERE isbn = ?";
        try (Connection c = Database.connect(url); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, isbn);
            try (ResultSet rs = ps.executeQuery()) { return rs.next() ? Optional.of(toBook(rs)) : Optional.empty(); }
        }
    }
    public List<Book> search(String term) throws SQLException {
        String sql = "SELECT * FROM books WHERE title LIKE ? OR author LIKE ? ORDER BY title ASC";
        String pattern = "%" + term + "%";
        return query(sql, pattern, pattern);
    }
    public List<Book> unread() throws SQLException { return query("SELECT * FROM books WHERE is_read = 0 ORDER BY title ASC"); }
    public List<Book> getSorted(String column) throws SQLException {
        String orderBy = switch (column.toLowerCase()) {
            case "author" -> "author ASC, title ASC"; case "year" -> "year DESC, title ASC";
            case "rating" -> "rating DESC, title ASC"; default -> "title ASC";
        };
        return query("SELECT * FROM books ORDER BY " + orderBy);
    }
    public int add(Book b) throws SQLException {
        String sql = "INSERT INTO books (isbn, title, author, year, rating, is_read) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection c = Database.connect(url); PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, b.isbn()); ps.setString(2, b.title()); ps.setString(3, b.author());
            ps.setInt(4, b.year()); ps.setInt(5, b.rating()); ps.setInt(6, b.read() ? 1 : 0);
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) { return keys.next() ? keys.getInt(1) : -1; }
        }
    }
    public boolean update(Book b) throws SQLException {
        String sql = "UPDATE books SET isbn = ?, title = ?, author = ?, year = ?, rating = ?, is_read = ? WHERE id = ?";
        try (Connection c = Database.connect(url); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, b.isbn()); ps.setString(2, b.title()); ps.setString(3, b.author());
            ps.setInt(4, b.year()); ps.setInt(5, b.rating()); ps.setInt(6, b.read() ? 1 : 0); ps.setInt(7, b.id());
            return ps.executeUpdate() > 0;
        }
    }
    public boolean markRead(String isbn, int rating) throws SQLException {
        if (rating < 1 || rating > 5) throw new IllegalArgumentException("Rating must be between 1 and 5");
        String sql = "UPDATE books SET is_read = 1, rating = ? WHERE isbn = ?";
        try (Connection c = Database.connect(url); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, rating); ps.setString(2, isbn);
            return ps.executeUpdate() > 0;
        }
    }
    public boolean delete(String isbn) throws SQLException {
        String sql = "DELETE FROM books WHERE isbn = ?";
        try (Connection c = Database.connect(url); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, isbn); return ps.executeUpdate() > 0;
        }
    }
    public Map<String, Integer> countByAuthor() throws SQLException {
        String sql = "SELECT author, COUNT(*) AS book_count FROM books GROUP BY author ORDER BY book_count DESC, author ASC";
        Map<String, Integer> result = new LinkedHashMap<>();
        try (Connection c = Database.connect(url); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) result.put(rs.getString("author"), rs.getInt("book_count"));
        }
        return result;
    }
    public double averageRating() throws SQLException {
        String sql = "SELECT AVG(rating) FROM books WHERE rating > 0";
        try (Connection c = Database.connect(url); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getDouble(1) : 0.0;
        }
    }
}
