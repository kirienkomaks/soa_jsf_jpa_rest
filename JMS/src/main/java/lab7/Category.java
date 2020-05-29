package lab7;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "category")
public class Category implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idCategory", nullable = false)
    private int idCategory;

    @OneToMany(mappedBy="category")
    private List<Book> books;

    @Column(name = "categoryName", nullable = false)
    private String categoryName;

    public Category() {super(); }
    public Category(String category) {
        this.categoryName=category;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
