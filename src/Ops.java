/*
Colin Passno
CEN 3024C
Software Development 1
9/2/2024
*/

/*
A class containing all operations that can be performed on the book arraylist.
At present, the only operations are to populate the list from a text file, remove a book based on id number, and list all books currenly in the arraylist.
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Ops {

    /*
    importBooks()
    importBooks is called with the filename (or path) of the text file containing book entries.
    It then reads this file, breaks each line into the appropriate parts, and generates an arraylist of books.
    This arraylist is then returned.
     */
    public static List<Book> importBooks(String fileName) {
        List<Book> books = new ArrayList<>();
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            while ((line = br.readLine()) != null) {

                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String title = parts[1];
                String author = parts[2];

                Book book = new Book(id, title, author);
                books.add(book);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return books;
    }

    /*
    listAll()
    Lists all books found in the arraylist passed into it to the console.
     */
    public static void listAll(List<Book> books) {
        System.out.println("***Listing all books***");
        for (Book book : books) {
            System.out.println(book);
        }
    }

    /*
    remove()
    this method is called with an arraylist. It then prompts the user to input the ID of the book they wish to remove,
    and then iterates through the arraylist to find the appropriate book and remove it.
    The newly trimmed arraylist is then returned.
     */
    public static List<Book> remove(List<Book> books) {
        boolean found = false;
        boolean number = false;
        Scanner sc = new Scanner(System.in);
        int id = -1;

        System.out.println("Insert the id of the book you want to remove: ");
        String line = sc.nextLine();

        while (!number) {
            try {
                id = Integer.parseInt(line);
                number = true;
            } catch (NumberFormatException e) {
                System.out.println("Please input a number");
                line = sc.nextLine();
            }
        }

        Iterator<Book> iterator = books.iterator();
        while (iterator.hasNext()) {
            Book book = iterator.next();
            if (book.getId() == id) {
                iterator.remove();
                found = true;
                System.out.println("***removed from collection***\n" + book);
                break;
            }
        }
            if (!found) {System.out.println("No entry with ID " + id + " found!");}
        return books;
    }
}