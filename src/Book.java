/*
Colin Passno
CEN 3024C
Software Development 1
9/2/2024
*/


import java.time.LocalDate;

/**
 * The Book class defines and manages the "book" object. Each object simply consists of five variables: An ID, a title, an author, a flag indicating if a book is checked out, and a due date..
 */
public class Book {
    private int id;
    private String title;
    private String author;
    private boolean checkedOut;
    private LocalDate dueDate;

    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    public int getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getAuthor() {
        return author;
    }
    public boolean isCheckedOut() {
        return checkedOut;
    }
    public LocalDate getDueDate() {
        return dueDate;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }


    /**
     * checkOut()
     * This method sets the book's checkedOut flag to true, and sets it's due date to four weeks from the current date,
     */
    public void checkOut() {
        checkedOut = true;
        dueDate = LocalDate.now().plusDays(28);
    }
    public void checkOut(int days) {
        checkedOut = true;
        dueDate = LocalDate.now().plusDays(days);
    }

    /**
     * checkIn()
     * This method sets the book's checkedOut flag to false, and sets it's due date to NULL,
     */
    public void checkIn() {
        checkedOut = false;
        dueDate = null;
    }

    /**
     * Override of toString, so that we can just type "book" instead of a long chain of gets when we want to display an entry.
     * @return returns a string describing the state of several object variables
     */
    @Override
    public String toString() {
        String output = "ID: " + id + "\n\t TITLE: " + title + " \n\t\tAUTHOR: " + author + "\n\t\t STATUS: ";
        if (checkedOut) {
            output += "CHECKED OUT\t DUE: " + dueDate;
        }
        else { output += "NOT CHECKED OUT";}
        return output;
    }
}
