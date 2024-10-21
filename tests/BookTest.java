import javax.swing.*;
import java.io.*;
import java.util.*;

import static org.junit.Assert.*;

public class BookTest {

    Book book1 = new Book(21,"Kill The Ocean", "Murray Auchincloss");
    Book book2 = new Book(22,"Cigarettes are better than weed", "Ryan McGee");
    Book book3 = new Book(23,"Hpoe", "Orack Bbama");

    //List<Book> db = new ArrayList<>();
    //Ops.addObj(db, book1);
    List<Book> db = new ArrayList<>(Arrays.asList(book1, book2));

    @org.junit.Test
    public void addBook() {
        Ops.addObj(db, book3);
        assertEquals(db.get(2).getId(),book3.getId());
    }

    @org.junit.Test
    public void removeId() {
        boolean present = false;

        db = Ops.remove(db, 21);

        for (Book book : db) {
            if (book.getId() == 21) {present = true; break;}
        }
        assertFalse(present);
    }

    @org.junit.Test
    public void removeTitle() {
        boolean present = false;
        db = Ops.removeByTitle(db, "Kill The Ocean");

        for (Book book : db) {
            if (book.getId() == 21) {present = true; break;}
        }
        assertFalse(present);
    }



    @org.junit.Test
    public void checkOut() {
        //Book book = new Book(21,"Kill The Ocean", "Murray Auchincloss");
        book1.checkOut();
        assertNotEquals(book1.getDueDate(), null);
    }

    @org.junit.Test
    public void checkIn() {
        //Book book = new Book(21,"Kill The Ocean", "Murray Auchincloss");
        book1.checkIn();
        assertNull(book1.getDueDate());
    }

}