/*
Colin Passno
CEN 3024C
Software Development 1
9/2/2024
*/

/*
A "database", built to read a text file and convert the data contained within into a catalogue of book objects contained in an arraylist.
User is presented with a choice to list all books, or remove a book from the catalogue. A call is made to the appropriate method contained in the Ops class.
 */

import java.util.Scanner;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        boolean running = true;
        String imp = "import.txt"; //Filepath for list of books to import
        List<Book> col;
        Scanner sc = new Scanner(System.in);
        String act = "";
        col = Ops.importBooks(imp);


        while (running) {
            System.out.println("Please choose an action.\n\t[L]ist all books\n\t[R]emove a book\n\te[X]it program");
            act = sc.nextLine().trim().toLowerCase();
            switch (act) {
                case "l" -> Ops.listAll(col);
                case "r" -> Ops.remove(col);
                case "x" -> running = false;
                default -> System.out.println("Invalid action");
            }


        }
    }
}
