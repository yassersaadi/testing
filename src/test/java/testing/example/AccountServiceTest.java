package testing.example;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import testing.example.Model.Account;
import testing.example.Service.AccountService;

import java.math.BigDecimal;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceTest {

    static private AccountService accountService;
    public static HashMap<String, Account> accountHashMap;
    public static Account account;

    @BeforeAll
    static void init(){
        AccountServiceTest.accountHashMap = new HashMap<>();
        AccountServiceTest.account = new Account("123456789",new BigDecimal(20000));
        accountHashMap.put(account.getRib(),account);
        accountService = new AccountService(accountHashMap);
    }


    @Test
    @DisplayName("Testing if the provided account exist")
    void isAccountExistTest(){
        assertEquals(account,accountService.getAccount(account.getRib()),"Should return that the account exist ");
    }

    @Test
    @DisplayName("Testing if the provided account doesn't exist")
    void isAccountNotExist(){
        Account account2 = new Account("111111111", new BigDecimal(20000));
        assertNull(accountService.getAccount(account2.getRib()),"Should return that the account doesn't exist ");
    }

}