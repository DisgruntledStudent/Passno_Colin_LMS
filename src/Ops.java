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

import javax.swing.*;
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
    public static List<Book> importBooks() {
        List<Book> books = new ArrayList<>();
        String path = getPath();
        //String path = "import.txt";
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
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
    public static List<Book> remove(List<Book> books, int line) {
        boolean found = false;
        boolean number = false;
        int id = line;
        Iterator<Book> iterator = books.iterator();
        while (iterator.hasNext()) {
            Book book = iterator.next();
            if (book.getId() == id) {
                iterator.remove();
                found = true;
                System.out.println("***removed from collection***\n" + book + "\n");
                JOptionPane.showMessageDialog(null, book.getTitle() + "\nRemoved Successfully", "Remove By ID", JOptionPane.INFORMATION_MESSAGE);
                break;
            }
        }
            if (!found) {
                JOptionPane.showMessageDialog(null, "ID Not Found", "Remove By Title", JOptionPane.WARNING_MESSAGE);
            }
        return books;
    }

    public static List<Book> removeByTitle(List<Book> books, String line) {
        boolean found = false;
        line = line.toLowerCase();

        Iterator<Book> iterator = books.iterator();
        while (iterator.hasNext()) {
            Book book = iterator.next();
            if (line.equals(book.getTitle().toLowerCase())) {
                iterator.remove();
                found = true;
                System.out.println("***removed from collection***\n" + book + "\n");
                JOptionPane.showMessageDialog(null, book.getTitle() + "\nRemoved Successfully", "Remove By Title", JOptionPane.INFORMATION_MESSAGE);
                break;
            }
        }
        if (!found) {
            JOptionPane.showMessageDialog(null, "Title not found", "Remove By Title", JOptionPane.WARNING_MESSAGE);
        }
        return books;
    }

    /*checkOut()
    Attempts to find a book in the list passed with it, and executes the book's checkOut() method.
    Returns updated collection.
     */
    public static List<Book> checkOut(List<Book> books, String input) {
        boolean found = false;
        String line = input.toLowerCase();

        Iterator<Book> iterator = books.iterator();
        while (iterator.hasNext()) {
            Book book = iterator.next();
            if (line.equals(book.getTitle().toLowerCase())) {
                found = true;
                if (book.isCheckedOut()) {
                    JOptionPane.showMessageDialog(null, book.getTitle() + "\nIs Already Checked Out", "Check Out Book", JOptionPane.WARNING_MESSAGE);
                    return books;
                }
                book.checkOut();
                JOptionPane.showMessageDialog(null, book.getTitle() + "\nChecked out successfully\nDue: " + book.getDueDate(), "Check Out Book", JOptionPane.INFORMATION_MESSAGE);
                break;
            }
        }
        if (!found) {JOptionPane.showMessageDialog(null, "Title Not Found", "Check Out Book", JOptionPane.WARNING_MESSAGE);
        }
        return books;
    }

    /*checkIn()
    Attempts to find a book in the list passed with it, and executes the book's checkIn() method.
    Returns updated collection.
     */
    public static List<Book> checkIn(List<Book> books, String input) {
        boolean found = false;
        String line = input.toLowerCase();

        Iterator<Book> iterator = books.iterator();
        while (iterator.hasNext()) {
            Book book = iterator.next();
            if (line.equals(book.getTitle().toLowerCase())) {
                found = true;
                if (!book.isCheckedOut()) {
                    JOptionPane.showMessageDialog(null, book.getTitle() + "\nIs Already Checked In", "Check In Book", JOptionPane.WARNING_MESSAGE);
                    return books;
                }
                book.checkIn();
                JOptionPane.showMessageDialog(null, book.getTitle() + "\nChecked in successfully", "Check In Book", JOptionPane.INFORMATION_MESSAGE);
                break;
            }
        }
        if (!found) {JOptionPane.showMessageDialog(null, "Title Not Found", "Check In Book", JOptionPane.WARNING_MESSAGE);
        }
        return books;
    }

    //Small function to allow the user to select a file via jfilechooser.
    public static String getPath() {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            return selectedFile.getAbsolutePath();
        } else if (result == JFileChooser.CANCEL_OPTION) {
            System.out.println("File selection canceled.");
        }
        else {System.out.println("File selection failed, somehow.");}
        return null;
    }

    //Takes the book collection list and converts it into a 2d array object that can be used by the jframe table.
    public static Object[][] toTable(List<Book> col) {
        Object[][] data;
        if (col == null) {
            data =new Object[][] {{"--","--","--","--","--"}};
            return data;
        }
        int size = col.size();
        data = new Object[size][5];
        for (int i = 0; i < size; i++) {
            Book book = col.get(i);
            data[i][0] = book.getId();
            data[i][1] = book.getTitle();
            data[i][2] = book.getAuthor();
            if (book.isCheckedOut()) {
                data[i][3] = "Checked Out";
                data[i][4] = book.getDueDate();
            }
            else {
                data[i][3] = "Checked In";
                data[i][4] = "---";
            }

        }
        return data;
    }

    //checks if the collection has been populated. Used to prevent users from performing operations on an empty list. Returns true if list is not null.
    public static boolean impChk(List<Book> books) {
        if (books == null) {
            JOptionPane.showMessageDialog(null, "No Database Imported", "NO DATABASE", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else {return true;}
    }
}