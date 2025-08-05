package br.edu.ifba.inf008.plugins.common.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookTest {

    @Test
    public void testBookSettersAndGetters() {
        // 1. Create a new Book instance
        Book book = new Book();

        // 2. Use setters to define the attribute values
        book.setId(1);
        book.setTitle("The Lord of the Rings");
        book.setAuthor("J.R.R. Tolkien");
        book.setIsbn("978-85-9508-080-0");
        book.setPublishedYear(1954);
        book.setCopiesAvailable(5);

        // 3. Use getters and assertEquals to verify each value
        assertEquals(1, book.getId());
        assertEquals("The Lord of the Rings", book.getTitle());
        assertEquals("J.R.R. Tolkien", book.getAuthor());
        assertEquals("978-85-9508-080-0", book.getIsbn());
        assertEquals(1954, book.getPublishedYear());
        assertEquals(5, book.getCopiesAvailable());
    }
}