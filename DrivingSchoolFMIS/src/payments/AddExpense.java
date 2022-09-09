package payments;

import java.sql.ResultSet;

import static settings.AlertClass.makeAlert;

public class AddExpense {

    ResultSet rs;

    public boolean add(Expense e) {
        if (validateExpenseFields(e)) {
            if (checkExpense(e.getName())) {
                boolean added = new PaymentQueries().addExpense(e);
                return added;
            } else {
                makeAlert("warning", "The Expense you entered Exist");
            }
        }
        return true;
    }

    private boolean validateExpenseFields(Expense e) {
        if (!e.getName().isEmpty()) {
            return true;
        } else {
            makeAlert("warning", "Please complete the field");
        }

        return false;
    }

    private boolean checkExpense(String id) {

        return new PaymentQueries().getExpense(id) == null;
    }
}
