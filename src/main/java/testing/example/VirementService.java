package testing.example;

import org.springframework.stereotype.Service;

@Service
public class VirementService {

    private final VirementValidator virementValidator;

    private final AccountService accountService;

    public VirementService(VirementValidator virementValidator, AccountService accountService) {
        this.virementValidator = virementValidator;
        this.accountService = accountService;
    }

    public void performTransfert(VirementRequest virementRequest){
        String ribSender = virementRequest.getRibSender();
        String ribReceiver = virementRequest.getRibReceiver();

        if (virementValidator.isVirementRequestValid(virementRequest)){
                accountService.setBalance(ribSender, virementRequest.getPayment().negate() );
                accountService.setBalance(ribReceiver, virementRequest.getPayment() );

        }
    }
}
