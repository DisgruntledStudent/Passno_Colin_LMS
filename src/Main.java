/*
Colin Passno
CEN 3024C
Software Development 1
9/2/2024
*/

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

/**
 * Main class
 * A connection is established to a database file containing a catalogue of books.
 * A window is generated, displaying a table containing the books and a series of buttons used to execute actions.
 */
public class Main extends JFrame {
    private JPanel MainPanel;
    private JTable table;
    private JButton buttonImport;
    private JButton removeTitleButton;
    private JButton removeIDButton;
    private JButton checkOutButton;
    private JButton checkInButton;
    private JButton exitButton;
    private DefaultTableModel tModel;

    public Main() throws SQLException {
        setContentPane(MainPanel);
        setTitle("LMS V0.4");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 500);
        setLocationRelativeTo(null);
        setVisible(true);
        Connection db = DriverManager.getConnection("jdbc:sqlite:library.db");
        refreshTableData(db);



        //Listeners for buttons. Each triggers the method of the same description.
       buttonImport.addActionListener(new ActionListener() { //depreciated. Might use later to let users choose path to database.
            @Override
            public void actionPerformed(ActionEvent e) {
               refreshTableData(db);
            }
        });
        removeTitleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    String input = JOptionPane.showInputDialog(null, "Please enter Title of book to remove:", "Remove By Title", JOptionPane.QUESTION_MESSAGE);
                    Ops.dbRemoveTitle(db,input);
                    refreshTableData(db);
            }
        });
        removeIDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    String input = JOptionPane.showInputDialog(null, "Please enter ID of book to remove:", "Remove By ID", JOptionPane.QUESTION_MESSAGE);
                    try {
                        Ops.dbRemoveId(db, input);
                        refreshTableData(db);
                    } catch (NumberFormatException ee) {
                        JOptionPane.showMessageDialog(null, "Please Input A Valid Number", "Remove By ID", JOptionPane.ERROR_MESSAGE);
                    }
            }
        });
        checkOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    String input = JOptionPane.showInputDialog(null, "Please enter ID of book to check out:", "Check Out Book", JOptionPane.QUESTION_MESSAGE);
                    Ops.dbCheckOut(db, Integer.parseInt(input));
                    refreshTableData(db);
            }
        });
        checkInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    String input = JOptionPane.showInputDialog(null, "Please enter ID of book to check in:", "Check In Book", JOptionPane.QUESTION_MESSAGE);
                    Ops.dbCheckIn(db, Integer.parseInt(input));
                    refreshTableData(db);
            }
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) throws SQLException {
        new Main();

    }

    /**
     * custom formatting for table element
     */
    private void createUIComponents() {
        String[] columnNames = {"ID", "Title", "Author", "Genre", "Checked Out", "Due Date"};
        Object[][] tData = new Object[][] {{"--", "--", "--", "--", "--"}};
        tModel = new DefaultTableModel(tData, columnNames);
        table = new JTable(tModel);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getColumnModel().getColumn(0).setPreferredWidth(25);   // ID
        table.getColumnModel().getColumn(1).setPreferredWidth(275);  // Title
        table.getColumnModel().getColumn(2).setPreferredWidth(150);  // Author
        table.getColumnModel().getColumn(3).setPreferredWidth(150);  // Genre
        table.getColumnModel().getColumn(4).setPreferredWidth(75);  // Checked Out
        table.getColumnModel().getColumn(5).setPreferredWidth(75);  // Due Date
    }

    // Method to refresh the table data. Clears data from table and populates it again.

    /**
     * refreshes data in table
     * @param db connection to database
     */
    public void refreshTableData(Connection db) {
        Object[][] newData = Ops.toTable(db);
        tModel.setRowCount(0);
        for (Object[] row : newData) {
            tModel.addRow(row);
        }
    }
}





