/*
Colin Passno
CEN 3024C
Software Development 1
9/2/2024
*/

/*
This class defines and manages the "book" object. Each object simply consists of three variables: An ID, a title, and an author.
This is meant to be used in conjunction with the arraylist to create the "database"
 */


import java.time.LocalDate;

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


    /*checkOut()
    self-contained method for checking out a book. Defaults to 4 weeks if called with no set length.
    */
    public void checkOut() {
        checkedOut = true;
        dueDate = LocalDate.now().plusDays(28);
    }
    public void checkOut(int days) {
        checkedOut = true;
        dueDate = LocalDate.now().plusDays(days);
    }

    /*checkIn()
    Resets a book's checked out flag and clears the due date.
    */
    public void checkIn() {
        checkedOut = false;
        dueDate = null;
    }

    //Override of toString, so that we can just type "book" instead of a long chain of gets when we want to display an entry.
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
