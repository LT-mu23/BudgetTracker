package sr;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class MainApp {
    static BudgetManager manager = new BudgetManager();

    public static void main(String[] args) {
        JFrame frame = new JFrame("Budget Tracker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new GridLayout(0, 2, 10, 10));

        JButton addIncome = new JButton("Add Income");
        JButton addExpense = new JButton("Add Expense");
        JButton viewBalance = new JButton("View Balance");
        JButton viewCategory = new JButton("Category Report");
        JButton setLimit = new JButton("Set Limit");
        JButton editDelete = new JButton("Delete Record");
        JButton sort = new JButton("Sort");
        JButton reset = new JButton("Reset");
        JButton summary = new JButton("Session Summary");

        frame.add(addIncome);
        frame.add(addExpense);
        frame.add(viewBalance);
        frame.add(viewCategory);
        frame.add(setLimit);
        frame.add(editDelete);
        frame.add(sort);
        frame.add(reset);
        frame.add(summary);

        addIncome.addActionListener(e -> addTransaction("Income"));
        addExpense.addActionListener(e -> addTransaction("Expense"));

        viewBalance.addActionListener(e -> show("Balance",
                "Income: " + manager.totalIncome() +
                        "\nExpense: " + manager.totalExpense() +
                        "\nBalance: " + manager.getBalance()));

        viewCategory.addActionListener(e -> {
            HashMap<String, Double> map = manager.categoryExpenses();
            String msg = "";
            for (String k : map.keySet()) {
                msg += k + ": " + map.get(k) + "\n";
            }
            show("Category Report", msg);
        });

        setLimit.addActionListener(e -> {
            String cat = JOptionPane.showInputDialog("Enter category:");
            String lim = JOptionPane.showInputDialog("Enter limit:");
            try {
                double val = Double.parseDouble(lim);
                manager.setLimit(cat, val);
                show("Limit Set", "Limit set for " + cat);
            } catch (Exception ex) {
                show("Error", "Invalid number");
            }
        });

        editDelete.addActionListener(e -> {
            ArrayList<Transaction> list = manager.getAll();
            String[] arr = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                arr[i] = i + ": " + list.get(i).toString();
            }
            String pick = (String) JOptionPane.showInputDialog(null, "Select to delete:",
                    "Delete", JOptionPane.QUESTION_MESSAGE, null, arr, arr[0]);
            if (pick != null) {
                int idx = Integer.parseInt(pick.split(":")[0]);
                manager.delete(idx);
                show("Deleted", "Record deleted");
            }
        });

        sort.addActionListener(e -> {
            String[] ops = {"Amount", "Date"};
            int c = JOptionPane.showOptionDialog(null, "Sort by:", "Sort",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, ops, ops[0]);
            if (c == 0) manager.sortByAmount();
            else manager.sortByDate();
            show("Sorted", "Records sorted.");
        });

        reset.addActionListener(e -> {
            manager.reset();
            show("Reset", "All data cleared.");
        });

        summary.addActionListener(e -> show("Summary", manager.summary()));

        frame.setVisible(true);
    }

    public static void addTransaction(String type) {
        JTextField field1 = new JTextField();
        JTextField field2 = new JTextField();
        JTextField field3 = new JTextField();

        JPanel p = new JPanel(new GridLayout(0, 2));
        p.add(new JLabel(type.equals("Income") ? "Source:" : "Category:"));
        p.add(field1);
        p.add(new JLabel("Amount:"));
        p.add(field2);
        p.add(new JLabel("Date:"));
        p.add(field3);

        int res = JOptionPane.showConfirmDialog(null, p, "Add " + type,
                JOptionPane.OK_CANCEL_OPTION);

        if (res == JOptionPane.OK_OPTION) {
            try {
                String name = field1.getText();
                double amt = Double.parseDouble(field2.getText());
                String date = field3.getText();
                Transaction t = new Transaction(type, name, amt, date);
                manager.add(t);
                if (type.equals("Expense") && manager.isLimitExceeded(name)) {
                    JOptionPane.showMessageDialog(null, "Warning: Limit exceeded for " + name);
                }
                show("Success", type + " added");
            } catch (Exception ex) {
                show("Error", "Invalid input");
            }
        }
    }

    public static void show(String title, String msg) {
        JOptionPane.showMessageDialog(null, msg, title, JOptionPane.INFORMATION_MESSAGE);
    }
}
