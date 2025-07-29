package br.edu.ifba.inf008.plugins.common.model;

import java.time.LocalDate;

/*CREATE TABLE loans (
    loan_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    book_id INT NOT NULL,
    loan_date DATE NOT NULL,
    return_date DATE,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (book_id) REFERENCES books(book_id) ON DELETE CASCADE

*/
public class Loan {
    private int loan_id;
    private int user_id;
    private int book_id;
    private LocalDate loan_date;
    private LocalDate return_date;
    private String userName;
    private String bookTitle;
   
    public Loan(){

    }

    public Loan (int user_id,int book_id){
        
        this.user_id = user_id;
        this.book_id = book_id;
    }

    public int getBookId() {
        return book_id;
    }

    public int getUserId() {
        return user_id;
    }

    public int getLoanId() {
        return loan_id;
    }

    public LocalDate getLoanDate() {
        return loan_date;
    }

    public LocalDate getReturnDate() {
        return return_date;
    }

    public void setId(int id){
        loan_id = id;
    }

    public void setBookId(int book_id) {
        this.book_id = book_id;
    }

    public void setUserId(int user_id) {
        this.user_id = user_id;
    }

    public void setLoanDate(LocalDate date){
        loan_date = date;
    }

    public void setReturnDate(LocalDate date){
        return_date = date;
    }

    public void setLoanId(int loan_id){
        this.loan_id = loan_id;
    }

    public String getUserName() {
    return userName;
}

    public void setUserName(String userName) {
    this.userName = userName;
}

    public String getBookTitle() {
    return bookTitle;
}

    public void setBookTitle(String bookTitle) {
    this.bookTitle = bookTitle;
}

}
