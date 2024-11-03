/*
Colin Passno
CEN 3024C
Software Development 1
9/2/2024
*/

/*
A "database", built to read a text file and convert the data contained within into a catalogue of book objects contained in an arraylist.
A window is generated, displaying a table containing the books and a series of buttons used to execute actions.
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Main extends JFrame {
    private JPanel MainPanel;
    private JTable table;
    private JButton buttonImport;
    private JButton removeTitleButton;
    private JButton removeIDButton;
    private JButton checkOutButton;
    private JButton checkInButton;
    private JButton exitButton;
    private DefaultTableModel tModel; // Declare the model here
    List<Book> col;

    public Main() {
        setContentPane(MainPanel);
        setTitle("LMS V0.1");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);
        setVisible(true);

        //Listeners for buttons. Each triggers the method of the same description.
        buttonImport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               col = Ops.importBooks();
               refreshTableData();
            }
        });
        removeTitleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Ops.impChk(col)) {
                    String input = JOptionPane.showInputDialog(null, "Please enter Title of book to remove:", "Remove By Title", JOptionPane.QUESTION_MESSAGE);
                    col = Ops.removeByTitle(col, input);
                    refreshTableData();
                }
            }
        });
        removeIDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Ops.impChk(col)) {
                    String input = JOptionPane.showInputDialog(null, "Please enter ID of book to remove:", "Remove By ID", JOptionPane.QUESTION_MESSAGE);
                    try {
                        int id = Integer.parseInt(input);
                        col = Ops.remove(col, id);
                        refreshTableData();
                    } catch (NumberFormatException ee) {
                        JOptionPane.showMessageDialog(null, "Please Input A Valid Number", "Remove By ID", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        checkOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Ops.impChk(col)) {
                    String input = JOptionPane.showInputDialog(null, "Please enter Title of book to check out:", "Check Out Book", JOptionPane.QUESTION_MESSAGE);
                    col = Ops.checkOut(col, input);
                    refreshTableData();
                }
            }
        });
        checkInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Ops.impChk(col)) {
                    String input = JOptionPane.showInputDialog(null, "Please enter Title of book to check in:", "Check In Book", JOptionPane.QUESTION_MESSAGE);
                    col = Ops.checkIn(col, input);
                    refreshTableData();
                }
            }
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        new Main();

    }

    //custom formatting for the table
    private void createUIComponents() {
        String[] columnNames = {"ID", "Title", "Author", "Checked Out", "Due Date"};
        Object[][] tData = Ops.toTable(col);
        tModel = new DefaultTableModel(tData, columnNames);
        table = new JTable(tModel);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getColumnModel().getColumn(0).setPreferredWidth(25);   // ID
        table.getColumnModel().getColumn(1).setPreferredWidth(275);  // Title
        table.getColumnModel().getColumn(2).setPreferredWidth(150);  // Author
        table.getColumnModel().getColumn(3).setPreferredWidth(75);  // Checked Out
        table.getColumnModel().getColumn(4).setPreferredWidth(75);  // Due Date
    }

    // Method to refresh the table data. Clears data from table and populates it again.
    public void refreshTableData() {
        Object[][] newData = Ops.toTable(col);
        tModel.setRowCount(0);
        for (Object[] row : newData) {
            tModel.addRow(row);
        }
    }
}





