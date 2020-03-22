package testing.example.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import testing.example.Model.Account;
import testing.example.Exception.NoAccountException;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class AccountService {
    private Map<String, Account> accountHashMap;
    @Autowired
    public AccountService(Map<String, Account> accountHashMap) {
        this.accountHashMap = accountHashMap;
    }

    public Account getAccount(String rib){
        if(isAccountExist(rib)){
            return accountHashMap.get(rib);
        }
        return null;
    }


    private boolean isAccountExist(String rib){
        if(accountHashMap.containsKey(rib)){
            return true;
        }
        try {
            throw new NoAccountException("No account exception");
        } catch (NoAccountException e) {
            e.printStackTrace();
        }
        return false;
    }

    public BigDecimal getBalance(String rib){
        return getAccount(rib).getBalance();
    }

    public void setBalance(String rib,BigDecimal payment){
        getAccount(rib).setBalance(getAccount(rib).getBalance().add(payment));
    }




}
