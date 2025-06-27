package sr;

public class Transaction {
    String type; // "Income" or "Expense"
    String name; // Source or Category
    double amount;
    String date;

    public Transaction(String type, String name, double amount, String date) {
        this.type = type;
        this.name = name;
        this.amount = amount;
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String toString() {
        return type + " | " + name + " | " + amount + " | " + date;
    }
}
