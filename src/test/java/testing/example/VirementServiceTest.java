package testing.example;

import org.junit.jupiter.api.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doCallRealMethod;


class VirementServiceTest {
    @Mock
    private AccountService accountService;

    private VirementValidator virementValidator;

    private VirementService virementService;
    private Account accountSender;
    private Account accountReceiver;
    private HashMap<String, Account> accountHashMap;

    @BeforeEach
    void initBeforeEach() {
        MockitoAnnotations.initMocks(this);
        virementValidator = new VirementValidator(accountService);
        virementService = new VirementService(virementValidator, accountService);

        accountSender = new Account("123456789", new BigDecimal(20000));
        accountReceiver = new Account("123456781", new BigDecimal(3000));

        accountHashMap = new HashMap<>();
        accountHashMap.put(accountSender.getRib(), accountSender);
        accountHashMap.put(accountReceiver.getRib(), accountReceiver);

        given(accountService.getAccountHashMap()).willReturn(accountHashMap);
        given(accountService.getAccount(accountSender.getRib())).willReturn(accountSender);
        given(accountService.getAccount(accountReceiver.getRib())).willReturn(accountReceiver);
        given(accountService.getBalance(accountSender.getRib())).willReturn(accountSender.getBalance());
        doCallRealMethod().when(accountService).setBalance(anyString(), any(BigDecimal.class));
    }

//    @Test
//    void performTransfert() {
//        VirementService virementService = new VirementService();
//        AccountService accountService = new AccountService();
//        String ribEmmeteur = "123456789";
//        String ribRecepteur = "123456781";
//        VirementRequest virement = new VirementRequest(new BigDecimal(3000),accountService.getAccountHashMap().get(ribEmmeteur).getRib(),accountService.getAccountHashMap().get(ribRecepteur).getRib());
//        BigDecimal soldeBeforeVirementEmmeteur = accountService.getBalance(ribEmmeteur);
//        BigDecimal soldeBeforeViremenRecepteur = accountService.getBalance(ribRecepteur);
//        virementService.performTransfert(virement);
//        assertEquals(soldeBeforeVirementEmmeteur.subtract(virement.getMontant()), accountService.getBalance(ribEmmeteur) );
//        assertEquals(soldeBeforeViremenRecepteur.add(virement.getMontant()), accountService.getBalance(ribEmmeteur) );
//    }

    @Test
    @DisplayName("Perform transfer of a negative payment")
    void performNegativeTransfer() {
        //SHould replace any String with the checking of the rib format
        VirementRequest virementRequest = new VirementRequest(new BigDecimal(-3000), accountSender.getRib(), accountReceiver.getRib());

        BigDecimal balanceSenderBeforeTransfer = accountSender.getBalance();
        BigDecimal balanceReceiverBeforeTransfer = accountReceiver.getBalance();

        virementService.performTransfert(virementRequest);

        assertEquals(balanceSenderBeforeTransfer, accountSender.getBalance(), "Should not send negative payment");
        assertEquals(balanceReceiverBeforeTransfer, accountReceiver.getBalance(), "Should not send negative payment");
    }

    @Test
    @DisplayName("Perform transfer of a payment == 0")
    @Disabled
    void performPaymentEqualZeroTransfer() {
        VirementRequest virementRequest = new VirementRequest(new BigDecimal(0), accountSender.getRib(), accountReceiver.getRib());
        virementService.performTransfert(virementRequest);
        //Should check if there is a call to the performTransfert
    }


    @Test
    @DisplayName("Perform transfer of a positive payment")
    void performPositiveTransfer() {
        VirementRequest virementRequest = new VirementRequest(new BigDecimal(3000), accountSender.getRib(), accountReceiver.getRib());

        BigDecimal expectedSenderBalance = accountSender.getBalance().subtract(virementRequest.getPayment());
        BigDecimal expectedReceiverBalance = accountReceiver.getBalance().add(virementRequest.getPayment());

        virementService.performTransfert(virementRequest);

        assertEquals(expectedSenderBalance, accountSender.getBalance(), "Should cut payment to sender balance");
        assertEquals(expectedReceiverBalance, accountReceiver.getBalance(), "Should add payment to receiver balance ");
    }



    @Test
    @DisplayName("Perform transfer of a null payment")
    @Disabled
    void performNullTransfer() {
        VirementRequest virementRequest = new VirementRequest(null, accountSender.getRib(), accountReceiver.getRib());

        virementService.performTransfert(virementRequest);
        //Should check if there is a call to the performTransfert
    }


    @Test
    @DisplayName("Perform transfer of balance > payment")
    void performTransferBalanceGreaterPayment() {
        VirementRequest virementRequest = new VirementRequest(new BigDecimal(2000), accountSender.getRib(), accountReceiver.getRib());

        BigDecimal expectedSenderBalance = accountSender.getBalance().subtract(virementRequest.getPayment());
        BigDecimal expectedReceiverBalance = accountReceiver.getBalance().add(virementRequest.getPayment());


        virementService.performTransfert(virementRequest);

        assertEquals(expectedSenderBalance, accountSender.getBalance(), "Should send payment");
        assertEquals(expectedReceiverBalance, accountReceiver.getBalance(), "Should receive payment");
    }

    @Test
    @DisplayName("Perform transfer of payment > balance")
    void performTransferPaymentGreaterThanBalance() {
        VirementRequest virementRequest = new VirementRequest(new BigDecimal(200000), accountSender.getRib(), accountReceiver.getRib());

        BigDecimal balanceSenderBeforeTransfer = accountSender.getBalance();
        BigDecimal balanceReceiverBeforeTransfer = accountReceiver.getBalance();

        virementService.performTransfert(virementRequest);

        assertEquals(balanceSenderBeforeTransfer, accountSender.getBalance(), "Should not send payment");
        assertEquals(balanceReceiverBeforeTransfer, accountReceiver.getBalance(), "Should not receive payment");
    }

    @Test
    @DisplayName("Perform transfer of payment == balance")
    void performTransferPaymentEqualsBalance() {
        VirementRequest virementRequest = new VirementRequest(new BigDecimal(3000), accountSender.getRib(), accountReceiver.getRib());

        BigDecimal expectedSenderBalance = accountSender.getBalance().subtract(virementRequest.getPayment());
        BigDecimal expectedReceiverBalance = accountReceiver.getBalance().add(virementRequest.getPayment());


        virementService.performTransfert(virementRequest);

        assertEquals(expectedSenderBalance, accountSender.getBalance(), "Should send payment");
        assertEquals(expectedReceiverBalance, accountReceiver.getBalance(), "Should receive payment");
    }

}