package testing.example;

import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import testing.example.Helpers.VirementValidator;
import testing.example.Model.Account;
import testing.example.Service.AccountService;
import testing.example.Model.VirementRequest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

class VirementValidatorTest {
    private static VirementValidator virementValidator;
    private static Account accountSender;
    private static Account accountReceiver;
    private VirementRequest virementRequest;

    public VirementValidatorTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Mock
    private static AccountService accountService;

    @BeforeEach
    void init(){
        virementValidator = new VirementValidator(accountService);

        accountSender = new Account("123456789", new BigDecimal(20000));
        accountReceiver = new Account("123456781", new BigDecimal(3000));

        given(accountService.getAccount(accountSender.getRib())).willReturn(accountSender);
        given(accountService.getAccount(accountReceiver.getRib())).willReturn(accountReceiver);
        given(accountService.getBalance(accountSender.getRib())).willReturn(accountSender.getBalance());
        given(accountService.getBalance(accountReceiver.getRib())).willReturn(accountReceiver.getBalance());
    }

//    Question Should i test only the virementRequestValidator function that calls to some other function to validate the virement
//    or should i test the individual functions and the virementRequestValidator
//    ps : The functions were private I changed them to public so that i can test them

//    @Test
//    @DisplayName("Checks payment greater than zero")
//    void isPaymentGreaterThanZeroTest(){
//        assertTrue(virementValidator.isPaymentGreaterThanZero(new BigDecimal(1)),"Should return that the payment is greater than zero");
//    }
//
//    @Test
//    @DisplayName("Checks payment equals zero")
//    void isPaymentEqualsZeroTest(){
//        assertFalse(virementValidator.isPaymentGreaterThanZero(BigDecimal.ZERO),"Should return that the payment is equal zero");
//    }
//
//    @Test
//    @DisplayName("Checks payment negative")
//    void isPaymentNegativeTest(){
//        assertFalse(virementValidator.isPaymentGreaterThanZero(new BigDecimal(-1)), "Should return that the payment is negative");
//    }
//
//    @Test
//    @DisplayName("Checks balance greater than payment")
//    void isBalanceGreaterThanPaymentTest(){
//        assertTrue(virementValidator.isBalanceGreaterThanPayment(new BigDecimal(2000), new BigDecimal(3000)), "Should return that the balance is greater thant the payment");
//    }
//
//    @Test
//    @DisplayName("Checks payment greater than balance")
//    void isPaymentGreaterThanBalanceTest(){
//        assertFalse(virementValidator.isBalanceGreaterThanPayment(new BigDecimal(3000), new BigDecimal(2000)), "Should return that the payment is greater thant the balance");
//    }
//
//
//    @Test
//    @DisplayName("Checks balance equals payment")
//    void isBalanceEqualsPaymentTest(){
//        assertTrue(virementValidator.isBalanceGreaterThanPayment(new BigDecimal(2000), new BigDecimal(2000)), "Should return that the balance equals the payment");
//    }
//
//    @Test
//    @DisplayName("Checks payment equals null")
//    void isPaymentNull(){
//        assertTrue(virementValidator.isPaymentNull(null), "Should return that the payment is null");
//    }
//
//    @Test
//    @DisplayName("Checks payment isn't null")
//    void isPaymentNotNull(){
//        assertFalse(virementValidator.isPaymentNull(new BigDecimal(2000)), "Should return that the payment isn't null");
//    }


    @Nested
    class RequestValidTest{
        @Test
        @DisplayName("Checks that a virement request is valid")
        void requestValidTest(){
            VirementRequest virementRequest = new VirementRequest(new BigDecimal(2000), accountSender.getRib(), accountReceiver.getRib());
            assertTrue(virementValidator.isVirementRequestValid(virementRequest),"Should return that the virement request is valid");
        }

        @Test
        @DisplayName("Checks that the virement is valid when the sender has an invalid account")
        void accountSenderInvalidVirement(){
            Account invalidSenderAccount = new Account("12222222", new BigDecimal(3000));
            given(accountService.getAccount(invalidSenderAccount.getRib())).willReturn(null);
            VirementRequest virementRequest = new VirementRequest(new BigDecimal(2000), accountSender.getRib(), invalidSenderAccount.getRib());
            assertFalse(virementValidator.isVirementRequestValid(virementRequest),"Should return that the virement request is invalid");
        }

        @Test
        @DisplayName("Checks that the virement is valid when there is no account")
        void accountReceiverInvalidVirement(){
            Account invalidReceiverAccount = new Account("12222222", new BigDecimal(3000));
            given(accountService.getAccount(invalidReceiverAccount.getRib())).willReturn(null);
            VirementRequest virementRequest = new VirementRequest(new BigDecimal(2000), accountSender.getRib(), invalidReceiverAccount.getRib());
            assertFalse(virementValidator.isVirementRequestValid(virementRequest),"Should return that the virement request is invalid");
        }

        @Test
        @DisplayName("Checks that the virement is valid when payment null")
        void paymentNullVirement(){
            VirementRequest virementRequest = new VirementRequest(null, accountSender.getRib(), accountReceiver.getRib());
            assertFalse(virementValidator.isVirementRequestValid(virementRequest),"Should return that the virement request is invalid");
        }

        @Test
        @DisplayName("Checks that the virement is valid when payment equals zero")
        void paymentEqualsZeroVirement(){
            VirementRequest virementRequest = new VirementRequest(BigDecimal.ZERO, accountSender.getRib(), accountReceiver.getRib());
            assertFalse(virementValidator.isVirementRequestValid(virementRequest),"Should return that the virement request is invalid");
        }

        @Test
        @DisplayName("Checks that the virement is valid when payment is negative")
        void paymentNegativeVirement(){
            VirementRequest virementRequest = new VirementRequest(BigDecimal.ZERO, accountSender.getRib(), accountReceiver.getRib());
            assertFalse(virementValidator.isVirementRequestValid(virementRequest),"Should return that the virement request is invalid");
        }

        @Test
        @DisplayName("Checks that the virement is valid when payment greater than balance")
        void paymentGreaterThanBalanceVirement(){
            VirementRequest virementRequest = new VirementRequest(new BigDecimal(210000), accountSender.getRib(), accountReceiver.getRib());
            assertFalse(virementValidator.isVirementRequestValid(virementRequest),"Should return that the virement request is invalid");
        }


    }




//    @Test
//    void paymentEqualsZeroExceptionTest(){
//        Exception exception = assertThrows(PaymentEqualsZeroException.class, () -> virementValidator.isPaymentGreaterThanZero(BigDecimal.ZERO));
//        String message = ExceptionMessage.PAYMENT_EQUALS_ZERO;
//        assertTrue(exception.getMessage().contains(message));
//    }
//
//    @Test
//    void paymentNegativeExceptionTest(){
//        Exception exception = assertThrows(NegativePaymentException.class, () -> virementValidator.isPaymentGreaterThanZero(new BigDecimal(-200)));
//        String message = ExceptionMessage.PAYMENT_NEGATIVE;
//        assertTrue(exception.getMessage().contains(message));
//    }










}