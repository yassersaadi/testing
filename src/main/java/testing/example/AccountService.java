package testing.example;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.HashMap;

@Service
public class AccountService {
    private HashMap<String,Account> accountHashMap;
    @PostConstruct
    public void init(){
        accountHashMap = new HashMap<>();
        Account account1 = new Account("123456789",new BigDecimal(20000));
        Account account2 = new Account("123456781",new BigDecimal(3000));
        accountHashMap.put("123456789",account1);
        accountHashMap.put("123456781", account2);
    }

    public Account getAccount(String rib){
        return accountHashMap.containsKey(rib) ? accountHashMap.get(rib) : null;
    }

    public BigDecimal getBalance(String rib){
        return getAccount(rib).getBalance();
    }

    public void setBalance(String rib,BigDecimal payment){
        getAccount(rib).setBalance(getAccount(rib).getBalance().add(payment));
    }


    public HashMap<String, Account> getAccountHashMap() {
        return accountHashMap;
    }

}
