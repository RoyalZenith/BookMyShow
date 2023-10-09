package code.gaurav.bookmyshow.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@Entity
public class Payment extends BaseModel{
    private String refId;
    @Enumerated(EnumType.STRING)
    private List<PaymentProvider> paymentProviders;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    private double amount;
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @ManyToOne
    private Ticket ticket;
}
