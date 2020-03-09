package testing.example;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class VirementValidator {

    private final AccountService accountService;

    public VirementValidator(AccountService accountService) {
        this.accountService = accountService;
    }

    //Should check if balance is > 0
    private boolean isPaymentPositive(BigDecimal payment){
        return payment.compareTo(BigDecimal.ZERO) > 0 ? true : false ;
    }
    //Checking if balance > payment
    private boolean isBalanceGreaterThanPayment(BigDecimal payment, BigDecimal balance){
        if(balance.compareTo(payment) >  0 || balance.compareTo(payment) == 0 ) {
            return true;
        }
        return false;
    }

    public boolean isVirementRequestValid(VirementRequest virementRequest){
        if(accountService.getAccount(virementRequest.getRibSender()) != null && accountService.getAccount(virementRequest.getRibReceiver()) != null){
            if( isPaymentPositive(virementRequest.getPayment()) && isBalanceGreaterThanPayment(virementRequest.getPayment(), accountService.getBalance(virementRequest.getRibSender()))){
                return true;
            }
        }
        return false;
    }
}
