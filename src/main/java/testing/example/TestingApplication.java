package testing.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;

@SpringBootApplication
public class TestingApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestingApplication.class, args);
        VirementService virementService= (VirementService) ApplicationContextProvider.getApplicationContext().getBean("virementService");
        AccountService accountService= (AccountService) ApplicationContextProvider.getApplicationContext().getBean("accountService");
        String ribEmmeteur = "123456789";
        String ribRecepteur = "123456781";
        VirementRequest virement1 = new VirementRequest(new BigDecimal(-3000),accountService.getAccountHashMap().get(ribEmmeteur).getRib(),accountService.getAccountHashMap().get(ribRecepteur).getRib());
        System.out.println("Solde Emmeteur avant transaction : ");
        System.out.println(accountService.getAccountHashMap().get(ribEmmeteur).getBalance());
        System.out.println("Solde Recepteur avant transaction : ");
        System.out.println(accountService.getAccountHashMap().get(ribRecepteur).getBalance());
        virementService.performTransfert(virement1);
        System.out.println("Solde apres transaction : ");
        System.out.println(accountService.getAccountHashMap().get(ribEmmeteur).getBalance());
        System.out.println("Solde Recepteur apres transaction : ");
        System.out.println(accountService.getAccountHashMap().get(ribRecepteur).getBalance());



    }
}
