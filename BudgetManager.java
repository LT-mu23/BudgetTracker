package sr;

import java.util.*;

public class BudgetManager {
    ArrayList<Transaction> list = new ArrayList<>();
    HashMap<String, Double> limits = new HashMap<>();

    public void add(Transaction t) {
        list.add(t);
    }

    public ArrayList<Transaction> getAll() {
        return list;
    }

    public double totalIncome() {
        double sum = 0;
        for (Transaction t : list) {
            if (t.getType().equals("Income")) {
                sum += t.getAmount();
            }
        }
        return sum;
    }

    public double totalExpense() {
        double sum = 0;
        for (Transaction t : list) {
            if (t.getType().equals("Expense")) {
                sum += t.getAmount();
            }
        }
        return sum;
    }

    public double getBalance() {
        return totalIncome() - totalExpense();
    }

    public HashMap<String, Double> categoryExpenses() {
        HashMap<String, Double> map = new HashMap<>();
        for (Transaction t : list) {
            if (t.getType().equals("Expense")) {
                String cat = t.getName();
                double amt = t.getAmount();
                if (map.containsKey(cat)) {
                    map.put(cat, map.get(cat) + amt);
                } else {
                    map.put(cat, amt);
                }
            }
        }
        return map;
    }

    public void setLimit(String category, double limit) {
        limits.put(category, limit);
    }

    public boolean isLimitExceeded(String category) {
        double spent = categoryExpenses().getOrDefault(category, 0.0);
        double limit = limits.getOrDefault(category, -1.0);
        return limit != -1 && spent >= limit;
    }

    public void delete(int index) {
        if (index >= 0 && index < list.size()) {
            list.remove(index);
        }
    }

    public void reset() {
        list.clear();
        limits.clear();
    }

    public void sortByAmount() {
        Collections.sort(list, Comparator.comparingDouble(Transaction::getAmount));
    }

    public void sortByDate() {
        Collections.sort(list, Comparator.comparing(Transaction::getDate));
    }

    public String summary() {
        String top = "";
        double max = 0;
        for (Map.Entry<String, Double> e : categoryExpenses().entrySet()) {
            if (e.getValue() > max) {
                max = e.getValue();
                top = e.getKey();
            }
        }
        return "Income: " + totalIncome() + "\nExpense: " + totalExpense() +
                "\nBalance: " + getBalance() + "\nTop Category: " + top;
    }
}
