package testing.example.Helpers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import testing.example.Exception.NegativePaymentException;
import testing.example.Exception.NullPaymentException;
import testing.example.Exception.PaymentGreaterThanBalanceException;
import testing.example.Service.AccountService;
import testing.example.Service.VirementRequest;

import java.math.BigDecimal;

@Component
public class VirementValidator {

    private final AccountService accountService;
    @Autowired
    public VirementValidator(AccountService accountService) {
        this.accountService = accountService;
    }

    //Should check if balance is > 0
    private boolean isPaymentGreaterThanZero(BigDecimal payment) {
        if (payment.compareTo(BigDecimal.ZERO) > 0) {
            return true;
        }
        try {
            throw new NegativePaymentException("Payment is negative");
        } catch (NegativePaymentException e) {
            e.printStackTrace();
        }
        return false;
    }

    //Checking if balance > payment
    private boolean isBalanceGreaterThanPayment(BigDecimal payment, BigDecimal balance) {
        if (balance.compareTo(payment) > 0 || balance.compareTo(payment) == 0) {
            return true;
        }
        try {
            throw new PaymentGreaterThanBalanceException("Payment greater than balance");
        } catch (PaymentGreaterThanBalanceException e) {
            e.printStackTrace();
        }
        return false;
    }
    private boolean isPaymentNull(BigDecimal payment){
        if(payment == null){
            try {
                throw new NullPaymentException("Payment is null");
            } catch (NullPaymentException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    public boolean isVirementRequestValid(VirementRequest virementRequest) {
        if (accountService.getAccount(virementRequest.getRibSender()) != null && accountService.getAccount(virementRequest.getRibReceiver()) != null) {
            if (!isPaymentNull(virementRequest.getPayment()) && isPaymentGreaterThanZero(virementRequest.getPayment()) && isBalanceGreaterThanPayment(virementRequest.getPayment(), accountService.getBalance(virementRequest.getRibSender()))) {
                return true;
            }
        }
        return false;
    }


}
