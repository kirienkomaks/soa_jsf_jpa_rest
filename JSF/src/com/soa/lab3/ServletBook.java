package com.soa.lab3;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.util.*;

@ManagedBean(name = "ServletBook")
@ApplicationScoped
public class ServletBook implements Serializable {
    private List<Book> books = new ArrayList<>();
    private List<Book> filteredBooks = new ArrayList<>();
    private List<Book> bookTEMP= new ArrayList<>();
    private String waluta;
    private String filtry = "";
    private String suma = "";
    private int ilosc;

    //filtracja

    private Map<String, Boolean> selectedIds = new HashMap<String, Boolean>();
    private List<Book> selectedDataList = new ArrayList<Book>();


    public String getSuma() {
        return suma;
    }

    public void setSuma(String suma) {
        this.suma = suma;
    }

    public int getIlosc() {
        return ilosc;
    }

    public void setIlosc(int ilosc) {
        this.ilosc = ilosc;
    }

    public Map<String, Boolean> getSelectedIds() {
        return selectedIds;
    }

    public List<Book> getSelectedDataList() {
        return selectedDataList;
    }

    public void setSelectedDataList(List<Book> selectedDataList) {
        this.selectedDataList = selectedDataList;
    }

    public void setSelectedIds(Map<String, Boolean> selectedIds) {
        System.out.println(selectedIds);
        this.selectedIds = selectedIds;
    }

    public List<Book> getBookTEMP() {
        return bookTEMP;
    }

    public void setBookTEMP(List<Book> bookTEMP) {
        this.bookTEMP = bookTEMP;
    }

    public List<Book> getFilteredBooks() {
        return filteredBooks;
    }

    public void setFilteredBooks(List<Book> filteredBooks) {
        this.filteredBooks = filteredBooks;
    }

    public String getFiltry() {
        return filtry;
    }

    public void setFiltry(String filtry) {
        this.filtry = filtry;
    }

    public String getWaluta() {
        return waluta;
    }

    public void setWaluta(String waluta) {
        this.waluta = waluta;
    }

    public ServletBook() {
        books.add(new Book("Book1", "John", "wojenna" , 150.0, "USD", 300));
        books.add(new Book("Book2", "Lew", "wojenna" , 250.0, "RUB", 800));
        books.add(new Book("Book3", "Ala", "romans" , 350.0, "PLN", 500));
        books.add(new Book("Book4", "Richard", "romans" , 450.0, "PLN", 400));
        books.add(new Book("Book5", "Dmitriy", "kryminal" , 500.0, "USD", 334));
        books.add(new Book("Book6", "Daniel", "kryminal" , 1000.0, "RUB", 915));
    }

    public List updateWalute (List<Book> l){
        List<Book> list =  new ArrayList<>();
        for (Book b: l) {
            list.add(b.clone());
        }
        for (Book b: list) {
            if (b.getWaluta().equals(waluta)){

            } else{
                if (b.getWaluta().equals("USD") && waluta.equals("PLN")){
                    double cena = b.getCena()*4.2;
                    b.setCena(cena);
                    b.setWaluta("PLN");

                }
                if (b.getWaluta().equals("USD") && waluta.equals("RUB")){
                    double cena = b.getCena()*76.4;
                    b.setCena(cena);
                    b.setWaluta("RUB");
                }
                if (b.getWaluta().equals("PLN") && waluta.equals("USD")){
                    double cena = b.getCena()*0.24;
                    b.setCena(cena);
                    b.setWaluta("USD");

                }
                if (b.getWaluta().equals("PLN") && waluta.equals("RUB")){
                    double cena = b.getCena()*18;
                    b.setCena(cena);
                    b.setWaluta("RUB");

                }
                if (b.getWaluta().equals("RUB") && waluta.equals("USD")){
                    double cena = b.getCena()*0.013;
                    b.setCena(cena);
                    b.setWaluta("USD");
                }
                if (b.getWaluta().equals("RUB") && waluta.equals("PLN")){
                    double cena = b.getCena()*0.055;
                    b.setCena(cena);
                    b.setWaluta("PLN");
                }
            }
        }
        return list;
    }
    public List<Book> getBooks() {
        return books;
    }
    public void setBooks(List<Book> books) {
        this.books = books;
    }


    public String select(){

        if(filteredBooks.size()==0){
            for (Book b: books) {
                filteredBooks.add(b.clone());
            }
        }
        System.out.println(selectedIds);
        if(selectedIds.size()!=0) {
            System.out.println("!=0");
            for (Book dataItem : filteredBooks) {
                System.out.println(dataItem);
                if (selectedIds.get(dataItem.getTytul()).booleanValue()) {
                    selectedDataList.add(dataItem);
                    selectedIds.remove(dataItem.getTytul());
                }
            }
            if (!waluta.equals("oryginal")) {
                selectedDataList = updateWalute(selectedDataList);
                ilosc = selectedDataList.size();
                int licznik = 0;
                for (Book b : selectedDataList) {
                    licznik += b.getCena();
                }
                suma = licznik + " " + selectedDataList.get(0).getWaluta();

            }
        }
        return "select";
    }
    public boolean filterByPrice(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim();
        if (filterText == null || filterText.equals("")) {
            return true;
        }

        if (value == null) {
            return false;
        }

        return ((Comparable) value).compareTo(getDouble(filterText)) > 0;
    }
    public boolean filterByPage(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim();
        if (filterText == null || filterText.equals("")) {
            return true;
        }

        if (value == null) {
            return false;
        }

        return ((Comparable) value).compareTo(getInteger(filterText)) > 0;
    }
    private Double getDouble(String string) {
        try {
            return Double.valueOf(string);
        }
        catch (NumberFormatException ex) {
            return 0.0;
        }
    }
    private int getInteger(String string) {
        try {
            return Integer.valueOf(string);
        }
        catch (NumberFormatException ex) {
            return 0;
        }
    }

    /*
    public String filtrujWybrane() {
        filteredBooks.clear();
        for (Book b : books) {

            if (typFilter.equals("none")) {
                filteredBooks.add(b);
            }
            if (b.getTyp().equals(typFilter)) {
                filteredBooks.add(b);
            }
        }
        if (cenaMaxFilter != 0.0) {
            for (Book b : filteredBooks) {

                System.out.println(filteredBooks.size());
                if (b.getCena() >= cenaMinFilter && b.getCena() <= cenaMaxFilter) {
                    bookTEMP.add(b);
                }

            }
        filteredBooks.clear();
        filteredBooks.addAll(bookTEMP);
        bookTEMP.clear();
        }
        for (Book b:filteredBooks) {
            if (walutaWybranaFilter.equals("none")) {
                bookTEMP.add(b);}
            if (b.getWaluta().equals(walutaWybranaFilter)) {
                bookTEMP.add(b);

            }
        }
        filteredBooks.clear();
        filteredBooks.addAll(bookTEMP);
        bookTEMP.clear();

        for (Book b:filteredBooks) {
            if (iloscStronFilter.equals("none")) {
                bookTEMP.add(b);}
            if (iloscStronFilter.equals("300") && b.getIlStron()<=300) {
                bookTEMP.add(b); }
            if (iloscStronFilter.equals("600") && b.getIlStron()>300 && b.getIlStron()<=600) {
                bookTEMP.add(b); }
            if (iloscStronFilter.equals("900") && b.getIlStron()>600 && b.getIlStron()<=900) {
                bookTEMP.add(b); }
            if (iloscStronFilter.equals("1000") && b.getIlStron()>900) {
                bookTEMP.add(b); }
        }
        filteredBooks.clear();
        filteredBooks.addAll(bookTEMP);
        bookTEMP.clear();
        return "filter";
    }
    */
}


