package soa.lab6;

import javax.ejb.LocalBean;
import javax.ejb.Lock;
import javax.ejb.Stateless;
import javax.persistence.*;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@LocalBean
@Stateless
public class LibraryPersistentBean implements LibraryPersistentBeanLocal {

    EntityManagerFactory factory = Persistence.createEntityManagerFactory("JPA-Zajecia");
    EntityManager em = factory.createEntityManager();
    CriteriaBuilder cb = em.getCriteriaBuilder();

    public LibraryPersistentBean() { }

    @Override
    @Lock
    @Transactional
    public void addBook(Book book, Category category, Author author) {
        try {
            em.getTransaction().begin();
            em.persist(category);
            em.persist(author);
            em.persist(book);
            em.getTransaction().commit();
            addCatalog(book);
            System.out.println("book added");

        } catch (Exception e) {
            System.err.println("Blad przy dodawaniu rekordu book: " + e);
            e.printStackTrace();
        }
    }

    @Override
    @Lock
    @Transactional
    public void addCatalog(Book book) {
        try {
            List<Catalog> catalog = getCatalog();
            if (isInCatalog(book)) {
                for (Catalog c : catalog) {
                    System.out.println("In merge");
                    if (c.getPositionName().equals(book.getTitle())) {
                        c.setQuantity(c.getQuantity() + 1);
                        c.setAvailable(c.getAvailable() + 1);
                        em.getTransaction().begin();
                        em.merge(c);
                        em.getTransaction().commit();
                        System.out.println("Ilosc pozycji +1");
                    }
                }
            }
            else {
                Catalog catalog1 = new Catalog(book.getTitle(),book.getAuthor().getName(),book.getAuthor().getSurname(), 1,1);
                em.getTransaction().begin();
                em.persist(catalog1);
                em.getTransaction().commit();
                System.out.println("Insert new position");
                catalog = getCatalog();
                System.out.println(catalog.size());
                for (Catalog c:catalog) {
                    System.out.println(c.getIdCatalog());

                }
            }

        } catch (Exception e) {
            System.err.println("Blad przy dodawaniu rekordu book: " + e);
            e.printStackTrace();
        }
    }

    @Override
    @Lock
    @Transactional
    public boolean isInCatalog(Book book) {
        List <Catalog> catalog = getCatalog();
        try {
            for (Catalog c:catalog) {
                System.out.println("catalog");
                System.out.println(c.getPositionName());
                System.out.println(c.getAuthorName());
                System.out.println(c.getAuthorSurname());

                System.out.println("book");
                System.out.println(book.getTitle());
                System.out.println(book.getAuthor().getName());
                System.out.println(book.getAuthor().getSurname());

                if (c.getPositionName().equals(book.getTitle()) ){
                    System.out.println("TRUE");
                    return true;
                }
            }
        } catch (Exception e) {
            System.err.println("Blad przy pobieraniu katalogu: " + e);
            e.printStackTrace();
        }
        return false;
    }

    @Override
    @Lock
    @Transactional
    public List<Catalog> getCatalog() {
        List<Catalog> list = new ArrayList<>();
        try {
            Query q = em.createQuery("FROM Catalog", Catalog.class);
            list = q.getResultList();
            return list;
        } catch (Exception e) {
            System.err.println("Blad przy pobieraniu katalogu: " + e);
            e.printStackTrace();
        }
        return list;
    }

    @Override
    @Lock
    @Transactional
    public List<Book> getBooks() {
        List<Book> list = new ArrayList<>();
        try {
            Query q = em.createQuery("FROM Book", Book.class);
            list = q.getResultList();
            return list;

        } catch (Exception e) {
            System.err.println("Blad przy pobieraniu rekordow: " + e);
        }
        return list;
    }

    @Override
    @Lock
    @Transactional
    public List<Book> getNotLendingBooks() {
        List<Book> list = new ArrayList<>();
        List<Book> books;
        try {
            Query q = em.createQuery("FROM Book", Book.class);
            books = q.getResultList();
            for (Book b : books) {
                try {
                    String s = b.getLending().toString();
                }
                catch (Exception e)
                {
                    list.add(b);
                }
            }

            return list;

        } catch (Exception e) {
            System.err.println("Blad przy pobieraniu rekordow lending: " + e);
            e.printStackTrace();
        }
        return list;
    }

    @Override
    @Lock
    @Transactional
    public List<Lending> getLendingBooks() {
        List<Lending> lendings = new ArrayList<>();
        try {
            Query ql = em.createQuery("FROM Lending", Lending.class);
            lendings = ql.getResultList();
            return lendings;

        } catch (Exception e) {
            System.err.println("Blad przy pobieraniu rekordow: " + e);
        }
        return lendings;
    }

    @Override
    @Lock
    @Transactional
    public List<Book> searchBook(String title) {
        List<Book> list = new ArrayList<>();
        try {
            Query query = em.createQuery("from Book b where b.title=:tyt", Book.class);
            query.setParameter("tyt", title);
            list = query.getResultList();
            return list;

        } catch (Exception e) {
            System.err.println("Blad przy wyszukiwaniu rekordu book: " + e);
        }
        return list;
    }

    @Override
    @Lock
    @Transactional
    public Author getAuthor(String name, String surname) {
        Author author;
        try {
            Query query = em.createQuery("from Author a where a.name=:n AND a.surname=:s", Author.class)
                    .setParameter("n", name)
                    .setParameter("s", surname);
            author = (Author) query.getSingleResult();
            System.out.println("Znaleziono autora");

            return author;
        } catch (Exception e) {
            author = new Author(name, surname);
            System.out.println("Nowy autor");
            return author;
        }
    }

    @Override
    @Lock
    @Transactional
    public void addUser(String name, String surname) {
        em.getTransaction().begin();
        em.persist(new User(name, surname));
        em.getTransaction().commit();
    }

    @Override
    @Lock
    @Transactional
    public List<User> getUsers() {
        List<User> list = new ArrayList<>();
        try {
            Query q = em.createQuery("FROM User", User.class);
            list = q.getResultList();

        } catch (Exception e) {
            System.err.println("Blad przy pobieraniu rekordow: " + e);
            return list;
        }
        return list;
    }

    @Override
    @Lock
    @Transactional
    public List<Author> getAuthors() {
        List<Author> list = new ArrayList<>();
        try {
            Query q = em.createQuery("FROM Author", Author.class);
            list = q.getResultList();

        } catch (Exception e) {
            System.err.println("Blad przy pobieraniu rekordow: " + e);
            return list;
        }
        return list;
    }

    @Override
    @Lock
    @Transactional
    public User getUser(String name, String surname) {
        User user;
        try {
            Query query = em.createQuery("from User u where u.name=:n AND u.surname=:s", User.class)
                    .setParameter("n", name)
                    .setParameter("s", surname);
            user = (User) query.getSingleResult();
            System.out.println("Znaleziono Usera");
            return user;
        } catch (Exception e) {
            user = new User(name, surname);
            System.out.println("Nowy User");
            return user;
        }
    }

    @Override
    @Lock
    @Transactional
    public Category getCategory(String categoryStr) {
        Category category;
        try {
            Query query = em.createQuery("from Category c where c.categoryName=:c", Category.class)
                    .setParameter("c", categoryStr);

            category = (Category) query.getSingleResult();
            System.out.println("Znaleziono kategorie");
            return category;
        } catch (Exception e) {
            category = new Category(categoryStr);
            System.out.println("Nowa Kategoria");
            return category;
        }
    }

    @Override
    @Lock
    @Transactional
    public void deleteBook(Book book) {
        try {
            em.getTransaction().begin();
            em.remove(em.contains(book) ? book : em.merge(book));
            em.getTransaction().commit();
            deleteCatalog(book);

        } catch (Exception e) {
            System.err.println("Blad przy usuwaniu rekordu: " + e);
            e.printStackTrace();
        }
    }

    @Override
    @Lock
    @Transactional
    public void deleteCatalog(Book book) {
        try {
            List<Catalog> catalog = getCatalog();
            if (!catalog.equals(null)) {
                if (isInCatalog(book)) {
                    for (Catalog c : catalog) {
                        if (c.getPositionName().equals(book.getTitle()) && c.getAuthorName().equals(book.getAuthor().getName()) && c.getAuthorSurname().equals(book.getAuthor().getSurname())) {
                            c.setQuantity(c.getQuantity() - 1);
                            c.setAvailable(c.getAvailable() - 1);
                            if (c.getQuantity()==0){
                                em.getTransaction().begin();
                                em.remove(c);
                                em.getTransaction().commit();
                            }
                            else {
                                em.getTransaction().begin();
                                em.merge(c);
                                em.getTransaction().commit();
                                System.out.println("Ilosc pozycji -1");
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            System.err.println("Blad przy usuwaniu catalogu: " + e);
            e.printStackTrace();
        }
    }

    @Override
    @Lock
    @Transactional
    public void reserveBook(Book book, User user, Date dateLending, Date dateReturn) {
        try {
            Lending lending = new Lending(book, dateLending, dateReturn, user);
            book.setLending(lending);
            em.getTransaction().begin();
            em.persist(user);
            em.persist(lending);
            em.persist(book);
            em.getTransaction().commit();

            List<Catalog> catalog = getCatalog();
            if (!catalog.equals(null)) {
                System.out.println("not null");
                if (isInCatalog(book)) {
                    for (Catalog c : catalog) {
                        if (c.getPositionName().equals(book.getTitle())) {
                            System.out.println("equals");
                            c.setAvailable(c.getAvailable() - 1);
                                em.getTransaction().begin();
                                em.merge(c);
                                em.getTransaction().commit();
                                System.out.println("Rezerwacja w katalogu");
                            }
                        }
                    }
                }
        } catch (Exception e) {
            System.err.println("Blad przy rezerwacji: " + e);
        }
    }

    @Override
    @Lock
    @Transactional
    public void updateBook(Book book, String name,String surname, String categoryStr, String title, String isbn) {
        try {
            Author author = getAuthor(name,surname);
            Category category = getCategory(categoryStr);
            book.setAuthor(author);
            book.setCategory(category);
            book.setTitle(title);
            book.setIsbn(isbn);
            em.getTransaction().begin();
            em.merge(author);
            em.merge(category);
            em.merge(book);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Blad przy zmianie rekordu: " + e);
        }
    }

    @Override
    @Lock
    @Transactional
    public List<User> selectUserByAuthor(String name, String surname, Date firstDate, Date secondDate){
        List<User> users = new ArrayList<>();
        CriteriaQuery<Book> query = cb.createQuery(Book.class);
        Root<Book> bookRoot = query.from(Book.class);
        //Predicate greater= cb.greaterThanOrEqualTo(bookRoot.get("lending").get("dateLending"), firstDate);
        //Predicate less= cb.lessThanOrEqualTo(bookRoot.get("lending").get("dateLending"), secondDate);
        Predicate between = cb.between(bookRoot.get("lending").get("dateLending"),firstDate,secondDate);
        query.select(bookRoot);
        query.where(cb.and(
                cb.equal(bookRoot.get("author").get("idAuthor"),getAuthor(name, surname).getIdAuthor()),
                bookRoot.get("lending").get("idLending").isNotNull(),
                between
        ));
        TypedQuery<Book> bookQuery =em.createQuery(query);
        List<Book> list=bookQuery.getResultList();
        System.out.println(list.size());
        for (Book objects : list) {
            users.add(objects.getLending().getUser());
        }
        return users;
    }

    @Override
    @Lock
    @Transactional
    public List<User> selectUserByBookName(String title, Date firstDate, Date secondDate){
        List<User> users = new ArrayList<>();
        CriteriaQuery<Book> query = cb.createQuery(Book.class);
        Root<Book> bookRoot = query.from(Book.class);
        Predicate greater= cb.greaterThanOrEqualTo(bookRoot.get("lending").get("dateLending"), firstDate);
        Predicate less= cb.lessThanOrEqualTo(bookRoot.get("lending").get("dateLending"), secondDate);
        //Predicate between = cb.between(bookRoot.get("lending").get("dateLending"),firstDate,secondDate);
        query.select(bookRoot);
        query.where(cb.and(
                cb.equal(bookRoot.get("title"),title),
                bookRoot.get("lending").get("idLending").isNotNull(),
                greater,
                less
        ));
        TypedQuery<Book> bookQuery =em.createQuery(query);
        List<Book> list=bookQuery.getResultList();
        for (Book objects : list) {
            users.add(objects.getLending().getUser());
        }
        return users;
    }

    @Override
    @Lock
    @Transactional
    public List<Author> selectAuthorByUser(String name, String surname, Date dateLending, Date dateReturn){
        List<Author> authors = new ArrayList<>();
        CriteriaQuery<Lending> query = cb.createQuery(Lending.class);
        Root<Lending> lendingRoot = query.from(Lending.class);
        query.select(lendingRoot);
        query.where(
                cb.equal(lendingRoot.get("user").get("idUser"),getUser(name, surname).getIdUser()),
                lendingRoot.get("user").isNotNull()
                );
        TypedQuery<Lending> lendingQuery =em.createQuery(query);
        List<Lending> list=lendingQuery.getResultList();
        for (Lending objects : list) {
            authors.add(objects.getBook().getAuthor());
        }
        return authors;
    }

    @Override
    @Lock
    @Transactional
    public Author selectMaxCountAuthor(String name, String surname){
        Author author = new Author();
        List<Author> authorList = getAuthors();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Book> bookRoot = query.from(Book.class);
        //Root<Author> authorRoot = query.from(Author.class);
        long maxCount = 0;
        for (Author a:authorList) {
            query.select(cb.count(bookRoot));
            query.where(
                    bookRoot.get("lending").get("idLending").isNotNull(),
                    cb.equal(bookRoot.get("author").get("idAuthor"),a.getIdAuthor()));
            TypedQuery<Long> longTypedQuery =em.createQuery(query);
            long count = longTypedQuery.getSingleResult();
            if (count>maxCount){
                maxCount=count;
                author = a;
            }
        }
        return author;
    }
}
