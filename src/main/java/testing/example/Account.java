package testing.example;

import java.math.BigDecimal;

public class Account {
    private String rib;
    private BigDecimal balance;

    public Account(String rib, BigDecimal balance) {
        this.rib = rib;
        this.balance = balance;
    }

    public BigDecimal getBalance() {
        return balance;
    }


    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getRib() {
        return rib;
    }
}
