package br.edu.ifba.inf008.plugins.common.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoanTest {

    @Test
    public void testLoanSettersAndGetters() {
        // 1. Create a new Loan instance
        Loan loan = new Loan();
        LocalDate loanDate = LocalDate.of(2025, 8, 4);
        LocalDate returnDate = LocalDate.of(2025, 8, 18);

        // 2. Use setters to define the attribute values
        loan.setId(10);
        loan.setUserId(2);
        loan.setBookId(5);
        loan.setLoanDate(loanDate);
        loan.setReturnDate(returnDate);
        loan.setUserName("John Doe");
        loan.setBookTitle("The Art of War");

        // 3. Use getters and assertEquals to verify each value
        assertEquals(10, loan.getLoanId());
        assertEquals(2, loan.getUserId());
        assertEquals(5, loan.getBookId());
        assertEquals(loanDate, loan.getLoanDate());
        assertEquals(returnDate, loan.getReturnDate());
        assertEquals("John Doe", loan.getUserName());
        assertEquals("The Art of War", loan.getBookTitle());
    }
}