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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class Ops {

    /*
    toTable pulls all entries from the database connection passed to it and returns a 2d array that is digestible by the GUI's table*/
    public static Object[][] toTable(Connection db) {
        String query = "SELECT id, title, author, genre, checkedOut, dueDate FROM books";
        List<Object[]> dataList = new ArrayList<>(); // List to store row data

        try (Statement stmt = db.createStatement();
             ResultSet res = stmt.executeQuery(query)) {

            // Iterate over ResultSet and collect data
            while (res.next()) {
                Object[] row = new Object[6]; // 6 columns (id, title, author, genre, checkedOut, dueDate)

                row[0] = res.getInt("id");        // Book ID
                row[1] = res.getString("title");  // Book Title
                row[2] = res.getString("author"); // Author Name
                row[3] = res.getString("genre");  // Genre
                boolean isCheckedOut = res.getBoolean("checkedOut"); // Checked out status

                if (isCheckedOut) {
                    row[4] = "Checked Out";  // If checked out, show "Checked Out"
                    row[5] = res.getString("dueDate");  // Get the due date
                } else {
                    row[4] = "Checked In";   // If not checked out, show "Checked In"
                    row[5] = "---";  // No due date if checked in
                }

                // Add the row to the list
                dataList.add(row);
            }

            // If no rows found, add a default row indicating the database is missing
            if (dataList.isEmpty()) {
                dataList.add(new Object[] {"--", "Database Missing!", "--", "--", "--", "--"});
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // In case of an error, add a default row with error message
            dataList.add(new Object[] {"--", "--", "--", "--", "--", "--"});
        }

        // Convert List to 2D array
        Object[][] data = new Object[dataList.size()][6];
        dataList.toArray(data); // Copy list into array

        return data;
    }

    /*
    removes a row from the database passed to it who's name matches the string passed to it. Displays feedback for success/fail/error.
     */
    public static void dbRemoveTitle(Connection db, String title) {
        String query = "DELETE FROM books WHERE title = ?"; // SQL DELETE query

        try (PreparedStatement stmt = db.prepareStatement(query)) {
            stmt.setString(1, title);  // Set the title parameter to the provided value
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, rowsAffected + " Entries Removed Successfully", "Remove By Title", JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(null, "No Entries Found", "Remove By Title", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "\nSQL EXCEPTION", "SQL EXCEPTION", JOptionPane.INFORMATION_MESSAGE);

        }
    }

    /*
    removes a row from the database passed to it who's id matches the string passed to it. Displays feedback for success/fail/error.
     */
    public static void dbRemoveId(Connection db, String id) {
        String query = "DELETE FROM books WHERE id = ?"; // SQL DELETE query

        try (PreparedStatement stmt = db.prepareStatement(query)) {
            stmt.setString(1, id);  // Set the title parameter to the provided value
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, rowsAffected + " Entries Removed Successfully", "Remove By ID", JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(null, "No Entries Found", "Remove By ID", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "\nSQL EXCEPTION", "SQL EXCEPTION", JOptionPane.INFORMATION_MESSAGE);

        }
    }

    /*
    checks out a book from the database passed to it who's id matches the string passed to it. Displays feedback for success/fail/error.
     */
    public static void dbCheckOut(Connection db, int id) {
        String chkQry = "SELECT checkedOut FROM books WHERE id = ?";
        String updQry = "UPDATE books SET checkedOut = 1, dueDate = ? WHERE id = ?";
        try {
            try (PreparedStatement selectStmt = db.prepareStatement(chkQry)) {
                selectStmt.setInt(1, id);
                try (ResultSet rs = selectStmt.executeQuery()) {
                    if (rs.next()) {
                        boolean isCheckedOut = rs.getBoolean("checkedOut");
                        if (!isCheckedOut) {
                            LocalDate dueDate = LocalDate.now().plusWeeks(4);
                            String due = dueDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
                            try (PreparedStatement updateStmt = db.prepareStatement(updQry)) {
                                updateStmt.setString(1, due);
                                updateStmt.setInt(2, id);
                                int rowsAffected = updateStmt.executeUpdate();
                                if (rowsAffected > 0) {
                                    JOptionPane.showMessageDialog(null, "Book checked out successfully\nDue: " + due, "Check Out Book", JOptionPane.INFORMATION_MESSAGE);
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Book already checked out", "Check Out Book", JOptionPane.WARNING_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "No entry found", "Check Out Book", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
    checks in a book from the database passed to it who's id matches the string passed to it. Displays feedback for success/fail/error.
     */
    public static void dbCheckIn(Connection db, int id) {
        String chkQry = "SELECT checkedOut FROM books WHERE id = ?";
        String updQry = "UPDATE books SET checkedOut = 0, dueDate = ? WHERE id = ?";
        try {
            try (PreparedStatement selectStmt = db.prepareStatement(chkQry)) {
                selectStmt.setInt(1, id);
                try (ResultSet rs = selectStmt.executeQuery()) {
                    if (rs.next()) {
                        boolean isCheckedOut = rs.getBoolean("checkedOut");
                        if (isCheckedOut) {
                            try (PreparedStatement updateStmt = db.prepareStatement(updQry)) {
                                updateStmt.setNull(1, java.sql.Types.VARCHAR);
                                updateStmt.setInt(2, id);
                                int rowsAffected = updateStmt.executeUpdate();
                                if (rowsAffected > 0) {
                                    JOptionPane.showMessageDialog(null, "Book checked in successfully", "Check In Book", JOptionPane.INFORMATION_MESSAGE);
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Book already checked in", "Check In Book", JOptionPane.WARNING_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "No entry found", "Check In Book", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}