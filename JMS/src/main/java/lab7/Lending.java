package lab7;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "lending")
public class Lending implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idLending", nullable = false)
    private int idLending;

    @OneToOne
    @JoinColumn(name = "idBook")
    private Book book;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "idUser")
    private User user;

    @Column(name = "dateLending", nullable = false)
    private Date dateLending;

    @Column(name = "dateReturn", nullable = false)
    private Date dateReturn;

    public Lending() {
        super();
    }

    public Lending(Book book, Date dateLending, Date dateReturn, User user) {
        this.book = book;
        this.dateLending = dateLending;
        this.dateReturn = dateReturn;
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getIdLending() {
        return idLending;
    }

    public void setIdLending(int idLending) {
        this.idLending = idLending;
    }

    public Date getDateLending() {
        return dateLending;
    }

    public void setDateLending(Date dateLending) {
        this.dateLending = dateLending;
    }

    public Date getDateReturn() {
        return dateReturn;
    }

    public void setDateReturn(Date dateReturn) {
        this.dateReturn = dateReturn;
    }
}
