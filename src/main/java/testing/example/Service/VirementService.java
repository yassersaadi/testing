package testing.example.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import testing.example.Helpers.VirementValidator;

@Service
public class VirementService {

    private final VirementValidator virementValidator;

    private final AccountService accountService;

    @Autowired
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
