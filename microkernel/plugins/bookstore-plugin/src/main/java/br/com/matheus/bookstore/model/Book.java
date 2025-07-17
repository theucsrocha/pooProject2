package br.com.matheus.bookstore.model;
/* CREATE TABLE books (
    book_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    author VARCHAR(100) NOT NULL,
    isbn VARCHAR(20) UNIQUE NOT NULL,
    published_year INT,
    copies_available INT DEFAULT 0
);*/
public class Book {
    private int book_id;
    private String title;
    private String author;
    private String isbn;
    private int published_year;
    private int copies_available;
    static private int book_id_increment;

    public Book(){}

    public Book(String title,String author,String isbn,int published_year,int copies_available){
        this.book_id = book_id_increment++;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.published_year = published_year;
        this.copies_available = copies_available;
    }
    public String getAuthor() {
        return author;
    }

    public int getBookId(){
        return book_id;
    }

    public String getTitle(){
        return title;
    }

    public String getIsbn(){
        return isbn;
    }

    public int getPublishedYear(){
        return published_year;
    }

    public int getCopiesAvailable() {
        return copies_available;
    }

    public void setId(int book_id){
        this.book_id = book_id;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setPublishedYear(int published_year) {
        this.published_year = published_year;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    public void setCopiesAvailable(int copies_available) {
        this.copies_available = copies_available;
    }

    public void addCopies(){
        copies_available++;
    }

    public void removeCopies(){
        copies_available--;
    }
}
