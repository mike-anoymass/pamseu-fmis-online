package payments;

import validation.ValidateFieldsClass;
import java.sql.ResultSet;


public class AddPayment {
    ResultSet rs;

    public boolean add(Payment p){
        if (new ValidateFieldsClass().validatePaymentFields(p)) {
            boolean added = new PaymentQueries().addPayment(p);
            return added;
        }
        return true;
    }
}
