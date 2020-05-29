package lab7;

import javax.ejb.Local;
import java.util.Date;
import java.util.List;

@Local
public interface LibraryPersistentBeanLocal {

    void addBook(Book book, Category category, Author author);

    void deleteBook(Book book);

    void updateBook(Book book, String name, String surname, String category, String title, String isbn);

    void reserveBook(Book book, User user, Date dateLending, Date dateReturn);

    void addUser(String name, String surname, Boolean notifications);

    User getUser(String name, String surname);

    List<Book> getBooks();

    List<Book> getNotLendingBooks();

    List<Lending> getLendingBooks();

    void returnBook(Book book);

    List<User> getUsers();

    List<Book> searchBook(String title);

    Author getAuthor(String name, String surname);

    List<Author> getAuthors();

    Category getCategory(String categoryStr);

    List<Catalog> getCatalog();

    boolean isInCatalog(Book book);

    void addCatalog(Book book);

    void deleteCatalog(Book book);

    List<User> selectUserByAuthor(String name, String surname, Date firstDate, Date secondDate);

    List<Author> selectAuthorByUser(String name, String surname, Date dateLending, Date dateReturn);

    List<User> selectUserByBookName(String title, Date firstDate, Date secondDate);

    Author selectMaxCountAuthor(String name, String surname);

    void sendToUsers();

    void insertMessage(User user, String message);

    List<Messages> getMessages(User user);

    void addObservations(User user, Book book);

    void sendToObservations(Book book);

}
