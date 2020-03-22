package testing.example.Service;

import java.math.BigDecimal;

public class VirementRequest {
    public VirementRequest(BigDecimal payment, String ribSender, String ribReceiver) {
        this.payment = payment;
        this.ribSender = ribSender;
        this.ribReceiver = ribReceiver;
    }

    private BigDecimal payment;
    private String ribSender;
    private String ribReceiver;

    public BigDecimal getPayment() {
        return payment;
    }



    public String getRibSender() {
        return ribSender;
    }



    public String getRibReceiver() {
        return ribReceiver;
    }


}
