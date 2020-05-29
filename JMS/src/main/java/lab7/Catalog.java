package lab7;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "catalog")
public class Catalog implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idCatalog", nullable = false)
    private int idCatalog;

    @Column(name = "positionName", nullable = false)
    private String positionName;

    @Column(name = "authorName", nullable = false)
    private String authorName;

    @Column(name = "authorSurname", nullable = false)
    private String authorSurname;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "available", nullable = false)
    private int available;

    public Catalog() {super();}

    public Catalog(String positionName,String authorName, String authorSurname, int quantity, int available ) {
        this.positionName = positionName;
        this.authorName = authorName;
        this.authorSurname = authorSurname;
        this.quantity = quantity;
        this.available = available;
    }

    public int getIdCatalog() {
        return idCatalog;
    }

    public void setIdCatalog(int idCatalog) {
        this.idCatalog = idCatalog;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorSurname() {
        return authorSurname;
    }

    public void setAuthorSurname(String authorSurname) {
        this.authorSurname = authorSurname;
    }
}
