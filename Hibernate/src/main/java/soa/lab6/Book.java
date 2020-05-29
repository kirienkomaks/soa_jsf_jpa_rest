package soa.lab6;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "book")
public class Book implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idBook", nullable = false)
    private int idBook;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "isbn", nullable = false)
    private String isbn;

    @ManyToOne(targetEntity = Category.class, cascade=CascadeType.MERGE)
    @JoinColumn(name="idCategory")
    private Category category;

    @ManyToOne(targetEntity = Author.class, cascade=CascadeType.MERGE)
    @JoinColumn(name="idAuthor")
    private Author author;

    @OneToOne(mappedBy="book")
    private Lending lending;

    public Book() {
        super();
    }

    public Book(String title, String isbn, Author author, Category category) {
        this.title=title;
        this.author=author;
        this.isbn=isbn;
        this.category=category;
    }

    public Lending getLending() {
        return lending;
    }

    public void setLending(Lending lending) {
        this.lending = lending;
    }

    public int getIdBook() {
        return idBook;
    }

    public void setIdBook(int idBook) {
        this.idBook = idBook;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Category getCategory() { return category; }

    public void setCategory(Category category) { this.category = category; }
}
