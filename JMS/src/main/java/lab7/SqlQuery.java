package lab7;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Named
@ApplicationScoped
public class SqlQuery implements Serializable {


    @EJB
    private LibraryPersistentBean bean;

    private List<Lending> lendings;
    private List<User> users;
    private List<Author> authors;

    private Author author;
    private User user;

    private String nameAuthor;
    private String surnameAuthor;

    private String nameUser;
    private String surnameUser;
    private Date firstDate;
    private Date secondDate;

    private String title;

    private boolean userByAuthor = false;
    private boolean userByBookName = false;
    private boolean authorByUser = false;
    private boolean authorByMaxlendings = false;


    public SqlQuery() {}

    public  String selectUser(){
        users = bean.selectUserByAuthor(nameAuthor, surnameAuthor, firstDate, secondDate);
        return "improvedSearch";
    }

    public  String selectUserBookName(){
        users = bean.selectUserByBookName(title, firstDate, secondDate);
        return "improvedSearch";
    }

    public  String selectAuthorUser(){
        authors = bean.selectAuthorByUser(nameUser, surnameUser, firstDate, secondDate);
        return "improvedSearch";
    }

    public String selectMaxAuthor(){
        setAuthorByMaxlendings();
        author = bean.selectMaxCountAuthor(nameUser,surnameAuthor);
        return "improvedSearch";
    }

    public void setUserByAuthor() {
        this.userByAuthor = true;
        this.userByBookName = false;
        this.authorByUser = false;
        this.authorByMaxlendings = false;
    }
    public void setUserByBookName() {
        this.userByAuthor = false;
        this.userByBookName = true;
        this.authorByUser = false;
        this.authorByMaxlendings = false;
    }
    public void setAuthorByUser() {
        this.userByAuthor = false;
        this.userByBookName = false;
        this.authorByUser = true;
        this.authorByMaxlendings = false;
    }
    public void setAuthorByMaxlendings() {
        this.userByAuthor = false;
        this.userByBookName = false;
        this.authorByUser = false;
        this.authorByMaxlendings = true;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public boolean isUserByAuthor() {
        return userByAuthor;
    }

    public void setUserByAuthor(boolean userByAuthor) {
        this.userByAuthor = userByAuthor;
    }

    public boolean isUserByBookName() {
        return userByBookName;
    }

    public void setUserByBookName(boolean userByBookName) {
        this.userByBookName = userByBookName;
    }

    public boolean isAuthorByUser() {
        return authorByUser;
    }

    public void setAuthorByUser(boolean authorByUser) {
        this.authorByUser = authorByUser;
    }

    public boolean isAuthorByMaxlendings() {
        return authorByMaxlendings;
    }

    public void setAuthorByMaxlendings(boolean authorByMaxlendings) {
        this.authorByMaxlendings = authorByMaxlendings;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getFirstDate() {
        return firstDate;
    }

    public void setFirstDate(Date firstDate) {
        this.firstDate = firstDate;
    }

    public Date getSecondDate() {
        return secondDate;
    }

    public void setSecondDate(Date secondDate) {
        this.secondDate = secondDate;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Lending> getLendings() {
        return lendings;
    }

    public void setLendings(List<Lending> lendings) {
        this.lendings = lendings;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getNameAuthor() {
        return nameAuthor;
    }

    public void setNameAuthor(String nameAuthor) {
        this.nameAuthor = nameAuthor;
    }

    public String getSurnameAuthor() {
        return surnameAuthor;
    }

    public void setSurnameAuthor(String surnameAuthor) {
        this.surnameAuthor = surnameAuthor;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getSurnameUser() {
        return surnameUser;
    }

    public void setSurnameUser(String surnameUser) {
        this.surnameUser = surnameUser;
    }
}