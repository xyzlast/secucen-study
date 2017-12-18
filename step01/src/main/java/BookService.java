import java.sql.*;

public class BookService {

    public void add(Book book) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
        String url = "jdbc:mysql://127.0.0.1:4306/bookstore";
        Class.forName("org.mariadb.jdbc.Driver").newInstance();
        Connection conn = DriverManager.getConnection (url, "root", "qwer12#$");

        PreparedStatement st = conn.prepareStatement("insert books(name, author, publishDate, comment) values(?, ?, ?, ?)");
        st.setString(1, book.getName());
        st.setString(2, book.getAuthor());
        java.sql.Date sqlDate = new java.sql.Date(book.getPublishDate().getTime());
        st.setDate(3, sqlDate);
        st.setString(4, book.getComment());
        st.execute();

        st.close();
        conn.close();
    }

    public Book update(Book book) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
        String url = "jdbc:mysql://127.0.0.1:4306/bookstore";
        Class.forName("org.mariadb.jdbc.Driver").newInstance();
        Connection conn = DriverManager.getConnection (url, "root", "qwer12#$");

        PreparedStatement st = conn.prepareStatement("update books set name = ?, author = ?, publishDate = ?, comment = ? where id = ?");
        st.setString(1, book.getName());
        st.setString(2, book.getAuthor());
        st.setDate(3, new java.sql.Date(book.getPublishDate().getTime()));
        st.setString(4, book.getComment());
        st.setInt(5, book.getId());
        st.execute();

        st.close();
        conn.close();

        return book;
    }

    public boolean delete(int bookId) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
        String url = "jdbc:mysql://127.0.0.1:4306/bookstore";
        Class.forName("org.mariadb.jdbc.Driver").newInstance();
        Connection conn = DriverManager.getConnection (url, "root", "qwer12#$");

        PreparedStatement st = conn.prepareStatement("delete from books where id = ?");
        st.setInt(1, bookId);
        st.execute();

        st.close();
        conn.close();

        return true;
    }

    public Book get(int id) throws ClassNotFoundException, SQLException, IllegalAccessException, InstantiationException {
        String url = "jdbc:mysql://localhost:4306/bookstore";
        Class.forName("org.mariadb.jdbc.Driver").newInstance();
        Connection conn = DriverManager.getConnection (url, "root", "qwer12#$");

        PreparedStatement st = conn.prepareStatement("select id, name, author, publishDate, comment from books where id=?");
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();

        Book book = null;
        if (!rs.next()) {
            book = new Book();
            book.setId(rs.getInt("id"));
            book.setName(rs.getString("name"));
            book.setAuthor(rs.getString("author"));
            java.util.Date date = new java.util.Date(rs.getDate("publishDate").getTime());
            book.setPublishDate(date);
            book.setComment(rs.getString("comment"));
        }
        rs.close();
        st.close();
        conn.close();
        return book;
    }

    public long countAll() throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        String url = "jdbc:mysql://localhost:4306/bookstore";
        Class.forName("org.mariadb.jdbc.Driver").newInstance();
        Connection conn = DriverManager.getConnection (url, "root", "qwer12#$");

        PreparedStatement st = conn.prepareStatement("select count(*) from books");
        ResultSet rs = st.executeQuery();
        rs.next();

        Long count = rs.getLong(1);

        rs.close();
        st.close();
        conn.close();

        return count;
    }
}
