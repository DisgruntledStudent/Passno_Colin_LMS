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


public class Book {
    private int id;
    private String title;
    private String author;

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
    public void setId(int id) {
        this.id = id;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setAuthor(String author) {
        this.author = author;
    }

    //Override of toString, so that we can just type "book" instead of a long chain of gets when we want to display an entry.
    @Override
    public String toString() {
        return "ID: " + id + "\n\t TITLE: " + title + " \n\t\tAUTHOR: " + author;
    }
}
