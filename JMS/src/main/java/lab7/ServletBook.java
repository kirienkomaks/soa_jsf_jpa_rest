package lab7;

import lab7.queue.QueueReceiver;
import lab7.queue.QueueSender;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Named
@ApplicationScoped
public class ServletBook implements Serializable {

    @EJB
    private LibraryPersistentBean bean;

    @EJB
    private QueueSender queueSender;

    @EJB
    private QueueReceiver queueReceiver;

    private List<Book> books ;
    private List<Book> chooseBooks ;
    private List<User> users;

    private String name;
    private String surname;
    private String title;
    private String isbn;
    private String category;
    private Date dateLending;
    private Date dateReturn;

    private Book search;
    private List<Book> searchList;

    private String operation;
    private boolean add = false;
    private boolean update = false;
    private boolean delete = false;
    private boolean show = false;
    private boolean addUser = false;
    private boolean searchListBool = false;
    private boolean messages = false;

    private boolean notifications;

    public ServletBook() {}

    public String addBook(){
        try {
            Author author = bean.getAuthor(name,surname);
            Category category = bean.getCategory(this.category);
            Book book = new Book(title, isbn, author, category);
            bean.addBook(book, category, author);

            bean.sendToUsers();
            books = bean.getBooks();
            chooseBooks = bean.getBooks();
            System.out.println(bean.getAuthor(name,surname).getBooks());
        }
        catch (Exception e){
            System.err.println("Blad przy dodawaniu rekordu book: " + e);
        }
        return "index";
    }

    public String addUser(){
        bean.addUser(name,surname,notifications);
        users=bean.getUsers();
        addUser=false;
        return "libUsers";
    }

    public void searchBook() {
        searchList = bean.searchBook(title);
        searchListBool = true;
    }

    public List readListUsers(){
        return bean.getUsers();
    }

    public List<Catalog> readCatalog(){
        return bean.getCatalog();
    }
    public List<Lending> readLendings(){
        return bean.getLendingBooks();
    }

    public List<Book> readListBooks(){
        return bean.getBooks();
    }

    public String deleteBook(Book book){
        try {
            bean.deleteBook(book);
        } catch (Exception e) {
            System.err.println("Blad przy usuwaniu rekordu: " + e);
            e.printStackTrace();
        }
        books= bean.getBooks();
        return "index";
    }

    public String showMessages(){
        setMessages(true);
        return "libUsers";

    }

    public List<Messages> getMessages(){
        User user = bean.getUser(name, surname);
        List<Messages> messages = bean.getMessages(user);
        return messages;
    }
    public String reservation(){
        books = bean.getBooks();
        chooseBooks = bean.getNotLendingBooks();
        System.out.println(chooseBooks);
        users = bean.getUsers();

        return "lendingBooks";
    }

    public String returnBooks(Book book) {
        bean.returnBook(book);
        books = bean.getBooks();
        chooseBooks = bean.getNotLendingBooks();
        users = bean.getUsers();

        queueSender.sendMessage("Ksiązka " + book.getTitle() + " zwrócona");
        bean.sendToObservations(book);

        return "lendingBooks";
    }

    public String notificBook(Book book){
        User user = bean.getUser(name, surname);
        bean.addObservations(user,book);
        return  "lendingBooks";
    }

    public String reserved(Book book){
        try {
            bean.reserveBook(book,bean.getUser(name, surname), dateLending, dateReturn);
        } catch (Exception e) {
            System.err.println("Blad przy rezerwacji rekordu: " + e);
            e.printStackTrace();
        }
        books = bean.getBooks();
        chooseBooks = bean.getNotLendingBooks();
        users = bean.getUsers();
        return "lendingBooks";
    }

    public String updateBooks(Book book) {
        try {
            bean.updateBook(book,name,surname,category, title,isbn);

        } catch (Exception e) {
            System.err.println("Blad przy zmianie rekordu  : " + e);
        }
        books = bean.getBooks();
        for (Book b:books) {
            System.out.println(b.getTitle() + " " + b.getAuthor().getName());
        }
        return "index";
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

    public boolean isSearchListBool() {
        return searchListBool;
    }

    public void setSearchListBool(boolean searchListBool) {
        this.searchListBool = searchListBool;
    }

    public List<Book> getSearchList() {
        return searchList;
    }

    public void setSearchList(List<Book> searchList) {
        this.searchList = searchList;
    }

    public Book getSearch() {
        return search;
    }

    public void setSearch(Book search) {
        this.search = search;
    }

    public boolean isAddUser() {
        return addUser;
    }

    public void setAddUser(boolean addUser) {
        this.addUser = addUser;
    }

    public void setAddUser() {
        this.addUser = true;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) { this.show = show; }

    public void setShow() {
        this.show = true;
        this.delete = false;
        this.add = false;
        this.update = false;
        this.searchListBool = false;
    }

    public boolean isAdd() {
        return add;
    }

    public void setAdd() {
        this.delete = false;
        this.add = true;
        this.update = false;
        this.show = false;
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate() {
        this.delete = false;
        this.add = false;
        this.update = true;
        this.show = false;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete() {
        this.delete = true;
        this.add = false;
        this.update = false;
        this.show = false;
    }

    public void setAdd(boolean add) {
        this.add = add;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Book> getChooseBooks() {
        return chooseBooks;
    }

    public void setChooseBooks(List<Book> chooseBooks) {
        this.chooseBooks = chooseBooks;
    }

    public boolean isNotifications() {
        return notifications;
    }

    public void setNotifications(boolean notifications) {
        this.notifications = notifications;
    }

    public boolean isMessages() {
        return messages;
    }

    public void setMessages(boolean messages) {
        this.messages = messages;
    }
}
